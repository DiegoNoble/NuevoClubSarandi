package com.club.views;

import Utilidades.Utilidades;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Funcionario;
import com.club.BEANS.Sectores;
import com.club.BEANS.SectoresFuncionario;
import com.club.BEANS.Situacion;
import com.club.DAOs.DepDAO;
import com.club.DAOs.FuncionarioDAO;
import com.club.DAOs.SectorDAO;
import com.club.DAOs.SectoresFuncionarioDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.huellas.BioMini;
import com.club.modelos.FuncionarioTableModel;
import com.club.modelos.SectoresFuncionarioTableModel;
import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;

public final class FuncionarioView extends javax.swing.JInternalFrame {

    private FuncionarioDAO funcionarioDAO;
    private List<Funcionario> listFuncionarios;
    private FuncionarioTableModel tblModelFuncionario;
    private ListSelectionModel listModelFuncionarios;
    private ImageIcon foto;
    private Icon icono;
    private Funcionario funcionarioSeleccionado;
    private Funcionario funcionario;
    private HashMap parametros;
    BioMini bioMini;
    private List<SectoresFuncionario> listSectoresFuncionario;
    private List<SectoresFuncionario> listSectoresFuncionarioToRemove;
    private List<SectoresFuncionario> sectoresSeleccionados;
    SectoresFuncionarioTableModel tblModelSectoresFuncionario;
    ListSelectionModel listModelSectores;
    SectoresFuncionarioDAO sectoresFuncionarioDAO;
    int cifras = (int) Math.pow(10, 2);

    public FuncionarioView() {
        initComponents();

        btnEliminar.setVisible(false);
        parametros = new HashMap();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        configTblSectores();
        buscaSectoresAsignados();
        cbSituacion.setModel(new DefaultComboBoxModel(Situacion.values()));

    }

