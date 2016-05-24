package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.SocioDAO;
import com.club.huellas.BioMini;
import java.awt.Color;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class DependienteFrameConsulta extends javax.swing.JInternalFrame {

    DependienteFrameConsulta seteaSocioTitular;
    DepDAO depDAO;
    SocioDAO socioDAO;
    CobradorDAO cobradorDAO;
    CategoriaDAO categoriaDAO;
    List<Dependiente> listDependientes;
    Dependiente dependiente;
    Socio socioTitular;
    DefaultTableModel tblModel;
    ListSelectionModel listModelDependiente;
    ImageIcon foto;
    Icon icono;
    Dependiente depSeleccionado;
    HashMap parametros;
    BioMini bioMini;

    public DependienteFrameConsulta() {
        initComponents();
        dpFechaNacimiento.setFormats("dd/MM/yyyy");
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        muestraContenidoTbl();

    }

    public Socio getSocioTitular() {
        return socioTitular;
    }

    public void setSocioTitular(Socio socioTitular) {
        this.socioTitular = socioTitular;
        txtNombreSocioTitular.setText(socioTitular.getNombre());
        txtCodigoTitular.setText(socioTitular.getId().toString());
    }

    private void buscaTodosLosRegistros() {
        depDAO = new DepDAO();
        listDependientes = depDAO.BuscaTodos(Dependiente.class);

    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblDependiente.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = (DefaultTableModel) tblDependiente.getModel();

        listModelDependiente = tblDependiente.getSelectionModel();
        listModelDependiente.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblDependiente.getSelectedRow() != -1) {

                        depSeleccionado = listDependientes.get(tblDependiente.getSelectedRow());
                    }
                    muestraDetalles();
                }
            }
        });

    }

    private void muestraContenidoTbl() {

        try {

            tblModel.setNumRows(0);

            for (Dependiente dep : listDependientes) {
                tblModel.addRow(new Object[]{
                    dep.getId(),
                    dep.getNombre(),
                    dep.getFechanacimiento(),
                    dep.getCi(),
                    dep.getSituacion(), //dep.getSocio().getNombre()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar los socios" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {

        limpiaCampos();
        depDAO = new DepDAO();
        if (tblDependiente.getSelectedRow() != -1) {
            try {
                depSeleccionado = depDAO.BuscaTitular(depSeleccionado);
                txtCodigoTitular.setText(depSeleccionado.getSocio().getId().toString());
                txtNombreSocioTitular.setText(depSeleccionado.getNombre());
                jlCodigoDependiente.setText(depSeleccionado.getId().toString());
                dpFechaNacimiento.setDate(depSeleccionado.getFechanacimiento());
                txtNombre.setText(depSeleccionado.getNombre());
                ftxtCI.setText(depSeleccionado.getCi());
                cbSituacion.setSelectedItem(depSeleccionado.getSituacion());
                cbSexo.setSelectedItem(depSeleccionado.getSexo());
                foto = new ImageIcon(depSeleccionado.getFoto());
                icono = new ImageIcon(foto.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
                jlblFoto.setIcon(icono);
                txtFoto.setText(depSeleccionado.getFoto());
               
                cbParentesco.setSelectedItem(depSeleccionado.getParentesco());

                txtAreaHistorico.setText(depSeleccionado.getHistoria());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles" + error, "Error", JOptionPane.ERROR_MESSAGE);
                error.printStackTrace();
            }
        }

    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            depDAO = new DepDAO();
            dependiente = (Dependiente) depDAO.BuscaPorID(Dependiente.class, Integer.parseInt(txtFiltro.getText()));
            listDependientes.clear();
            listDependientes.add(dependiente);
            muestraContenidoTbl();

        } else if (rbCI.isSelected()) {
            depDAO = new DepDAO();
            listDependientes = depDAO.BuscaPorCI(txtFiltro.getText());
            muestraContenidoTbl();

        } else if (rbNombre.isSelected()) {
            depDAO = new DepDAO();
            listDependientes = depDAO.BuscaPorNombre(txtFiltro.getText());
            muestraContenidoTbl();

        } else if (rbCodTitular.isSelected()) {
            SocioDAO socioDAO = new SocioDAO();
            Socio socio = socioDAO.BuscaPorCodigo(txtFiltro.getText());
            depDAO = new DepDAO();
            listDependientes = depDAO.BuscaPorCodigodeSocio(socio);

            muestraContenidoTbl();

        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        ftxtCI.setEditable(true);
        cbSexo.setEnabled(true);
        dpFechaNacimiento.setEditable(true);
        dpFechaNacimiento.setEnabled(true);
        cbSituacion.setEnabled(true);
        tblDependiente.setEnabled(false);
        tblDependiente.setVisible(false);
        txtAreaHistorico.setEditable(true);
        cbParentesco.setEditable(true);
        cbParentesco.setEnabled(true);
    }

   

    private void limpiaCampos() {
        txtNombre.setText("");
        ftxtCI.setText("");
        cbSexo.setSelectedItem("Masculino");
        cbSituacion.setSelectedItem("Activo");
        txtCodigoTitular.setText("");
        txtNombreSocioTitular.setText("");
        txtAreaHistorico.setText("");
        jlblFoto.setIcon(null);
        txtFoto.setText("");
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
        jlCodigoDependiente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFoto = new javax.swing.JTextField();
        btnBuscarFoto = new javax.swing.JButton();
        ftxtCI = new javax.swing.JFormattedTextField();
        jlblFoto = new javax.swing.JLabel();
        dpFechaNacimiento = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoTitular = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombreSocioTitular = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbSituacion = new javax.swing.JComboBox();
        txtAreaHistorico = new java.awt.TextArea();
        jLabel22 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbParentesco = new javax.swing.JComboBox();

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

        tblDependiente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código del Socio", "Nombre", "Fecha Nacimiento", "C.I.", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblDependiente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblDependiente);
        if (tblDependiente.getColumnModel().getColumnCount() > 0) {
            tblDependiente.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblDependiente.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblDependiente.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblDependiente.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblDependiente.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblDependiente.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
            tblDependiente.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblDependiente.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
            tblDependiente.getColumnModel().getColumn(4).setPreferredWidth(20);
            tblDependiente.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
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

        jlCodigoDependiente.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jLabel6.setText("Foto"); // NOI18N

        txtFoto.setEditable(false);

        btnBuscarFoto.setText("..."); // NOI18N
        btnBuscarFoto.setEnabled(false);
        btnBuscarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFotoActionPerformed(evt);
            }
        });

        ftxtCI.setEditable(false);

        jlblFoto.setPreferredSize(new java.awt.Dimension(3, 4));

        dpFechaNacimiento.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCodigoDependiente, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addComponent(btnBuscarFoto))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(198, 198, 198))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCodigoDependiente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFoto))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dpFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftxtCI, jlCodigoDependiente, txtNombre});

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

        jLabel9.setText("Parentesco");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        cbParentesco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hijo/a", "Cónyuge", "Varios" }));
        cbParentesco.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbParentesco, gridBagConstraints);

        jTabbedPane1.addTab("Titular", jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnBuscarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFotoActionPerformed

        try {
            JFileChooser buscarFoto = new JFileChooser();
            buscarFoto.setCurrentDirectory(new File("C:/Fotos Socios/"));
            buscarFoto.setDialogTitle("Cargar Foto del Socio");
            buscarFoto.showOpenDialog(this);
            String foto = "C:/Fotos Socios/" + buscarFoto.getSelectedFile().getName();
            txtFoto.setText(foto);

            ImageIcon fot = new ImageIcon(foto);
            Icon icono = new ImageIcon(fot.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
            jlblFoto.setIcon(icono);
            this.repaint();

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar la foto" + error);
        }
    }//GEN-LAST:event_btnBuscarFotoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarFoto;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbParentesco;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbSituacion;
    private org.jdesktop.swingx.JXDatePicker dpFechaNacimiento;
    private javax.swing.JFormattedTextField ftxtCI;
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
