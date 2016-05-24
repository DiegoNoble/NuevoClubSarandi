package com.club.views;

import com.club.control.acceso.conexion;
import com.club.control.utilidades.Constantes;
import com.club.control.utilidades.data;
import java.awt.Image;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author Diego
 */
public class DependienteFrameConsultaOLD extends javax.swing.JInternalFrame {

    conexion conexionDependiente, conexionSocio;
    MaskFormatter formatoFechaNacimiento;
    data formatodeCampoData;
    Constantes formataciondeCamposFecha;

    public DependienteFrameConsultaOLD() {
        initComponents();
        conexionDependiente = new conexion();
        conexionDependiente.conecta();
        conexionDependiente.ejecutaSQL("select * from tbdependiente");
        muestraContenidoTabla();


        // formataciondeCamposFecha.formataciondeCamposFecha(ftxtFechaNacimiento);
        formatodeCamposFecha(ftxtFechaNacimiento);
        //formatoCamposNumericos(ftxtCI);

    }

    private void muestraContenidoTabla() {

        if (rbNombre.isSelected()) {

            conexionDependiente.ejecutaSQL("select * from tbdependiente where nombre like'%" + txtFiltro.getText() + "%'");

        } else if (rbCodigo.isSelected()) {

            conexionDependiente.ejecutaSQL("select * from tbdependiente where id like'%" + txtFiltro.getText() + "%'");

        } else if (rbCI.isSelected()) {

            conexionDependiente.ejecutaSQL("select * from tbdependiente where ci like'%" + txtFiltro.getText() + "%'");

        }else if (rbCodTitular.isSelected()){

            conexionDependiente.ejecutaSQL("select * from tbdependiente where id_socio = '"+txtFiltro.getText()+"%'");
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tblDependiente.getModel();
        modelo.setNumRows(0);

        try {
            while (conexionDependiente.resultSet.next()) {
                Object[] linha = new Object[10];
                linha[0] = conexionDependiente.resultSet.getString("id");
                linha[1] = conexionDependiente.resultSet.getString("nombre");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date data = conexionDependiente.resultSet.getDate("fechanacimiento");
                if (data != null) {
                    linha[2] = formato.format(data);
                }
                linha[3] = conexionDependiente.resultSet.getString("ci");
                linha[4] = conexionDependiente.resultSet.getString("situacion");
                linha[5] = conexionDependiente.resultSet.getString("sexo");
                linha[6] = conexionDependiente.resultSet.getString("foto");
                linha[7] = conexionDependiente.resultSet.getString("id_socio");
                linha [8] = conexionDependiente.resultSet.getString("historia");
                linha[9] = conexionDependiente.resultSet.getString("parentesco");

                modelo.addRow(linha);

            }
        } catch (SQLException error) {

            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + error);
        }

    }

