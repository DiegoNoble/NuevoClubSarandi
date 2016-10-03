package com.club.views;

import Utilidades.AjustaColumnas;
import Utilidades.ConsultaTalonesCobrosYa;
import Utilidades.EnviarEmail;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Parametros;
import com.club.BEANS.Socio;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.MensualidadesAnuladasDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.SocioDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.Renderers.TableRendererColor;
import com.club.modelos.MensualidadesCobrosYaTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsultaActualizaTalonCobrosYa extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    ParametrosDAO parametrosDAO;
    SocioDAO socioDAO;
    MensualidadesAnuladasDAO mensualidadesAnuladasDAO;
    CcCobradorDAO ccCobradorDAO;
    Socio socioSeleccionado;
    CcCobrador credito;
    MensualidadesCobrosYaTableModel tblModelMensualidades;
    ListSelectionModel listModelMensualidades;
    Mensualidades mensualidadSeleccionada;
    List<Mensualidades> listMensualidades;
    private static Cobrador cobradorCobrosYa;
    private static Parametros parametros;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultaActualizaTalonCobrosYa() {

        initComponents();
        defineModelo();
        Parametros();
        Calendar venc = Calendar.getInstance();
        venc.set(Calendar.DAY_OF_MONTH, 15);
        dpVencimiento.setDate(venc.getTime());
        muestraContenidoTabla();

    }

    private void Parametros() {
        ParametrosDAO cAO = new ParametrosDAO();
        parametros = (Parametros) cAO.BuscaPorID(Parametros.class, 1);
        cobradorCobrosYa = parametros.getCobradorCobrosYa();
    }

    private void muestraContenidoTabla() {
        Collection<String> situacionRecibo = new ArrayList<String>();
        if (rbPagos.isSelected()) {
            situacionRecibo.add("Pago");
        } else if (rbPendientes.isSelected()) {
            situacionRecibo.add("Pendiente de Pago");
        } else if (rbTodos.isSelected()) {
            situacionRecibo.add("Pendiente de Pago");
            situacionRecibo.add("Pago");
        }
        mensualidadesDAO = new MensualidadesDAO();
        listMensualidades.clear();
        listMensualidades.addAll(mensualidadesDAO.BuscaPorCobradorSituacionVencimiento(cobradorCobrosYa, dpVencimiento.getDate(), situacionRecibo));
        tblModelMensualidades.fireTableDataChanged();
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblMensualidades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listMensualidades = new ArrayList<>();
        tblModelMensualidades = new MensualidadesCobrosYaTableModel(listMensualidades);
        tblMensualidades.setModel(tblModelMensualidades);

        int[] anchos = {1, 170, 5, 20, 10, 150, 20, 20,20, 5};
        new AjustaColumnas().ajustar(tblMensualidades, anchos);

        tblMensualidades.getColumn("Pago").setCellRenderer(new TableRendererColor(5));
        tblMensualidades.getColumn("Vencimiento").setCellRenderer(new MeDateCellRenderer());
        //tblMensualidades.getColumn("Fecha Pago").setCellRenderer(new MeDateCellRenderer());

        listModelMensualidades = tblMensualidades.getSelectionModel();
        listModelMensualidades.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblMensualidades.getSelectedRow() != -1) {

                        mensualidadSeleccionada = listMensualidades.get(tblMensualidades.getSelectedRow());

                    }
                }
            }
        }
        );

    }

    void enviaEmail(Mensualidades mensualidad, String email
    ) {
        EnviarEmail enviarEmail = new EnviarEmail(mensualidad.getUrlPDF(), email);
        if (enviarEmail.enviaMail() == true) {
            JOptionPane.showMessageDialog(this, "Email enviado correctamente a: " + email);
        } else {
            JOptionPane.showMessageDialog(this, "Error enviando email a: " + email);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbPendientes = new javax.swing.JRadioButton();
        rbPagos = new javax.swing.JRadioButton();
        rbTodos = new javax.swing.JRadioButton();
        dpVencimiento = new org.jdesktop.swingx.JXDatePicker();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMensualidades = new javax.swing.JTable();
        scrollpane = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Busqueda por:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        buttonGroup1.add(rbPendientes);
        rbPendientes.setSelected(true);
        rbPendientes.setText("Solo pendientes");
        jPanel4.add(rbPendientes);

        buttonGroup1.add(rbPagos);
        rbPagos.setText("Solo pagos");
        jPanel4.add(rbPagos);

        buttonGroup1.add(rbTodos);
        rbTodos.setText("Todos");
        jPanel4.add(rbTodos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel2.add(dpVencimiento, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta/Actualiza CobrosYa"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblMensualidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMensualidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMensualidadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMensualidades);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        scrollpane.setBorder(javax.swing.BorderFactory.createTitledBorder("Log SMS"));

        txtLog.setColumns(20);
        txtLog.setRows(5);
        scrollpane.setViewportView(txtLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel6.add(scrollpane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        btnActualizar.setText("Actualizar talones pagos");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnActualizar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblMensualidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMensualidadesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMensualidadesMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        new Thread() {
            @Override
            public void run() {
                try {
                    txtLog.append("\nConsultando talones CobrosYa Pagos....");
                    ConsultaTalonesCobrosYa consultaTalonesCobrosYa = new ConsultaTalonesCobrosYa();
                    List<Mensualidades> talonesPagos = consultaTalonesCobrosYa.enviarTalonMiWeb(dpVencimiento.getDate());
                    txtLog.append("\n Se cargaron " + talonesPagos.size() + " talones pagos");
                    txtLog.append("\n------------------------");
                    for (Mensualidades talonPago : talonesPagos) {
                        txtLog.append("\nTalón CobrosYa: " + talonPago.getNroTalonCobrosYa() + ", Mensualidad nro: " + talonPago.getId() + ", Medio de Pago: " + talonPago.getMedioPago());

                        mensualidadesDAO = new MensualidadesDAO();
                        Mensualidades m = (Mensualidades) mensualidadesDAO.BuscaPorID(Mensualidades.class, talonPago.getId());
                        txtLog.append("\nActualizando cuenta Socio " + m.getSocio());

                        m.setFechaHoraTransaccionCobrosYa(talonPago.getFechaHoraTransaccionCobrosYa());
                        m.setFechaPago(talonPago.getFechaHoraTransaccionCobrosYa());
                        m.setMedioPago(talonPago.getMedioPago());
                        m.setMedioPagoId(talonPago.getMedioPagoId());
                        m.setPago("Pago");

                        mensualidadesDAO = new MensualidadesDAO();
                        mensualidadesDAO.Actualizar(m);

                        txtLog.append("\nListo!");
                        txtLog.append("\n------------------------");
                        txtLog.setCaretPosition(txtLog.getDocument().getLength());

                    }
                    muestraContenidoTabla();
                } catch (Exception ex) {
                    ex.printStackTrace();

                }

            }
        }.start();


    }//GEN-LAST:event_btnActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.jdesktop.swingx.JXDatePicker dpVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbPagos;
    private javax.swing.JRadioButton rbPendientes;
    private javax.swing.JRadioButton rbTodos;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable tblMensualidades;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
}