    private void buscaTodosLosRegistros() {
        funcionarioDAO = new FuncionarioDAO();
        listFuncionarios.addAll(funcionarioDAO.BuscaTodos(Funcionario.class));
        tblModelFuncionario.fireTableDataChanged();
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblFuncionario.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listFuncionarios = new ArrayList<>();
        tblModelFuncionario = new FuncionarioTableModel(listFuncionarios);

        tblFuncionario.setModel(tblModelFuncionario);

        tblFuncionario.getColumn("Ingreso").setCellRenderer(new MeDateCellRenderer());
        tblFuncionario.getColumn("Egreso").setCellRenderer(new MeDateCellRenderer());
        listModelFuncionarios = tblFuncionario.getSelectionModel();
        listModelFuncionarios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblFuncionario.getSelectedRow() != -1) {

                        funcionarioSeleccionado = listFuncionarios.get(tblFuncionario.getSelectedRow());
                    }
                    muestraDetalles();
                }
            }
        });

    }

    private void muestraDetalles() {

        limpiaCampos();

        if (tblFuncionario.getSelectedRow() != -1) {
            try {

                jlCodigoFuncionario.setText(funcionarioSeleccionado.getId().toString());
                txtFechaNacimiento.setDate(funcionarioSeleccionado.getFechanacimiento());
                dpIngreso.setDate(funcionarioSeleccionado.getFechaingreso());
                dpEgreso.setDate(funcionarioSeleccionado.getFechaegreso());
                txtNombre.setText(funcionarioSeleccionado.getNombre());
                ftxtCI.setText(funcionarioSeleccionado.getCi());
                txtNacionalidad.setText(funcionarioSeleccionado.getNacionalidad());
                txtCiudad.setText(funcionarioSeleccionado.getCiudad());
                txtBarrio.setText(funcionarioSeleccionado.getBarrio());
                txtTelefono.setText(funcionarioSeleccionado.getTelefono());
                txtDireccion.setText(funcionarioSeleccionado.getDireccion());
                txtEstadoCivil.setText(funcionarioSeleccionado.getEstadocivil());
                cbSituacion.setSelectedItem(funcionarioSeleccionado.getSituacion());
                cbSexo.setSelectedItem(funcionarioSeleccionado.getSexo());

                foto = new ImageIcon(funcionarioSeleccionado.getFoto());
                icono = new ImageIcon(foto.getImage().getScaledInstance(jlblFoto.getWidth(), jlblFoto.getHeight(), Image.SCALE_DEFAULT));
                jlblFoto.setIcon(icono);
                txtFoto.setText(funcionarioSeleccionado.getFoto());

                buscaSectoresAsignados();

                if (funcionarioSeleccionado.getCalidad() == null) {
                    lblStatusHuella.setText("Por favor registre la huella del funcionario");
                    lblStatusHuella.setForeground(Color.red);
                } else {
                    lblStatusHuella.setText("Funcionario posee huella registrada correctamente!");
                    lblStatusHuella.setForeground(Color.GREEN);
                    /*bioMini = new BioMini();
                     bioMini.imagenBDFuncionario(imagePanel1, funcionarioSeleccionado);*/
                }
                txtAreaDescripcion.setText(funcionarioSeleccionado.getDescripcion());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles" + error);
                error.printStackTrace();
            }
        }

    }

    void configTblSectores() {
        ((DefaultTableCellRenderer) tblSectores.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSectoresFuncionario = new ArrayList<>();
        listSectoresFuncionarioToRemove = new ArrayList<>();
        sectoresSeleccionados = new ArrayList<>();
        tblModelSectoresFuncionario = new SectoresFuncionarioTableModel(listSectoresFuncionario);
        tblSectores.setModel(tblModelSectoresFuncionario);

        tblSectores.setRowHeight(25);
        //tblSectores.removeColumn(tblSectores.getColumn("Inmueble"));
        tblSectores.getColumn("Sector").setCellEditor(new ComboBoxCellEditor(new ComboSectores()));

        listModelSectores = tblSectores.getSelectionModel();
        listModelSectores.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblSectores.getSelectedRow() != -1) {

                    btnEliminarSector.setEnabled(true);
                } else {
                    btnEliminar.setEnabled(false);
                }
            }
        });

    }

    void agregarNuevoSector() {

        tblModelSectoresFuncionario.agregar(new SectoresFuncionario(new Sectores(), funcionarioSeleccionado, new Double(0.0)));

        for (SectoresFuncionario sectores : listSectoresFuncionario) {
            Double procentage = 100.00 / listSectoresFuncionario.size();
            sectores.setProcentageSector(Math.rint((procentage * cifras) / cifras));
        }
        tblModelSectoresFuncionario.fireTableDataChanged();
    }

    void elminarSectorSeleccionado() {

        SectoresFuncionario sectoresToRemove = listSectoresFuncionario.get(tblSectores.getSelectedRow());
        listSectoresFuncionario.remove(sectoresToRemove);
        listSectoresFuncionarioToRemove.add(sectoresToRemove);
        tblModelSectoresFuncionario.fireTableDataChanged();
        for (SectoresFuncionario sectores : listSectoresFuncionario) {
            Double procentage = 100.00 / listSectoresFuncionario.size();
            sectores.setProcentageSector(Math.rint((procentage * cifras) / cifras));
        }
        tblModelSectoresFuncionario.fireTableStructureChanged();
    }

    void buscaSectoresAsignados() {

        sectoresFuncionarioDAO = new SectoresFuncionarioDAO();

        for (SectoresFuncionario sectores : sectoresFuncionarioDAO.BuscaPorFuncionario(funcionarioSeleccionado)) {
            tblModelSectoresFuncionario.agregar(sectores);
        }

    }

    boolean VerificaPorcentage() {

        Double verificaPorcentaje = new Double(0.0);

        for (SectoresFuncionario sectores : listSectoresFuncionario) {

            if ((sectores.getProcentageSector() == 0)) {
                return false;
            } else {
                verificaPorcentaje = verificaPorcentaje + sectores.getProcentageSector();
            }
        }
        return verificaPorcentaje == 100;

    }

    private class ComboSectores extends JComboBox<Object> {

        public ComboSectores() {
            AutoCompleteDecorator.decorate(this);
            SectorDAO sectorDAO = new SectorDAO();
            List<Sectores> sectores = sectorDAO.BuscaTodos(Sectores.class);
            for (Sectores sector : sectores) {
                this.addItem(sector);
            }
        }
    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            funcionario = new Funcionario();
            funcionario = (Funcionario) funcionarioDAO.BuscaPorID(Funcionario.class, Integer.parseInt(txtFiltro.getText()));

            listFuncionarios.clear();

            listFuncionarios.add(funcionario);

            tblModelFuncionario.fireTableDataChanged();

        } else if (rbCI.isSelected()) {
            funcionarioDAO = new FuncionarioDAO();
            listFuncionarios.addAll(funcionarioDAO.BuscaPorCI(txtFiltro.getText()));
            tblModelFuncionario.fireTableDataChanged();

        } else if (rbNombre.isSelected()) {
            funcionarioDAO = new FuncionarioDAO();
            listFuncionarios.addAll(funcionarioDAO.BuscaPorNombre(txtFiltro.getText()));
            tblModelFuncionario.fireTableDataChanged();

        }

    }

    private void habilitaCampos() {

        txtNombre.setEditable(true);
        txtBarrio.setEditable(true);
        ftxtCI.setEditable(true);
        txtCiudad.setEditable(true);
        txtDireccion.setEditable(true);
        txtEstadoCivil.setEditable(true);
        txtNacionalidad.setEditable(true);
        txtTelefono.setEditable(true);
        cbSexo.setEnabled(true);
        cbSexo.setEditable(true);
        dpIngreso.setEditable(true);
        dpIngreso.setEnabled(true);
        dpEgreso.setEditable(true);
        dpEgreso.setEnabled(true);
        txtFechaNacimiento.setEditable(true);
        txtFechaNacimiento.setEnabled(true);
        cbSituacion.setEditable(true);
        cbSituacion.setEnabled(true);
        tblFuncionario.setEnabled(false);
        tblFuncionario.setVisible(false);
        txtAreaDescripcion.setEditable(true);
        tblSectores.setEnabled(true);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtBarrio.setEditable(false);
        ftxtCI.setEditable(false);
        txtCiudad.setEditable(false);
        txtDireccion.setEditable(false);
        txtEstadoCivil.setEditable(false);
        txtNacionalidad.setEditable(false);
        txtTelefono.setEditable(false);
        cbSexo.setEnabled(false);
        cbSexo.setEditable(false);
        dpIngreso.setEnabled(false);
        txtFechaNacimiento.setEnabled(false);
        dpIngreso.setEditable(false);
        dpEgreso.setEnabled(false);
        dpEgreso.setEditable(false);
        txtFechaNacimiento.setEditable(false);
        cbSituacion.setEditable(false);
        cbSituacion.setEnabled(false);
        tblFuncionario.setEnabled(true);
        tblFuncionario.setVisible(true);
        txtAreaDescripcion.setEditable(false);
        tblSectores.setEnabled(false);
    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnFoto.setEnabled(false);
        btnAgregarSector.setEnabled(false);
        btnEliminarSector.setEnabled(false);
    }

    private void habilitaBotones() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnBuscar.setEnabled(true);
        btnFoto.setEnabled(true);
        btnAgregarSector.setEnabled(true);
        btnEliminarSector.setEnabled(true);
    }

    private void limpiaCampos() {

        jlCodigoFuncionario.setText("");
        txtFechaNacimiento.setDate(new Date());
        dpIngreso.setDate(new Date());
        txtNombre.setText("");
        ftxtCI.setText("");
        txtNacionalidad.setText("");
        txtCiudad.setText("");
        txtBarrio.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtEstadoCivil.setText("");
        //cbSituacion.setSelectedItem("");
        cbSexo.setSelectedItem("");
        jlblFoto.setIcon(null);
        txtAreaDescripcion.setText("");
        txtFoto.setText("");
        listSectoresFuncionario.clear();
        tblModelSectoresFuncionario.fireTableDataChanged();

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
        tblFuncionario = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
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
        jlCodigoFuncionario = new javax.swing.JLabel();
        ftxtCI = new javax.swing.JFormattedTextField();
        jlblFoto = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtFoto = new javax.swing.JTextField();
        btnFoto = new javax.swing.JButton();
        dpIngreso = new org.jdesktop.swingx.JXDatePicker();
        txtFechaNacimiento = new org.jdesktop.swingx.JXDatePicker();
        jLabel15 = new javax.swing.JLabel();
        cbSituacion = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        dpEgreso = new org.jdesktop.swingx.JXDatePicker();
        jPanel8 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtAreaDescripcion = new java.awt.TextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEstadoCivil = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtNacionalidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        txtBarrio = new javax.swing.JTextField();
        txtCiudad = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnHuella = new javax.swing.JButton();
        imagePanel1 = new imagepanel.ImagePanel();
        lblStatusHuella = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSectores = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        btnAgregarSector = new javax.swing.JButton();
        btnEliminarSector = new javax.swing.JButton();

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
        jLabel1.setText("Funcionarios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblFuncionario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFuncionario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblFuncionario);

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

        jLabel14.setText("Fecha de Engreso"); // NOI18N

        jlCodigoFuncionario.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

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

        dpIngreso.setEnabled(false);

        txtFechaNacimiento.setEnabled(false);

        jLabel15.setText("Situacion"); // NOI18N

        cbSituacion.setEnabled(false);

        jLabel20.setText("Fecha de Ingreso"); // NOI18N

        dpEgreso.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre)
                                    .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addComponent(jlCodigoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(4, 4, 4)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(ftxtCI, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(cbSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(dpIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(dpEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(345, 345, 345)
                                .addComponent(btnFoto))
                            .addComponent(txtFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jlblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSituacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlCodigoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel22.setText("Historico del Socio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel22, gridBagConstraints);

        txtAreaDescripcion.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtAreaDescripcion, gridBagConstraints);

        jLabel17.setText("Telefono"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel17, gridBagConstraints);

        jLabel12.setText("Nacionalidad"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel12, gridBagConstraints);

        jLabel10.setText("Estado Civil"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel10, gridBagConstraints);

        txtEstadoCivil.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtEstadoCivil, gridBagConstraints);

        txtTelefono.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtTelefono, gridBagConstraints);

        txtNacionalidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtNacionalidad, gridBagConstraints);

        jLabel9.setText("Ciudad"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel9, gridBagConstraints);

        jLabel8.setText("Barrio"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel8, gridBagConstraints);

        jLabel5.setText("Dirección"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(jLabel5, gridBagConstraints);

        txtDireccion.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtDireccion, gridBagConstraints);

        txtBarrio.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtBarrio, gridBagConstraints);

        txtCiudad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtCiudad, gridBagConstraints);

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

        jPanel5.setLayout(new java.awt.GridBagLayout());

        tblSectores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblSectores);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jScrollPane4, gridBagConstraints);

        jPanel11.setLayout(new java.awt.GridBagLayout());

        btnAgregarSector.setText("Agregar Sector");
        btnAgregarSector.setEnabled(false);
        btnAgregarSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarSectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(btnAgregarSector, gridBagConstraints);

        btnEliminarSector.setText("Eliminar Sector");
        btnEliminarSector.setEnabled(false);
        btnEliminarSector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarSectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel11.add(btnEliminarSector, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel5.add(jPanel11, gridBagConstraints);

        jTabbedPane1.addTab("Distribución Sectores", jPanel5);

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
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            txtNombre.requestFocus();

        } else if (ftxtCI.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la CI del Funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else if (txtFechaNacimiento.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la Fecha de Nacimiento del Funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);

        } else {

            if (jlCodigoFuncionario.getText().equals("")) {

                try {

                    funcionario = new Funcionario();

                    funcionario.setNombre(txtNombre.getText());
                    funcionario.setDireccion(txtDireccion.getText());
                    funcionario.setFechanacimiento(Utilidades.RecibeDateRetornaDateFormatoBD(txtFechaNacimiento.getDate()));
                    funcionario.setSexo(cbSexo.getSelectedItem().toString());
                    funcionario.setEstadocivil(txtEstadoCivil.getText());
                    funcionario.setNacionalidad(txtNacionalidad.getText());
                    funcionario.setCiudad(txtCiudad.getText());
                    funcionario.setCi(ftxtCI.getText());
                    funcionario.setFechaingreso(dpIngreso.getDate());
                    funcionario.setFechaegreso(dpEgreso.getDate());
                    funcionario.setSituacion((Situacion) cbSituacion.getSelectedItem());
                    funcionario.setBarrio(txtBarrio.getText());
                    funcionario.setTelefono(txtTelefono.getText());
                    funcionario.setDescripcion(txtAreaDescripcion.getText());
                    funcionario.setFoto(txtFoto.getText());

                    sectoresFuncionarioDAO = new SectoresFuncionarioDAO();
                    sectoresFuncionarioDAO.SalvarList(listSectoresFuncionario);

                    funcionarioDAO = new FuncionarioDAO();
                    if ((funcionarioDAO.VerificaCI(ftxtCI.getText())) == true) {

                        JOptionPane.showMessageDialog(null, "ERROR: El funcionario ya se encuentra en la base de datos!");

                    } else {
                        funcionarioDAO = new FuncionarioDAO();
                        funcionarioDAO.Salvar(funcionario);
                        JOptionPane.showMessageDialog(null, "Funcionario registrado correctamente!");
                        buscaTodosLosRegistros();
                        desabilitaCampos();
                        desabilitaBotones();
                        btnHuella.setEnabled(true);
                    }

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "No fue posible salvar el funcionario" + error);
                    error.printStackTrace();
                }

            } else {  //procedimento realizado cuando se desea alterar un registro

                if (txtNombre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese el nombre del funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.requestFocus();

                } else if (ftxtCI.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la CI del funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else if (txtFechaNacimiento.getDate().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingrese la Fecha de Nacimiento del Funcionario", "Atencion", JOptionPane.INFORMATION_MESSAGE);

                } else {

                    try {

                        funcionarioSeleccionado.setId(Integer.parseInt(jlCodigoFuncionario.getText()));
                        funcionarioSeleccionado.setNombre(txtNombre.getText());
                        funcionarioSeleccionado.setDireccion(txtDireccion.getText());
                        funcionarioSeleccionado.setFechanacimiento(txtFechaNacimiento.getDate());
                        funcionarioSeleccionado.setFechaegreso(dpEgreso.getDate());
                        funcionarioSeleccionado.setSexo(cbSexo.getSelectedItem().toString());
                        funcionarioSeleccionado.setEstadocivil(txtEstadoCivil.getText());
                        funcionarioSeleccionado.setNacionalidad(txtNacionalidad.getText());
                        funcionarioSeleccionado.setCiudad(txtCiudad.getText());
                        funcionarioSeleccionado.setCi(ftxtCI.getText());
                        funcionarioSeleccionado.setFechaingreso(dpIngreso.getDate());
                        funcionarioSeleccionado.setSituacion((Situacion) cbSituacion.getSelectedItem());
                        funcionarioSeleccionado.setBarrio(txtBarrio.getText());
                        funcionarioSeleccionado.setTelefono(txtTelefono.getText());
                        funcionarioSeleccionado.setDescripcion(txtAreaDescripcion.getText());
                        funcionarioSeleccionado.setFoto(txtFoto.getText());

                        funcionarioDAO = new FuncionarioDAO();
                        funcionarioDAO.Actualizar(funcionarioSeleccionado);

                        if (!listSectoresFuncionarioToRemove.isEmpty()) {
                            for (SectoresFuncionario toRemove : listSectoresFuncionarioToRemove) {
                                sectoresFuncionarioDAO = new SectoresFuncionarioDAO();
                                sectoresFuncionarioDAO.elminiar(SectoresFuncionario.class, toRemove.getId());
                            }
                        }
                        if (!listSectoresFuncionario.isEmpty()) {
                            for (SectoresFuncionario actualizar : listSectoresFuncionario) {
                                sectoresFuncionarioDAO = new SectoresFuncionarioDAO();
                                sectoresFuncionarioDAO.Actualizar(actualizar);
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Funcionario modificado correctamente!");

                        buscaTodosLosRegistros();
                        desabilitaCampos();
                        desabilitaBotones();
                        btnHuella.setEnabled(true);

                    } catch (Exception error) {
                        JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado " + error);
                        error.printStackTrace();
                    }
                }
            }
            listSectoresFuncionarioToRemove.clear();
        }


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        listSectoresFuncionarioToRemove.clear();

        desabilitaBotones();
        desabilitaCampos();
        limpiaCampos();


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (tblFuncionario.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Funcionario en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {

            if (tblFuncionario.getSelectedRow() != -1) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea excluir al Funcionario?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {

                    funcionarioDAO = new FuncionarioDAO();
                    funcionarioSeleccionado = listFuncionarios.get(tblFuncionario.getSelectedRow());
                    funcionarioDAO
                            .elminiar(Funcionario.class, funcionarioSeleccionado);

                }
            } else {

                buscaTodosLosRegistros();

                JOptionPane.showMessageDialog(this, "Seleccione un Funcionario en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFotoActionPerformed

        try {
            JFileChooser buscarFoto = new JFileChooser();
            buscarFoto.setCurrentDirectory(new File("C:/Fotos Funcionarios/"));
            buscarFoto.setDialogTitle("Cargar Foto del Funcionario");
            buscarFoto.showOpenDialog(this);
            String foto = "C:/Fotos Funcionarios/" + buscarFoto.getSelectedFile().getName();
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

    private void btnHuellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuellaActionPerformed

        bioMini = new BioMini();
        funcionarioDAO = new FuncionarioDAO();
        bioMini.iniciar();
        DepDAO depDAO = new DepDAO();
        Dependiente d = (bioMini.BuscarHuellaDep(imagePanel1, depDAO.BuscaDepXHuella()));
        Funcionario s = (bioMini.BuscarHuellaFuncionario(imagePanel1, funcionarioDAO.BuscaFuncionariosxHuella()));
        if (funcionarioSeleccionado != null) {
            if (s == null) {
                //Caso la huella no este registrada por otra persona
                bioMini.GuardarHuellaFuncionario(imagePanel1, funcionarioSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella registrada correctamente!");

            } else if (funcionarioSeleccionado.getId().compareTo(s.getId()) == 0) {

                bioMini.GuardarHuellaFuncionario(imagePanel1, funcionarioSeleccionado);
                JOptionPane.showMessageDialog(null, "Huella actualizada correctamente!");
                lblStatusHuella.setText("Funcionario posee huella registrada correctamente!");
                lblStatusHuella.setForeground(Color.GREEN);

            } else if (d != null) {
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por un funcionario Dependiente: " + d.getId() + " " + d.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //huella ya registrada por otra persona
                JOptionPane.showMessageDialog(null, "La huella escaneada ya se encuentra registrada por: " + s.getId() + " " + s.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }

    }//GEN-LAST:event_btnHuellaActionPerformed

    private void btnAgregarSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarSectorActionPerformed
        agregarNuevoSector();
    }//GEN-LAST:event_btnAgregarSectorActionPerformed

    private void btnEliminarSectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSectorActionPerformed
        elminarSectorSeleccionado();
    }//GEN-LAST:event_btnEliminarSectorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarSector;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarSector;
    private javax.swing.JButton btnFoto;
    private javax.swing.JButton btnHuella;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbSituacion;
    private org.jdesktop.swingx.JXDatePicker dpEgreso;
    private org.jdesktop.swingx.JXDatePicker dpIngreso;
    private javax.swing.JFormattedTextField ftxtCI;
    private imagepanel.ImagePanel imagePanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlCodigoFuncionario;
    private javax.swing.JLabel jlblFoto;
    private javax.swing.JLabel lblStatusHuella;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblFuncionario;
    public javax.swing.JTable tblSectores;
    private java.awt.TextArea txtAreaDescripcion;
    private javax.swing.JTextField txtBarrio;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEstadoCivil;
    private org.jdesktop.swingx.JXDatePicker txtFechaNacimiento;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtFoto;
    private javax.swing.JTextField txtNacionalidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