    private void formatodeCamposFecha(JFormattedTextField campo) {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####"); //macara del campo que será formatado
            //mascara.setValueContainsLiteralCharacters(false); //esta propiedad en false hace que al hacer un getValue ignore los caracteres del formato por ej. los /  /
            DefaultFormatterFactory formato = new DefaultFormatterFactory(mascara); //esta es la propiedad del JfTXT que contiene su formatación padron, indico que será el formato "mascara"
            campo.setFormatterFactory(formato); // seteamos el formato "mascara"
        } catch (ParseException ex) {
            Logger.getLogger(DependienteFrameCompleto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formatoCamposNumericos(JFormattedTextField campo) {
        try {
            MaskFormatter mascara = new MaskFormatter("##########");
            DefaultFormatterFactory formato = new DefaultFormatterFactory(mascara);
            campo.setFormatterFactory(formato);
        } catch (ParseException ex) {
            Logger.getLogger(DependienteFrameCompleto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void limpaCampos() {
        jlCodigoDependiente.setText("");
        txtNombre.setText("");
        ftxtCI.setText("");
        //cbSocioTitular.setSelectedItem("0");
        cbSexo.setSelectedItem("Masculino");
        cbSituacion.setSelectedItem("Activo");
        ftxtFechaNacimiento.setText("");
        txtCodigoTitular.setText("");
        txtNombreSocioTitular.setText("");
        txtAreaHistorico.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDependiente = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        rbCodigo = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCI = new javax.swing.JRadioButton();
        rbCodTitular = new javax.swing.JRadioButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ftxtFechaNacimiento = new javax.swing.JFormattedTextField();
        jlCodigoDependiente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFoto = new javax.swing.JTextField();
        ftxtCI = new javax.swing.JFormattedTextField();
        jlblFoto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoTitular = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombreSocioTitular = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbSituacion = new javax.swing.JComboBox();
        txtAreaHistorico = new java.awt.TextArea();
        jLabel22 = new javax.swing.JLabel();
        cbParentesco = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(850, 600));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dependientes"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltro, gridBagConstraints);

        tblDependiente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Socio", "Nombre", "Fecha Nacimiento", "C.I.", "Situacion", "Sexo", "Foto", "Codigo del Titular", "Historia", "Parentesco"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDependiente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblDependiente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDependienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDependiente);
        if (tblDependiente.getColumnModel().getColumnCount() > 0) {
            tblDependiente.getColumnModel().getColumn(5).setMinWidth(0);
            tblDependiente.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblDependiente.getColumnModel().getColumn(5).setMaxWidth(0);
            tblDependiente.getColumnModel().getColumn(6).setMinWidth(0);
            tblDependiente.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblDependiente.getColumnModel().getColumn(6).setMaxWidth(0);
            tblDependiente.getColumnModel().getColumn(8).setMinWidth(0);
            tblDependiente.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblDependiente.getColumnModel().getColumn(8).setMaxWidth(0);
            tblDependiente.getColumnModel().getColumn(9).setMinWidth(0);
            tblDependiente.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblDependiente.getColumnModel().getColumn(9).setMaxWidth(0);
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        buttonGroup1.add(rbCodigo);
        rbCodigo.setSelected(true);
        rbCodigo.setText("Código");
        jPanel6.add(rbCodigo);

        buttonGroup1.add(rbNombre);
        rbNombre.setText("Nombre");
        jPanel6.add(rbNombre);

        buttonGroup1.add(rbCI);
        rbCI.setText("Cédula");
        jPanel6.add(rbCI);

        buttonGroup1.add(rbCodTitular);
        rbCodTitular.setText("Cód. Titular");
        jPanel6.add(rbCodTitular);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jLabel2.setText("Código del Socio"); // NOI18N

        jLabel4.setText("Nombre"); // NOI18N

        txtNombre.setEditable(false);

        jLabel7.setText("Sexo"); // NOI18N

        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Femenino" }));
        cbSexo.setEnabled(false);

        jLabel13.setText("C.I."); // NOI18N

        jLabel14.setText("Fecha de Nacimiento"); // NOI18N

        ftxtFechaNacimiento.setEditable(false);

        jlCodigoDependiente.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jLabel6.setText("Foto"); // NOI18N

        txtFoto.setEditable(false);

        ftxtCI.setEditable(false);

        jlblFoto.setPreferredSize(new java.awt.Dimension(3, 4));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftxtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlCodigoDependiente, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(152, 152, 152)
                        .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(135, 135, 135))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ftxtCI, ftxtFechaNacimiento, jlCodigoDependiente, txtFoto});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbSexo, txtNombre});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jlCodigoDependiente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(ftxtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(71, 71, 71))
        );

