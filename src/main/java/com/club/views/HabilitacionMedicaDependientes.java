package com.club.views;

import Utilidades.Utilidades;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Dependiente;
import com.club.BEANS.FichaMedicaDependientes;
import com.club.BEANS.Socio;
import com.club.DAOs.DepDAO;
import com.club.DAOs.FichaMedicaDependienteDAO;
import com.club.DAOs.MensualidadesDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class HabilitacionMedicaDependientes extends javax.swing.JInternalFrame {

    private MensualidadesDAO mensualidadesDAO;
    private FichaMedicaDependienteDAO fmDepDAO;
    private List<Dependiente> listDependientes;
    private Dependiente dependiente;
    private FichaMedicaDependientes FichaMedicaDep;
    private DefaultTableModel tblModel;
    private ListSelectionModel listModelSocios;
    private Dependiente dependienteSelecionado;
    private HashMap parametros;
    private DepDAO dependienteDAO;

    public HabilitacionMedicaDependientes() {
        initComponents();
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        muestraContenidoTbl();
        txtFechaEmision.setFormats("dd/MM/yyyy");
        txtFechaEmision.setDate(new Date());

    }

    private void buscaTodosLosRegistros() {
        dependienteDAO = new DepDAO();
        listDependientes = dependienteDAO.BuscaTodos(Dependiente.class);
        //listDependientes = dependienteDAO.BuscaTodosMasRapido();
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblDependientes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = (DefaultTableModel) tblDependientes.getModel();

        listModelSocios = tblDependientes.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblDependientes.getSelectedRow() != -1) {

                        dependienteSelecionado = listDependientes.get(tblDependientes.getSelectedRow());
                    }
                    muestraDetalles();
                }
            }
        });

    }

    private void muestraContenidoTbl() {


        try {

            tblModel.setNumRows(0);

            for (Dependiente dependiente : listDependientes) {
                tblModel.addRow(new Object[]{
                            dependiente.getId(),
                            dependiente.getNombre(),
                            dependiente.getCi(),
                            dependiente.getSituacion()});
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar los registros" + e);
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {

        limpiaCampos();

        if (tblDependientes.getSelectedRow() != -1) {
            try {

                jlCodigoSocio.setText(dependienteSelecionado.getId().toString());
                txtNombre.setText(dependienteSelecionado.getNombre());
                ftxtCI.setText(dependienteSelecionado.getCi());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles" + error);
                error.printStackTrace();
            }
        }

    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            dependiente = new Dependiente();
            dependiente = (Dependiente) dependienteDAO.BuscaPorID(Dependiente.class, Integer.parseInt(txtFiltro.getText()));
            listDependientes.clear();
            listDependientes.add(dependiente);
            muestraContenidoTbl();

        } else if (rbCI.isSelected()) {
            dependienteDAO = new DepDAO();
            listDependientes = dependienteDAO.BuscaPorCI(txtFiltro.getText());
            muestraContenidoTbl();

        } else if (rbNombre.isSelected()) {
            dependienteDAO = new DepDAO();
            listDependientes = dependienteDAO.BuscaPorNombre(txtFiltro.getText());
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
        tblDependientes = new javax.swing.JTable();
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
        fichaMedica = new org.jasper.viewer.components.JasperRunnerButton();

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
        jLabel1.setText("Fichas Medicas Dependientes"); // NOI18N
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

        tblDependientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Socio", "Nombre", "C.I.", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
        tblDependientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblDependientes);
        tblDependientes.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblDependientes.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblDependientes.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
        tblDependientes.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());

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
        jPanel3.add(txtFechaEmision, gridBagConstraints);

        jTabbedPane1.addTab("Personal", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        fichaMedica.setText("Ficha Medica");
        fichaMedica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fichaMedicaActionPerformed(evt);
            }
        });
        jPanel6.add(fichaMedica);

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

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed

        

    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void fichaMedicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fichaMedicaActionPerformed

        try {

            if (dependienteSelecionado.getSituacion().equals("Inactivo")) {
                JOptionPane.showMessageDialog(null, "Dependiente Inactivo, verifique la situacion de su Titular");
                parametros.clear();
                fichaMedica.setReportURL(null);
            } else if (dependienteSelecionado.getSituacion().equals("Renuncia")) {
                JOptionPane.showMessageDialog(null, "El dependiente posee estado de renuncia, verifique estado de su Titular");
                parametros.clear();
                fichaMedica.setReportURL(null);
            } else if (dependienteSelecionado.getSituacion().equals("Activo")) {
                Socio socioTitular = dependienteSelecionado.getSocio();
                mensualidadesDAO = new MensualidadesDAO();

                if (mensualidadesDAO.VerificaCantidadVencimientos(socioTitular) == false ) {
                    JOptionPane.showMessageDialog(null, "El Titular tiene mensualidades pendientes, no es posible emitir la Ficha Médica");
                    parametros.clear();
                    fichaMedica.setReportURL(null);
                } else {
                    FichaMedicaDep = new FichaMedicaDependientes();
                    FichaMedicaDep.setDependiente(dependienteSelecionado);
                    FichaMedicaDep.setFechaEmision(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaEmision.getDate()));
                    Date fecha = txtFechaEmision.getDate();
                    Calendar fechaVencimiento = Calendar.getInstance();
                    fechaVencimiento.setTime(fecha);
                    fechaVencimiento.add(Calendar.DAY_OF_MONTH, 40);
                    FichaMedicaDep.setFechaVencimiento(fechaVencimiento);
                    fmDepDAO = new FichaMedicaDependienteDAO();
                    fmDepDAO.Salvar(FichaMedicaDep);

                    JOptionPane.showMessageDialog(null, "Ficha aprovada, precione OK para imprimir.");
                    parametros.clear();
                    parametros.put("logoClub", "./Imagenes/Escudo.jpg");
                    parametros.put("idDependiente", dependienteSelecionado.getId());
                    fichaMedica.setReportParameters(parametros);
                    fichaMedica.setReportURL("/Reportes/fichaMedicadeDependientes.jasper");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar la ficha");
            ex.printStackTrace();
        }

    }//GEN-LAST:event_fichaMedicaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private org.jasper.viewer.components.JasperRunnerButton fichaMedica;
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
    private javax.swing.JTable tblDependientes;
    private org.jdesktop.swingx.JXDatePicker txtFechaEmision;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
