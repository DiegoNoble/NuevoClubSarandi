package com.club.views;

import Utilidades.Utilidades;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.FichaMedica;
import com.club.BEANS.Socio;
import com.club.DAOs.FichaMedicaDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
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

public final class HabilitacionMedicaTitulares extends javax.swing.JInternalFrame {

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

    public HabilitacionMedicaTitulares() {
        initComponents();
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
                    muestraDetalles();
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

    private void muestraDetalles() {

        limpiaCampos();

        if (tblSocio.getSelectedRow() != -1) {
            try {

                jlCodigoSocio.setText(socioSeleccionado.getId().toString());
                txtNombre.setText(socioSeleccionado.getNombre());
                ftxtCI.setText(socioSeleccionado.getCi());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles" + error);
                error.printStackTrace();
            }
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

    private void limpiaCampos() {

        jlCodigoSocio.setText("");
        txtNombre.setText("");
        ftxtCI.setText("");

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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlCodigoSocio = new javax.swing.JLabel();
        ftxtCI = new javax.swing.JFormattedTextField();
        txtFechaEmision = new org.jdesktop.swingx.JXDatePicker();
        jPanel6 = new javax.swing.JPanel();
        ImprimirReporte = new org.jasper.viewer.components.JasperRunnerButton();

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
        tblSocio.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());

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

        jLabel2.setText("Código del Socio"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        txtNombre.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtNombre, gridBagConstraints);

        jLabel13.setText("C.I."); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel13, gridBagConstraints);

        jLabel14.setText("Fecha de Emisión"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel14, gridBagConstraints);

        jlCodigoSocio.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jlCodigoSocio, gridBagConstraints);

        ftxtCI.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftxtCI, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFechaEmision, gridBagConstraints);

        jTabbedPane1.addTab("Personal", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        ImprimirReporte.setText("Ficha Medica");
        ImprimirReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImprimirReporteActionPerformed(evt);
            }
        });
        jPanel6.add(ImprimirReporte);

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

    private void ImprimirReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImprimirReporteActionPerformed

        try {

            if (socioSeleccionado.getSituacion().equals("Inactivo")) {
                JOptionPane.showMessageDialog(null, "Socio Inactivo, active su situación para poder emitir mensualidades");
                parametros.clear();
                ImprimirReporte.setReportURL(null);
            } else if (socioSeleccionado.getSituacion().equals("Renuncia")) {
                JOptionPane.showMessageDialog(null, "El socio posee estado de renuncia");
                parametros.clear();
                ImprimirReporte.setReportURL(null);
            } else if (socioSeleccionado.getSituacion().equals("Activo")) {
                mensualidadesDAO = new MensualidadesDAO();

                 if (mensualidadesDAO.VerificaCantidadVencimientos(socioSeleccionado) == false ) {
                    JOptionPane.showMessageDialog(null, "El socio tiene mensualidades pendientes, no es posible emitir la Ficha Médica");

                    parametros.clear();
                    ImprimirReporte.setReportURL(null);

                } else {
                    FichaMedica = new FichaMedica();
                    FichaMedica.setSocio(socioSeleccionado);
                    FichaMedica.setFechaEmision(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaEmision.getDate()));
                    Date fecha = txtFechaEmision.getDate();
                    Calendar fechaVencimiento = Calendar.getInstance();
                    fechaVencimiento.setTime(fecha);
                    fechaVencimiento.add(Calendar.DAY_OF_MONTH, 40);
                    FichaMedica.setFechaVencimiento(fechaVencimiento);
                    fichaMedicaDAO = new FichaMedicaDAO();
                    fichaMedicaDAO.Salvar(FichaMedica);

                    JOptionPane.showMessageDialog(null, "Ficha aprovada, precione OK para imprimir.");
                    parametros.clear();
                    parametros.put("logoClub", "./imagenes/Escudo 2.jpg");
                    parametros.put("idSocio", socioSeleccionado.getId());
                    ImprimirReporte.setReportParameters(parametros);
                    ImprimirReporte.setReportURL("/Reportes/fichaMedica.jasper");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar la ficha");
            e.printStackTrace();
        }

    }//GEN-LAST:event_ImprimirReporteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jasper.viewer.components.JasperRunnerButton ImprimirReporte;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JFormattedTextField ftxtCI;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlCodigoSocio;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblSocio;
    private org.jdesktop.swingx.JXDatePicker txtFechaEmision;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