        jTabbedPane1.addTab("Personal", jPanel3);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel8.setText("Código do Titular");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);

        txtCodigoTitular.setEditable(false);
        txtCodigoTitular.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCodigoTitularPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtCodigoTitular, gridBagConstraints);

        jLabel16.setText("Socio Titular"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel16, gridBagConstraints);

        txtNombreSocioTitular.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNombreSocioTitular, gridBagConstraints);

        jLabel5.setText("Situación"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        cbSituacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo", "Renuncia" }));
        cbSituacion.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbSituacion, gridBagConstraints);

        txtAreaHistorico.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtAreaHistorico, gridBagConstraints);

        jLabel22.setText("Historico del Socio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel22, gridBagConstraints);

        cbParentesco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hijo/a", "Cónyuge", "Varios" }));
        cbParentesco.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbParentesco, gridBagConstraints);

        jLabel9.setText("Parentesco");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        jTabbedPane1.addTab("Titular", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCodigoTitularPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCodigoTitularPropertyChange

        //DETECTO CAMBIOS EN EL txtCodigoTitular Y A PARTIR DE ENTONCES INDICO QUE HACER..
        //PARA TAL FIN UTILIZAMOS EL METODO DocumentListener  DEL PAQUETE javax.swing.event.DocumentListener; Y DETERMINAMOS EL DOCUMENT EVENT ATRAVES DE javax.swing.event.DocumentEvent

        txtCodigoTitular.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {

                try {
                    String sql = "select * from tbsocio where id like '" + txtCodigoTitular.getText() + "'";

                    conexionSocio = new conexion();
                    conexionSocio.conecta();
                    conexionSocio.ejecutaSQL(sql);  //realiza el codigo SQL de la busqueda
                    //conexionSocio.resultSet.first();
                    while (conexionSocio.resultSet.next()) {
                        txtNombreSocioTitular.setText(conexionSocio.resultSet.getString("nombre"));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void removeUpdate(DocumentEvent e) {

                try {
                    String sql = "select * from tbsocio where id like '" + txtCodigoTitular.getText() + "'";

                    conexionSocio = new conexion();
                    conexionSocio.conecta();
                    conexionSocio.ejecutaSQL(sql);  //realiza el codigo SQL de la busqueda

                    while (conexionSocio.resultSet.next()) {
                        txtNombreSocioTitular.setText(conexionSocio.resultSet.getString("nombre"));
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            public void changedUpdate(DocumentEvent e) {
                try {
                    String sql = "select * from tbsocio where id like '" + txtCodigoTitular.getText() + "'";

                    conexionSocio = new conexion();
                    conexionSocio.conecta();
                    conexionSocio.ejecutaSQL(sql);  //realiza el codigo SQL de la busqueda

                    while (conexionSocio.resultSet.next()) {
                        txtNombreSocioTitular.setText(conexionSocio.resultSet.getString("nombre"));
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });




    }//GEN-LAST:event_txtCodigoTitularPropertyChange

    private void tblDependienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDependienteMouseClicked

        limpaCampos();

        try {
            int filaSeleccionada = tblDependiente.getSelectedRow();

            jlCodigoDependiente.setText(tblDependiente.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(tblDependiente.getValueAt(filaSeleccionada, 1).toString());
            Object objFN = tblDependiente.getValueAt(filaSeleccionada, 2);
            if (objFN == null){
            }else{
                ftxtFechaNacimiento.setText(tblDependiente.getValueAt(filaSeleccionada, 2).toString());
            }
            Object objCI = tblDependiente.getValueAt(filaSeleccionada, 3);
            if (objCI == null){
            }else{
                ftxtCI.setText(tblDependiente.getValueAt(filaSeleccionada, 3).toString());
            }
            cbSituacion.setSelectedItem(tblDependiente.getValueAt(filaSeleccionada, 4));
            cbSexo.setSelectedItem(tblDependiente.getValueAt(filaSeleccionada, 5));
            Object objFoto = tblDependiente.getValueAt(filaSeleccionada, 6);
            if (objFoto == null) {
            }else{
                ImageIcon fot = new ImageIcon(tblDependiente.getValueAt(filaSeleccionada, 6).toString());
                Icon icono = new ImageIcon(fot.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
                jlblFoto.setIcon(icono);
                txtFoto.setText(tblDependiente.getValueAt(filaSeleccionada, 6).toString());
            }

            txtCodigoTitular.setText(tblDependiente.getValueAt(filaSeleccionada, 7).toString());

            Object objHis = tblDependiente.getValueAt(filaSeleccionada, 8);
            if (objHis == null) {
            } else {
                txtAreaHistorico.setText(tblDependiente.getValueAt(filaSeleccionada, 8).toString());
            }

            Object objPar = tblDependiente.getValueAt(filaSeleccionada, 9);
            if (objPar == null) {
            } else {
                cbParentesco.setSelectedItem(tblDependiente.getValueAt(filaSeleccionada, 9).toString());
            }

            // actualizaComboBoxCategoria();
            // actualizaComboCobrador();

        } catch (Exception error) {
        }
    }//GEN-LAST:event_tblDependienteMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbParentesco;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbSituacion;
    private javax.swing.JFormattedTextField ftxtCI;
    private javax.swing.JFormattedTextField ftxtFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlCodigoDependiente;
    private javax.swing.JLabel jlblFoto;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodTitular;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblDependiente;
    private java.awt.TextArea txtAreaHistorico;
    private javax.swing.JTextField txtCodigoTitular;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtFoto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreSocioTitular;
    // End of variables declaration//GEN-END:variables

    //private JTextField txtCodigoTitular = new JTextField();
    //private JTextArea txtAreaCodigoTitular = new JTextArea();
    public void settxtCodigoTitular(String Codigo) {
        txtCodigoTitular.setText(Codigo);
    }
}
