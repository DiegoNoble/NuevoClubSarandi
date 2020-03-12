package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.SocioDAO;
import com.club.control.utilidades.LeeProperties;
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

public class DependienteFrameCompleto extends javax.swing.JInternalFrame {

    DependienteFrameCompleto seteaSocioTitular;
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
    LeeProperties props = new LeeProperties();

    public DependienteFrameCompleto() {
        initComponents();
        dpFechaNacimiento.setFormats("dd/MM/yyyy");
        btnEliminar.setVisible(false);
        btnEliminar.setVisible(false);
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
        
        if (tblDependiente.getSelectedRow() != -1) {
            try {
                depDAO = new DepDAO();
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
                if (depSeleccionado.getCalidad() == null) {
                    lblStatusHuella.setText("Por favor registre la huella del socio");
                    lblStatusHuella.setForeground(Color.red);
                } else {
                    lblStatusHuella.setText("Socio posee huella registrada correctamente!");
                    lblStatusHuella.setForeground(Color.GREEN);
                }
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
        btnActualizaDependientes.setEnabled(false);
        btnCarneDependiente.setEnabled(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        ftxtCI.setEditable(false);
        cbSexo.setEnabled(false);
        cbSituacion.setEnabled(false);
        dpFechaNacimiento.setEnabled(false);
        dpFechaNacimiento.setEditable(false);
        tblDependiente.setEnabled(true);
        tblDependiente.setVisible(true);
        txtAreaHistorico.setEditable(false);
        cbParentesco.setEditable(false);
        btnActualizaDependientes.setEnabled(true);
        btnCarneDependiente.setEnabled(true);
    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnBuscaSocioTitular.setEnabled(false);
        btnBuscarFoto.setEnabled(false);
    }

    private void habilitaBotones() {

        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscaSocioTitular.setEnabled(true);
        btnBuscarFoto.setEnabled(true);

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
        jPanel4 = new javax.swing.JPanel();
        btnActualizaDependientes = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCarneDependiente = new org.jasper.viewer.components.JasperRunnerButton();
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
        btnBuscaSocioTitular = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbSituacion = new javax.swing.JComboBox();
        txtAreaHistorico = new java.awt.TextArea();
        jLabel22 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbParentesco = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        imagePanel1 = new imagepanel.ImagePanel();
        btnHuella = new javax.swing.JButton();
        lblStatusHuella = new javax.swing.JLabel();

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

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnActualizaDependientes.setText("Actualizar Dependientes Inactivos");
        btnActualizaDependientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizaDependientesActionPerformed(evt);
            }
        });
        jPanel4.add(btnActualizaDependientes);

        btnNuevo.setText("Nuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo);

        btnEditar.setText("Editar"); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditar);

        btnEliminar.setText("Eliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        btnSalvar.setText("Salvar"); // NOI18N
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        btnCarneDependiente.setText("Carne Socio");
        btnCarneDependiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarneDependienteActionPerformed(evt);
            }
        });
        jPanel4.add(btnCarneDependiente);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

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

        btnBuscaSocioTitular.setText("..."); // NOI18N
        btnBuscaSocioTitular.setEnabled(false);
        btnBuscaSocioTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaSocioTitularActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(btnBuscaSocioTitular, gridBagConstraints);

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

        imagePanel1.setBackground(new java.awt.Color(255, 102, 102));
        imagePanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        imagePanel1.setForeground(new java.awt.Color(102, 255, 51));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        btnHuella.setText("Captura y guarda huella");
        btnHuella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuellaActionPerformed(evt);
            }
        });

        lblStatusHuella.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHuella)
                    .addComponent(lblStatusHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnHuella)
                        .addGap(18, 18, 18)
                        .addComponent(lblStatusHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jTabbedPane1.addTab("Huella", jPanel7);

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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        habilitaCampos();
        limpiaCampos();
        habilitaBotones();
        txtNombre.requestFocus();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (txtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            txtNombre.requestFocus();

        } else if (ftxtCI.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la CI del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else if (txtCodigoTitular.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el titular del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        } else {

            if (jlCodigoDependiente.getText().equals("")) {

                try {

                    dependiente = new Dependiente();

                    dependiente.setNombre(txtNombre.getText());
                    dependiente.setFechanacimiento(dpFechaNacimiento.getDate());
                    dependiente.setSexo(cbSexo.getSelectedItem().toString());
                    dependiente.setCi(ftxtCI.getText());
                    dependiente.setParentesco(cbParentesco.getSelectedItem().toString());
                    dependiente.setSocio(socioTitular);
                    dependiente.setSituacion(cbSituacion.getSelectedItem().toString());
                    dependiente.setHistoria(txtAreaHistorico.getText());
                    dependiente.setFoto(txtFoto.getText());
                    depDAO = new DepDAO();
                    if ((depDAO.VerificaCI(ftxtCI.getText())) == true) {

                        JOptionPane.showMessageDialog(null, "El dependiente ya se encuentra en la base de datos!", "Error", JOptionPane.ERROR_MESSAGE);

                    } else {
                        depDAO = new DepDAO();
                        depDAO.Salvar(dependiente);
                        JOptionPane.showMessageDialog(null, "Dependiente registrado correctamente!");
                        buscaTodosLosRegistros();
                        muestraContenidoTbl();
                        desabilitaCampos();
                        desabilitaBotones();
                    }

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "No fue posible salvar el Dependiente" + error, "Error", JOptionPane.ERROR_MESSAGE);
                    error.printStackTrace();
                }

            } else {  //procedimento realizado cuando se desea alterar un registro

                if (txtNombre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese el nombre del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.requestFocus();

                } else if (ftxtCI.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la CI del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else if (txtCodigoTitular.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese el titular del dependiente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    try {

                        socioDAO = new SocioDAO();
                        depSeleccionado.setId(Integer.parseInt(jlCodigoDependiente.getText()));
                        depSeleccionado.setNombre(txtNombre.getText());
                        depSeleccionado.setFechanacimiento(dpFechaNacimiento.getDate());
                        depSeleccionado.setSexo(cbSexo.getSelectedItem().toString());
                        depSeleccionado.setCi(ftxtCI.getText());
                        depSeleccionado.setParentesco(cbParentesco.getSelectedItem().toString());
                        depSeleccionado.setSocio(socioDAO.BuscaPorCodigo(txtCodigoTitular.getText()));
                        depSeleccionado.setSituacion(cbSituacion.getSelectedItem().toString());
                        depSeleccionado.setHistoria(txtAreaHistorico.getText());
                        depSeleccionado.setFoto(txtFoto.getText());

                        depDAO = new DepDAO();
                        depDAO.Actualizar(depSeleccionado);

                        JOptionPane.showMessageDialog(null, "Dependiente editado correctamente!");

                        buscaTodosLosRegistros();
                        muestraContenidoTbl();
                        desabilitaBotones();
                        desabilitaCampos();

                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(null, "Error " + error, "Error", JOptionPane.ERROR_MESSAGE);
                        error.printStackTrace();
                    }
                }
            }

        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        desabilitaBotones();
        desabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (tblDependiente.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Dependiente en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {
            if (tblDependiente.getSelectedRow() != -1) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea excluir al Socio?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {

                    depDAO = new DepDAO();
                    depSeleccionado = listDependientes.get(tblDependiente.getSelectedRow());
                    depDAO.elminiar(Dependiente.class, depSeleccionado);

                }
            } else {

                buscaTodosLosRegistros();
                muestraContenidoTbl();

                JOptionPane.showMessageDialog(this, "Seleccione un Depedneinte en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + e);
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscaSocioTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaSocioTitularActionPerformed

        SocioFrameCompleto socioFrame = new SocioFrameCompleto(this); // instanciamos un frame Socio como parametro nuestro dependiente frame ques el q esta siendo usado en el momento (representado en la clausular .this)
        socioFrame.setVisible(true); // muestra el frame socio para seleccionar el socio titular
        this.getDesktopPane().add(socioFrame); // adiciona este frame a nuestro desktop pane
        try {
            socioFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DependienteFrameCompleto.class.getName()).log(Level.SEVERE, null, ex);
        }
        socioFrame.toFront();

    }//GEN-LAST:event_btnBuscaSocioTitularActionPerformed

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

    private void btnActualizaDependientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizaDependientesActionPerformed

        //actualizaSituacionDependientes();
        //muestraContenidoTabla();

    }//GEN-LAST:event_btnActualizaDependientesActionPerformed

    private void btnCarneDependienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarneDependienteActionPerformed

        String camino = txtFoto.getText();
        String caminoLogo = "Imagenes/Escudo.jpg";
        try {

            parametros = new HashMap();
            parametros.clear();
            parametros.put("id", depSeleccionado.getId());
            parametros.put("fotoDependiente", camino);
            parametros.put("logoClub", caminoLogo);
            btnCarneDependiente.setDatabaseDriver(props.getDriver());
            btnCarneDependiente.setDatabasePassword(props.getPsw());
            btnCarneDependiente.setDatabaseURL(props.getUrl());
            btnCarneDependiente.setDatabaseUser(props.getUsr());

            btnCarneDependiente.setReportParameters(parametros);
            btnCarneDependiente.setReportURL("/Reportes/carneDependiente.jasper");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al abrir el informe: " + ex);
        }

    }//GEN-LAST:event_btnCarneDependienteActionPerformed

    private void btnHuellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuellaActionPerformed

        bioMini = new BioMini();
        bioMini.iniciar();
        depDAO = new DepDAO();
        Dependiente d = (bioMini.BuscarHuellaDep(imagePanel1, depDAO.BuscaDepXHuella()));
        socioDAO = new SocioDAO();
        Socio s = (bioMini.BuscarHuellaSocioTitular(imagePanel1, socioDAO.BuscaSociosxHuella()));
        if (depSeleccionado != null) {
            if (d == null) {
                //Caso la huella no este registrada por otra persona
                bioMini.GuardarHuellaDep(imagePanel1, depSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella registrada correctamente!");

            } else if (depSeleccionado.getId().compareTo(d.getId()) == 0) {

                bioMini.GuardarHuellaDep(imagePanel1, depSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella actualizada correctamente!");

            } else if (s != null) {
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por un socio Titular: " + s.getId() + " " + s.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //huella ya registrada por otra persona
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por: " + d.getId() + " " + d.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);

            }

        }
    }//GEN-LAST:event_btnHuellaActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizaDependientes;
    private javax.swing.JButton btnBuscaSocioTitular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarFoto;
    private javax.swing.JButton btnCancelar;
    private org.jasper.viewer.components.JasperRunnerButton btnCarneDependiente;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHuella;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbParentesco;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbSituacion;
    private org.jdesktop.swingx.JXDatePicker dpFechaNacimiento;
    private javax.swing.JFormattedTextField ftxtCI;
    private imagepanel.ImagePanel imagePanel1;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlCodigoDependiente;
    private javax.swing.JLabel jlblFoto;
    private javax.swing.JLabel lblStatusHuella;
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
