package com.club.views;

import Utilidades.Utilidades;
import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Categoria;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.SocioDAO;
import com.club.huellas.BioMini;
import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class SocioFrameCompleto extends javax.swing.JInternalFrame {

    private DependienteFrameCompleto seteaSocioTitular;
    private SocioDAO socioDAO;
    private CobradorDAO cobradorDAO;
    private CategoriaDAO categoriaDAO;
    private List<Socio> listSocios;
    private Socio socio;
    private DefaultTableModel tblModel;
    private ListSelectionModel listModelSocios;
    private ImageIcon foto;
    private Icon icono;
    private Socio socioSeleccionado;
    private HashMap parametros;
    BioMini bioMini;

    public SocioFrameCompleto() {
        initComponents();

        btnSeleccionaTitular.setVisible(false);
        btnEliminar.setVisible(false);
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        cargaComboBox();
        muestraContenidoTbl();

    }

     public SocioFrameCompleto(Boolean consulta) {
        initComponents();

        btnSeleccionaTitular.setVisible(false);
        btnEliminar.setVisible(false);
        btnSalvar.setVisible(false);
        btnCancelar.setVisible(false);
        btnNuevo.setVisible(false);
        btnEditar.setVisible(false);
        btnEliminar.setVisible(false);
        btnFoto.setVisible(false);
        
        
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        cargaComboBox();
        muestraContenidoTbl();

    }
     
    public SocioFrameCompleto(DependienteFrameCompleto dependienteFrame) {
        initComponents();

        btnSeleccionaTitular.setVisible(true);
        btnEliminar.setVisible(false);
        btnFicha.setVisible(false);
        btnCarneSocio.setVisible(false);

        DefineModeloTbl();
        buscaTodosLosRegistros();

        this.seteaSocioTitular = dependienteFrame;

        cargaComboBox();
        muestraContenidoTbl();

    }

    private void cargaComboBox() {

        try {
            cobradorDAO = new CobradorDAO();
            List<Cobrador> listCobradores = cobradorDAO.BuscaTodos(Cobrador.class);
            for (Cobrador cobrador : listCobradores) {
                cbCobrador.addItem(cobrador);
            }

            categoriaDAO = new CategoriaDAO();
            List<Categoria> listCategoria = categoriaDAO.BuscaTodos(Categoria.class);
            for (Categoria categoria : listCategoria) {
                cbCategoria.addItem(categoria);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar categorias y cobradores" + e);
            e.printStackTrace();
        }

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
            for (Socio socio1 : listSocios) {
                tblModel.addRow(new Object[]{
                    socio1.getId(),
                    socio1.getNombre(),
                    socio1.getCi(),
                    socio1.getFechaingreso(),
                    socio1.getFechanacimiento(),
                    socio1.getCategoria().getDefinicion(),
                    socio1.getSituacion()});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Socios no encontrado");
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {

        limpiaCampos();

        if (tblSocio.getSelectedRow() != -1) {
            try {

                jlCodigoSocio.setText(socioSeleccionado.getId().toString());
                txtFechaNacimiento.setDate(socioSeleccionado.getFechanacimiento());
                txtFechaIngreso.setDate(socioSeleccionado.getFechaingreso());
                txtNombre.setText(socioSeleccionado.getNombre());
                ftxtCI.setText(socioSeleccionado.getCi());
                txtNacionalidad.setText(socioSeleccionado.getNacionalidad());
                txtCiudad.setText(socioSeleccionado.getCiudad());
                txtBarrio.setText(socioSeleccionado.getBarrio());
                txtTelefono.setText(socioSeleccionado.getTelefono());
                txtCelular.setText(socioSeleccionado.getCelular());
                txtEmail.setText(socioSeleccionado.getEmail());
                txtDireccion.setText(socioSeleccionado.getDireccion());
                txtEstadoCivil.setText(socioSeleccionado.getEstadocivil());
                txtProfesion.setText(socioSeleccionado.getProfesion());
                cbSituacion.setSelectedItem(socioSeleccionado.getSituacion());
                cbSexo.setSelectedItem(socioSeleccionado.getSexo());

                foto = new ImageIcon(socioSeleccionado.getFoto());
                icono = new ImageIcon(foto.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
                jlblFoto.setIcon(icono);
                txtFoto.setText(socioSeleccionado.getFoto());

                if (socioSeleccionado.getCalidad() == null) {
                    lblStatusHuella.setText("Por favor registre la huella del socio");
                    lblStatusHuella.setForeground(Color.red);
                } else {
                    lblStatusHuella.setText("Socio posee huella registrada correctamente!");
                    lblStatusHuella.setForeground(Color.GREEN);
                }
                cbCategoria.setSelectedItem(socioSeleccionado.getCategoria());
                cbCobrador.setSelectedItem(socioSeleccionado.getCobrador());
                txtAreaHistorico.setText(socioSeleccionado.getHistoria());

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

    private void habilitaCampos() {

        txtNombre.setEditable(true);
        txtBarrio.setEditable(true);
        ftxtCI.setEditable(true);
        txtCiudad.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtEstadoCivil.setEditable(true);
        txtNacionalidad.setEditable(true);
        txtProfesion.setEditable(true);
        txtTelefono.setEditable(true);
        txtCelular.setEditable(true);
        cbCobrador.setEnabled(true);
        cbSexo.setEnabled(true);
        cbSexo.setEditable(true);
        txtFechaIngreso.setEditable(true);
        txtFechaIngreso.setEnabled(true);
        txtFechaNacimiento.setEditable(true);
        txtFechaNacimiento.setEnabled(true);
        cbSituacion.setEditable(true);
        cbSituacion.setEnabled(true);
        cbCategoria.setEnabled(true);
        tblSocio.setEnabled(false);
        tblSocio.setVisible(false);
        txtAreaHistorico.setEditable(true);
        btnCarneSocio.setEnabled(false);
        btnFicha.setEnabled(false);

    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtBarrio.setEditable(false);
        ftxtCI.setEditable(false);
        txtCiudad.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtEstadoCivil.setEditable(false);
        txtNacionalidad.setEditable(false);
        txtProfesion.setEditable(false);
        txtTelefono.setEditable(false);
        txtCelular.setEditable(false);
        txtCelular.setEnabled(false);
        cbCobrador.setEnabled(false);
        cbCobrador.setEditable(false);
        cbSexo.setEnabled(false);
        cbSexo.setEditable(false);
        txtFechaIngreso.setEnabled(false);
        txtFechaNacimiento.setEnabled(false);
        txtFechaIngreso.setEditable(false);
        txtFechaNacimiento.setEditable(false);
        cbSituacion.setEditable(false);
        cbSituacion.setEnabled(false);
        cbCategoria.setEditable(false);
        cbCategoria.setEnabled(false);
        tblSocio.setEnabled(true);
        tblSocio.setVisible(true);
        txtAreaHistorico.setEditable(false);
        btnCarneSocio.setEnabled(true);
        btnFicha.setEnabled(true);
    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnFoto.setEnabled(false);
    }

    private void habilitaBotones() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscar.setEnabled(true);
        btnFoto.setEnabled(true);
    }

    private void limpiaCampos() {

        jlCodigoSocio.setText("");
        txtFechaNacimiento.setDate(new Date());
        txtFechaIngreso.setDate(new Date());
        txtNombre.setText("");
        ftxtCI.setText("");
        txtNacionalidad.setText("");
        txtCiudad.setText("");
        txtBarrio.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtEstadoCivil.setText("");
        txtProfesion.setText("");
        cbSituacion.setSelectedItem("");
        cbSexo.setSelectedItem("");
        jlblFoto.setIcon(null);
        cbCategoria.setSelectedItem("");
        cbCobrador.setSelectedItem("");
        txtAreaHistorico.setText("");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSocio = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnFicha = new org.jasper.viewer.components.JasperRunnerButton();
        btnSeleccionaTitular = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCarneSocio = new org.jasper.viewer.components.JasperRunnerButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlCodigoSocio = new javax.swing.JLabel();
        ftxtCI = new javax.swing.JFormattedTextField();
        jlblFoto = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtFoto = new javax.swing.JTextField();
        btnFoto = new javax.swing.JButton();
        txtFechaIngreso = new org.jdesktop.swingx.JXDatePicker();
        txtFechaNacimiento = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNacionalidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBarrio = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtProfesion = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbSituacion = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        txtAreaHistorico = new java.awt.TextArea();
        cbCobrador = new javax.swing.JComboBox();
        cbCategoria = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        btnHuella = new javax.swing.JButton();
        imagePanel1 = new imagepanel.ImagePanel();
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
        jLabel1.setText("Socios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "C.I.", "Fecha Ingreso", "Fecha Nacimiento", "Categoría", "Situación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            tblSocio.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblSocio.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblSocio.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblSocio.getColumnModel().getColumn(2).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(3).setPreferredWidth(60);
            tblSocio.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
            tblSocio.getColumnModel().getColumn(4).setPreferredWidth(60);
            tblSocio.getColumnModel().getColumn(4).setCellRenderer(new MyDateCellRenderer());
            tblSocio.getColumnModel().getColumn(5).setPreferredWidth(40);
            tblSocio.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
            tblSocio.getColumnModel().getColumn(6).setPreferredWidth(40);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel6, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbCI);
        rbCI.setText("Cédula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 0);
        jPanel7.add(rbCI, gridBagConstraints);

        buttonGroup1.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 21, 0, 0);
        jPanel7.add(rbNombre, gridBagConstraints);

        buttonGroup1.add(rbCodigo);
        rbCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 0);
        jPanel7.add(rbCodigo, gridBagConstraints);

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFiltro, gridBagConstraints);

        jLabel3.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel3, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnBuscar, gridBagConstraints);

        jPanel2.add(jPanel7, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnFicha.setText("Ficha solicitud de filiación");
        btnFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFichaActionPerformed(evt);
            }
        });
        jPanel4.add(btnFicha);

        btnSeleccionaTitular.setText("Seleccionar Titular"); // NOI18N
        btnSeleccionaTitular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaTitularActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaTitular);

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

        btnCarneSocio.setText("Carne de Socio");
        btnCarneSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarneSocioActionPerformed(evt);
            }
        });
        jPanel4.add(btnCarneSocio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        jLabel2.setText("Código del Socio"); // NOI18N

        jLabel4.setText("Nombre"); // NOI18N

        txtNombre.setEditable(false);

        jLabel6.setText("Fecha Nacimiento"); // NOI18N

        jLabel7.setText("Sexo"); // NOI18N

        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Masculino", "Femenino" }));
        cbSexo.setEnabled(false);

        jLabel13.setText("C.I."); // NOI18N

        jLabel14.setText("Fecha de Ingreso"); // NOI18N

        jlCodigoSocio.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        ftxtCI.setEditable(false);

        jlblFoto.setPreferredSize(new java.awt.Dimension(3, 4));

        jLabel21.setText("Foto del Socio");

        txtFoto.setEditable(false);
        txtFoto.setEnabled(false);

        btnFoto.setText("...");
        btnFoto.setEnabled(false);
        btnFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFotoActionPerformed(evt);
            }
        });

        txtFechaIngreso.setEnabled(false);

        txtFechaNacimiento.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(449, 449, 449)
                .addComponent(btnFoto)
                .addGap(365, 569, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                                    .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addComponent(jlCodigoSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlCodigoSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFoto)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jTabbedPane1.addTab("Personal", jPanel3);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel12.setText("Nacionalidad"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel12, gridBagConstraints);

        txtNacionalidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNacionalidad, gridBagConstraints);

        jLabel9.setText("Ciudad"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel9, gridBagConstraints);

        txtCiudad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtCiudad, gridBagConstraints);

        jLabel8.setText("Barrio"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel8, gridBagConstraints);

        txtBarrio.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtBarrio, gridBagConstraints);

        jLabel17.setText("Telefono"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel17, gridBagConstraints);

        txtTelefono.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtTelefono, gridBagConstraints);

        jLabel18.setText("Email"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel18, gridBagConstraints);

        txtEmail.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtEmail, gridBagConstraints);

        jLabel5.setText("Dirección"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        txtDireccion.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtDireccion, gridBagConstraints);

        jLabel10.setText("Estado Civil"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel10, gridBagConstraints);

        txtEstadoCivil.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtEstadoCivil, gridBagConstraints);

        jLabel11.setText("Profesión"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel11, gridBagConstraints);

        txtProfesion.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtProfesion, gridBagConstraints);

        jLabel20.setText("Celular"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel20, gridBagConstraints);

        txtCelular.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtCelular, gridBagConstraints);

        jTabbedPane1.addTab("Contacto", jPanel5);

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel19.setText("Categoría"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel19, gridBagConstraints);

        jLabel16.setText("Cobrador"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel16, gridBagConstraints);

        jLabel15.setText("Situacion"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel15, gridBagConstraints);

        cbSituacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo", "Renuncia" }));
        cbSituacion.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbSituacion, gridBagConstraints);

        jLabel22.setText("Historico del Socio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel22, gridBagConstraints);

        txtAreaHistorico.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtAreaHistorico, gridBagConstraints);

        cbCobrador.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbCobrador, gridBagConstraints);

        cbCategoria.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbCategoria, gridBagConstraints);

        jTabbedPane1.addTab("Adicionales", jPanel8);

        btnHuella.setText("Captura y guarda huella");
        btnHuella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuellaActionPerformed(evt);
            }
        });

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

        lblStatusHuella.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(288, 288, 288)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatusHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuella))
                .addContainerGap(305, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(784, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(btnHuella)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatusHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(239, 239, 239))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Huella", jPanel9);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        btnHuella.setEnabled(false);
        limpiaCampos();
        habilitaCampos();
        habilitaBotones();
        txtNombre.requestFocus();


    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (txtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            txtNombre.requestFocus();

        } else if (ftxtCI.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la CI del Socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else if (txtFechaNacimiento.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Fecha de Nacimiento del Socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else if (cbCobrador.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una cobrador", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else if (cbCategoria.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una categoría", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else {

            if (jlCodigoSocio.getText().equals("")) {

                try {

                    socio = new Socio();

                    socio.setNombre(txtNombre.getText());
                    socio.setDireccion(txtDireccion.getText());
                    socio.setFechanacimiento(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaNacimiento.getDate()));
                    socio.setSexo(cbSexo.getSelectedItem().toString());
                    socio.setEstadocivil(txtEstadoCivil.getText());
                    socio.setProfesion(txtProfesion.getText());
                    socio.setNacionalidad(txtNacionalidad.getText());
                    socio.setCiudad(txtCiudad.getText());
                    socio.setCi(ftxtCI.getText());
                    socio.setFechaingreso(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaIngreso.getDate()));
                    socio.setSituacion(cbSituacion.getSelectedItem().toString());
                    socio.setCobrador((Cobrador) cbCobrador.getSelectedItem());
                    socio.setBarrio(txtBarrio.getText());
                    socio.setTelefono(txtTelefono.getText());
                    socio.setCelular(txtCelular.getText());
                    socio.setEmail(txtEmail.getText());
                    socio.setHistoria(txtAreaHistorico.getText());
                    socio.setCategoria((Categoria) cbCategoria.getSelectedItem());
                    socio.setFoto(txtFoto.getText());

                    socioDAO = new SocioDAO();
                    if ((socioDAO.VerificaCI(ftxtCI.getText())) == true) {

                        JOptionPane.showMessageDialog(null, "ERROR: El socio ya se encuentra en la base de datos!");

                    } else {
                        socioDAO = new SocioDAO();
                        socioDAO.Salvar(socio);
                        JOptionPane.showMessageDialog(null, "Socio registrado correctamente!");
                        buscaTodosLosRegistros();
                        muestraContenidoTbl();
                        desabilitaCampos();
                        desabilitaBotones();
                        btnHuella.setEnabled(true);
                    }

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "No fue posible salvar el socio" + error);
                    error.printStackTrace();
                }

            } else {  //procedimento realizado cuando se desea alterar un registro

                if (txtNombre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese el nombre del socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.requestFocus();

                } else if (ftxtCI.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la CI del socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else if (txtFechaNacimiento.getDate().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la Fecha de Nacimiento del Socio", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else if (cbCobrador.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cobrador", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else if (cbCategoria.getSelectedItem().equals("")) {
                    JOptionPane.showMessageDialog(null, "Seleccione una categoría", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    try {

                        socioSeleccionado.setId(Integer.parseInt(jlCodigoSocio.getText()));
                        socioSeleccionado.setNombre(txtNombre.getText());
                        socioSeleccionado.setDireccion(txtDireccion.getText());
                        socioSeleccionado.setFechanacimiento(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaNacimiento.getDate()));
                        socioSeleccionado.setSexo(cbSexo.getSelectedItem().toString());
                        socioSeleccionado.setEstadocivil(txtEstadoCivil.getText());
                        socioSeleccionado.setProfesion(txtProfesion.getText());
                        socioSeleccionado.setNacionalidad(txtNacionalidad.getText());
                        socioSeleccionado.setCiudad(txtCiudad.getText());
                        socioSeleccionado.setCi(ftxtCI.getText());
                        socioSeleccionado.setFechaingreso(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaIngreso.getDate()));
                        socioSeleccionado.setSituacion(cbSituacion.getSelectedItem().toString());
                        socioSeleccionado.setCobrador((Cobrador) cbCobrador.getSelectedItem());
                        socioSeleccionado.setBarrio(txtBarrio.getText());
                        socioSeleccionado.setTelefono(txtTelefono.getText());
                        socioSeleccionado.setCelular(txtCelular.getText());
                        socioSeleccionado.setEmail(txtEmail.getText());
                        socioSeleccionado.setHistoria(txtAreaHistorico.getText());
                        socioSeleccionado.setCategoria((Categoria) cbCategoria.getSelectedItem());
                        socioSeleccionado.setFoto(txtFoto.getText());

                        socioDAO = new SocioDAO();
                        socioDAO.Actualizar(socioSeleccionado);

                        JOptionPane.showMessageDialog(null, "Socio alterado correctamente!");

                        buscaTodosLosRegistros();
                        muestraContenidoTbl();
                        desabilitaCampos();
                        desabilitaBotones();
                        btnHuella.setEnabled(true);

                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado " + error);
                        error.printStackTrace();
                    }
                }
            }

        }


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        desabilitaBotones();
        desabilitaCampos();
        limpiaCampos();


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (tblSocio.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Socio en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {
            if (tblSocio.getSelectedRow() != -1) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea excluir al Socio?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {

                    socioDAO = new SocioDAO();
                    socioSeleccionado = listSocios.get(tblSocio.getSelectedRow());
                    socioDAO.elminiar(Socio.class, socioSeleccionado);

                }
            } else {

                buscaTodosLosRegistros();
                muestraContenidoTbl();

                JOptionPane.showMessageDialog(this, "Seleccione un Socio en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSeleccionaTitularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaTitularActionPerformed

        if (tblSocio.getSelectedRow() != -1) {

            seteaSocioTitular.setSocioTitular(listSocios.get(tblSocio.getSelectedRow()));

            this.dispose();
            seteaSocioTitular.toFront();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Socio en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);

        }

    }//GEN-LAST:event_btnSeleccionaTitularActionPerformed

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed

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
    }//GEN-LAST:event_btnFotoActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFichaActionPerformed

        try {
            parametros.clear();
            //parametros.put("fotoSocio", txtFoto.getText());
            parametros.put("idSocio", socioSeleccionado.getId());
            btnFicha.setReportParameters(parametros);

            socioDAO = new SocioDAO();
            List dp = socioDAO.BuscaSocioTitular(socioSeleccionado);
            if (dp.size() == 0) {

                btnFicha.setReportURL("/Reportes/solicitudSocioSinDep.jasper");

            } else {

                btnFicha.setReportURL("/Reportes/solicitudSocio.jasper");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte " + e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnFichaActionPerformed

    private void btnCarneSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarneSocioActionPerformed

        /* try {
         InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibos.jasper");
         parametros.clear();
         parametros.put("Msj", "preuba");
         parametros.put("emision", 6194);
         JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
         JasperViewer reporte = new JasperViewer(jasperPrint, false);
         reporte.setVisible(true);

         reporte.toFront();

         } catch (Exception ex) {
         JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
         ex.printStackTrace();
         }
         */ try {
            parametros.clear();
            parametros.put("fotoCarne", txtFoto.getText());
            parametros.put("logoClub", "Imagenes/Escudo.jpg");
            parametros.put("idSocio", socioSeleccionado.getId());
            btnCarneSocio.setReportParameters(parametros);
            btnCarneSocio.setReportURL("/Reportes/carneSocio.jasper");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte " + e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCarneSocioActionPerformed

    private void btnHuellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuellaActionPerformed

        bioMini = new BioMini();
        socioDAO = new SocioDAO();
        bioMini.iniciar();
        DepDAO depDAO = new DepDAO();
        Dependiente d = (bioMini.BuscarHuellaDep(imagePanel1, depDAO.BuscaDepXHuella()));
        Socio s = (bioMini.BuscarHuellaSocioTitular(imagePanel1, socioDAO.BuscaSociosxHuella()));
        if (socioSeleccionado != null) {
            if (s == null) {
                //Caso la huella no este registrada por otra persona
                bioMini.GuardarHuellaSocio(imagePanel1, socioSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella registrada correctamente!");

            } else if (socioSeleccionado.getId().compareTo(s.getId()) == 0) {

                bioMini.GuardarHuellaSocio(imagePanel1, socioSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella actualizada correctamente!");

            } else if (d != null) {
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por un socio Dependiente: " + d.getId() + " " + d.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //huella ya registrada por otra persona
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por: " + s.getId() + " " + s.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }

    }//GEN-LAST:event_btnHuellaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private org.jasper.viewer.components.JasperRunnerButton btnCarneSocio;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private org.jasper.viewer.components.JasperRunnerButton btnFicha;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnHuella;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaTitular;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCategoria;
    private javax.swing.JComboBox cbCobrador;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbSituacion;
    private javax.swing.JFormattedTextField ftxtCI;
    private imagepanel.ImagePanel imagePanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlCodigoSocio;
    private javax.swing.JLabel jlblFoto;
    private javax.swing.JLabel lblStatusHuella;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblSocio;
    private java.awt.TextArea txtAreaHistorico;
    private javax.swing.JTextField txtBarrio;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstadoCivil;
    private org.jdesktop.swingx.JXDatePicker txtFechaIngreso;
    private org.jdesktop.swingx.JXDatePicker txtFechaNacimiento;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtFoto;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtProfesion;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
