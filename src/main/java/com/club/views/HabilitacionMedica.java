package com.club.views;

import Utilidades.Utilidades;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Caja;
import com.club.BEANS.FichaMedica;
import com.club.BEANS.Parametros;
import com.club.BEANS.Socio;
import com.club.BEANS.Usuario;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.FichaMedicaDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.SocioDAO;
import com.club.control.utilidades.LeeProperties;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public final class HabilitacionMedica extends javax.swing.JInternalFrame {

    private SocioDAO socioDAO;
    private MensualidadesDAO mensualidadesDAO;
    private FichaMedicaDAO fichaMedicaDAO;
    private List<Socio> listSocios;
    private Socio socio;
    private FichaMedica FichaMedica;
    private DefaultTableModel tblModel;
    private ListSelectionModel listModelSocios;
    private Socio socioSeleccionado;
    private HashMap parametros;
    private ParametrosDAO parametrosDAO;
    Caja movimientoCaja;
    CajaDAO cajaDAO;
    Usuario usuario;
    LeeProperties props = new LeeProperties();

    public HabilitacionMedica(Usuario usuario) {
        initComponents();
        this.usuario = usuario;
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        muestraContenidoTbl();
        txtFechaEmision.setFormats("dd/MM/yyyy");
        txtFechaEmision.setDate(new Date());

    }

    private void buscaTodosLosRegistros() {
        socioDAO = new SocioDAO();
        listSocios = socioDAO.BuscaTodos(Socio.class);

    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblSocio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = (DefaultTableModel) tblSocio.getModel();

        listModelSocios = tblSocio.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocio.getSelectedRow() != -1) {

                        socioSeleccionado = listSocios.get(tblSocio.getSelectedRow());
                    }
                }
            }
        });

    }

    private void muestraContenidoTbl() {

        try {

            tblModel.setNumRows(0);

            for (Socio socio : listSocios) {
                tblModel.addRow(new Object[]{
                    socio.getId(),
                    socio.getNombre(),
                    socio.getCi(),
                    socio.getSituacion()});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar los socios" + e);
            e.printStackTrace();
        }
    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            socio = new Socio();
            socio = (Socio) socioDAO.BuscaPorID(Socio.class, Integer.parseInt(txtFiltro.getText()));
            listSocios.clear();
            listSocios.add(socio);
            muestraContenidoTbl();

        } else if (rbCI.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaPorCI(txtFiltro.getText());
            muestraContenidoTbl();

        } else if (rbNombre.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaPorNombre(txtFiltro.getText());
            muestraContenidoTbl();

        }

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
        txtFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSocio = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbCodigo = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCI = new javax.swing.JRadioButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtFechaEmision = new org.jdesktop.swingx.JXDatePicker();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 700));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fichas Medicas Titulares"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Busqueda por:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltro, gridBagConstraints);

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "C.I.", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSocio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblSocio);
        if (tblSocio.getColumnModel().getColumnCount() > 0) {
            tblSocio.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        grupoBusqueda.add(rbCodigo);
        rbCodigo.setText("Código");
        jPanel4.add(rbCodigo);

        grupoBusqueda.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");
        jPanel4.add(rbNombre);

        grupoBusqueda.add(rbCI);
        rbCI.setText("Cédula");
        jPanel4.add(rbCI);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel14.setText("Fecha de Emisión"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFechaEmision, gridBagConstraints);

        jButton1.setText("Ficha médica");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new java.awt.GridBagConstraints());

        jTabbedPane1.addTab("", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            parametros.clear();
            if (socioSeleccionado.getSituacion().equals("Inactivo")) {
                JOptionPane.showMessageDialog(null, "Socio Inactivo, active su situación para poder emitir ficha");
                parametros.clear();
            } else if (socioSeleccionado.getSituacion().equals("Renuncia")) {
                JOptionPane.showMessageDialog(null, "El socio posee estado de renuncia");
                parametros.clear();
            } else if (socioSeleccionado.getSituacion().equals("Activo")) {
                mensualidadesDAO = new MensualidadesDAO();

                if (mensualidadesDAO.VerificaCantidadVencimientos(socioSeleccionado, 2) == false) {
                    JOptionPane.showMessageDialog(null, "El socio tiene mensualidades pendientes, no es posible emitir la Ficha Médica");

                    parametros.clear();

                } else {
                    FichaMedica = new FichaMedica();
                    FichaMedica.setSocio(socioSeleccionado);
                    FichaMedica.setFechaEmision(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaEmision.getDate()));
                    Date fecha = txtFechaEmision.getDate();
                    Calendar fechaVencimiento = Calendar.getInstance();
                    fechaVencimiento.setTime(fecha);

                    parametrosDAO = new ParametrosDAO();
                    Parametros parametrosSistema = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
                    fechaVencimiento.add(Calendar.DAY_OF_MONTH, parametrosSistema.getVencimientoFicha());

                    FichaMedica.setFechaVencimiento(fechaVencimiento.getTime());
                    fichaMedicaDAO = new FichaMedicaDAO();
                    fichaMedicaDAO.Salvar(FichaMedica);

                    movimientoCaja = new Caja();
                    movimientoCaja.setConcepto("Ficha médica: " + FichaMedica.getId() + ", socio," + FichaMedica.getSocio());

                    if (socioSeleccionado.getCategoria().getId() == 105) {
                        parametros.put("valor", new Double(100.00));
                        movimientoCaja.setEntrada(100.00);
                    } else {
                        movimientoCaja.setEntrada(parametrosSistema.getValorFicha());
                        parametros.put("valor", parametrosSistema.getValorFicha());
                    }
                    movimientoCaja.setRubro(parametrosSistema.getRubroFichaMedica());
                    movimientoCaja.setSalida(0.0);
                    movimientoCaja.setSectores(parametrosSistema.getSectorFicha());
                    movimientoCaja.setUsuario(usuario.getNombre());
                    movimientoCaja.setFechaMovimiento(new Date());

                    cajaDAO = new CajaDAO();
                    cajaDAO.Salvar(movimientoCaja);

                    ajustaSaldos(buscaSaldoAnteriorFecha(new Date()));

                    JOptionPane.showMessageDialog(null, "Ficha aprovada, precione OK para imprimir.");

                    parametros.put("logoClub", "Imagenes/EscudoBN.png");
                    parametros.put("idFicha", FichaMedica.getId());

                    Connection conexion = DriverManager.getConnection(props.getUrl(), props.getUsr(), props.getPsw());

                    InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/fichaMedica.jasper");
                    JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, conexion);
                    JasperViewer reporte = new JasperViewer(jasperPrint, false);
                    reporte.setVisible(true);

                    reporte.toFront();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar la ficha");
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblSocio;
    private org.jdesktop.swingx.JXDatePicker txtFechaEmision;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
