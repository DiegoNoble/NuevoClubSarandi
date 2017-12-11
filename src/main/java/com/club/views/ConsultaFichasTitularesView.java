package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.FichaMedica;
import com.club.BEANS.Parametros;
import com.club.BEANS.Usuario;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.FichaMedicaDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.control.utilidades.ButtonColumnEditar;
import com.club.tableModels.FichasTitularesTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.joda.time.DateTime;
import org.joda.time.Days;

public final class ConsultaFichasTitularesView extends javax.swing.JInternalFrame {

    private FichaMedicaDAO FichaMedicaDAO;
    private List<FichaMedica> listFichas;
    private FichasTitularesTableModel tblModel;
    private ListSelectionModel listModelFichaMedicas;
    private FichaMedica FichaMedicaSelecionado;
    private Parametros parametrosSistema;
    private ParametrosDAO parametrosDAO;
    Caja movimientoCaja;
    CajaDAO cajaDAO;
    Usuario usuario;

    public ConsultaFichasTitularesView(Usuario usuario) {
        initComponents();
        this.usuario = usuario;
        listFichas = new ArrayList<>();
        dpDesde.setDate(new Date());
        dpHasta.setDate(new Date());
        DefineModeloTbl();
        parametrosDAO = new ParametrosDAO();
        parametrosSistema = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);

        filtros();
    }

    void confirmarExamen() {
        try {
            FichaMedicaSelecionado = listFichas.get(tbl.getSelectedRow());
            FichaMedicaDAO = new FichaMedicaDAO();
            FichaMedicaSelecionado.setExamenMedico(true);
            FichaMedicaDAO.Actualizar(FichaMedicaSelecionado);

            if (FichaMedicaSelecionado.getDependiente() != null) {
                DateTime fechaNacimiento = new DateTime(FichaMedicaSelecionado.getDependiente().getFechanacimiento());
                DateTime hoy = DateTime.now();
                int dias = Days.daysBetween(fechaNacimiento.toLocalDate(), hoy.toLocalDate()).getDays();

                if (dias > 2190) {
                    movimientoCaja = new Caja();
                    movimientoCaja.setConcepto("Pago honorarios médicos, ficha: " + FichaMedicaSelecionado.getId());
                    movimientoCaja.setEntrada(0.0);
                    movimientoCaja.setRubro(parametrosSistema.getRubroPagoDoctor());
                    movimientoCaja.setSalida(parametrosSistema.getComisionMedicoFicha());
                    movimientoCaja.setSectores(parametrosSistema.getSectorFicha());
                    movimientoCaja.setUsuario(usuario.getNombre());
                    movimientoCaja.setFechaMovimiento(new Date());

                    cajaDAO = new CajaDAO();
                    cajaDAO.Salvar(movimientoCaja);

                    ajustaSaldos(buscaSaldoAnteriorFecha(new Date()));

                }
            } else {
                movimientoCaja = new Caja();
                movimientoCaja.setConcepto("Pago honorarios médicos, ficha: " + FichaMedicaSelecionado.getId());
                movimientoCaja.setEntrada(0.0);
                movimientoCaja.setRubro(parametrosSistema.getRubroPagoDoctor());
                movimientoCaja.setSalida(parametrosSistema.getComisionMedicoFicha());
                movimientoCaja.setSectores(parametrosSistema.getSectorFicha());
                movimientoCaja.setUsuario(usuario.getNombre());
                movimientoCaja.setFechaMovimiento(new Date());

                cajaDAO = new CajaDAO();
                cajaDAO.Salvar(movimientoCaja);

                ajustaSaldos(buscaSaldoAnteriorFecha(new Date()));

            }

            lblMsj.setText("Ficha " + FichaMedicaSelecionado.getId() + " confirmada correctamente, registrada salida de comisión");
            new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lblMsj.setText("");
                }
            }).start();

            filtros();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaFichasTitularesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tblModel = new FichasTitularesTableModel(listFichas);
        tbl.setModel(tblModel);
        tbl.getColumn("Emisión").setCellRenderer(new MeDateCellRenderer());
        tbl.getColumn("Vencimiento").setCellRenderer(new MeDateCellRenderer());

        int[] anchos = {5, 20, 20, 200, 5, 10};
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            tbl.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        tbl.setRowHeight(25);

        new ButtonColumnEditar(tbl, 5) {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarExamen();
            }
        };

        listModelFichaMedicas = tbl.getSelectionModel();
        listModelFichaMedicas.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tbl.getSelectedRow() != -1) {
                        FichaMedicaSelecionado = listFichas.get(tbl.getSelectedRow());
                    } else {
                    }
                }
            }
        });
    }

    private void filtros() {

        listFichas.clear();

        FichaMedicaDAO = new FichaMedicaDAO();
        listFichas.addAll(FichaMedicaDAO.fichasEntreFechas(dpDesde.getDate(), dpHasta.getDate()));
        tblModel.fireTableDataChanged();

    }

    void ajustaSaldos(Double saldo) {
        try {
            cajaDAO = new CajaDAO();
            List<Caja> todos = cajaDAO.BuscaTodosOrdenaPorIDFyFecha(new Date());

            for (Caja mov : todos) {
                saldo = saldo + (mov.getEntrada() - mov.getSalida());
                mov.setSaldo(saldo);
                cajaDAO = new CajaDAO();

                cajaDAO.Actualizar(mov);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }

    Double buscaSaldoAnteriorFecha(Date fecha) {
        Double saldoAnterior = 0.0;
        cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnteriorFecha(fecha).getSaldo();
        return saldoAnterior;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupoBusqueda = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();
        dpDesde = new org.jdesktop.swingx.JXDatePicker();
        dpHasta = new org.jdesktop.swingx.JXDatePicker();
        jPanel6 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        lblMsj = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 500));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Números por Campaña"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Desde"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jLabel8.setText("hasta"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel8, gridBagConstraints);

        btnNuevo1.setText("Buscar");
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnNuevo1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel2.add(dpDesde, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel2.add(dpHasta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jToolBar1.setRollover(true);

        lblMsj.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jToolBar1.add(lblMsj);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jToolBar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        filtros();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo1;
    private org.jdesktop.swingx.JXDatePicker dpDesde;
    private org.jdesktop.swingx.JXDatePicker dpHasta;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblMsj;
    private javax.swing.JTable tbl;
    // End of variables declaration//GEN-END:variables
}
