package com.club.views;

import Utilidades.AjustaColumnas;
import Utilidades.ControlarEntradaTexto;
import com.club.BEANS.CampEconomica;
import com.club.BEANS.Categoria;
import com.club.BEANS.Numeros;
import com.club.BEANS.Socio;
import com.club.DAOs.CampEconomicaDAO;
import com.club.DAOs.NumerosDAO;
import com.club.DAOs.SocioDAO;
import com.club.tableModels.NumerosTableModel;
import com.club.tableModels.SocioTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class VentasCampEcoView extends javax.swing.JInternalFrame {

    NumerosDAO numerosDAO;
    CampEconomicaDAO campEconomicaDAO;
    List<Numeros> listNros;
    NumerosTableModel tblModelNumeros;
    ListSelectionModel listModelNumeross;
    Numeros NumerosSelecionado;
    SocioDAO socioDAO;
    SocioTableModel tblModelSocios;
    List<Socio> listSocios;

    public VentasCampEcoView() {
        initComponents();
        listNros = new ArrayList<>();
        buscaCampañas();
        TblModelNumeros();
        TblModelSocios();
        buscaNumeros();
        buscarSocios();

        deshabilitaCampos();
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        txtFiltro.setDocument(new ControlarEntradaTexto(3, chs));
        txtNro1.setDocument(new ControlarEntradaTexto(3, chs));
        txtNro2.setDocument(new ControlarEntradaTexto(3, chs));
    }

    private void buscaCampañas() {
        campEconomicaDAO = new CampEconomicaDAO();
        List<CampEconomica> campañas = campEconomicaDAO.BuscaTodos(CampEconomica.class);
        for (CampEconomica campaña : campañas) {
            cbCampañas.addItem(campaña);
            cbCampañas1.addItem(campaña);
        }
    }

    private void buscaNumeros() {
        listNros.clear();
        numerosDAO = new NumerosDAO();
        listNros.addAll(numerosDAO.BuscaTodos(Numeros.class));
        tblModelNumeros.fireTableDataChanged();
    }

    private void buscarSocios() {

        if (rbCodigo.isSelected()) {
            Socio socio = new Socio();
            socio = (Socio) socioDAO.BuscaPorID(Socio.class, Integer.parseInt(txtFiltro.getText()));

            listSocios.clear();
            listSocios.add(socio);
            tblModelSocios.fireTableDataChanged();

        } else if (rbCI.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaPorCI(txtFiltro.getText());
            tblModelSocios.fireTableDataChanged();

        } else if (rbNombre.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaPorNombre(txtFiltro.getText());
            tblModelSocios.fireTableDataChanged();

        }

    }

    private void TblModelSocios() {

        ((DefaultTableCellRenderer) tblSocios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listSocios = new ArrayList<Socio>();
        //socioDAO = new SocioDAO();
        //listSocios.addAll(socioDAO.BuscaTodos(Socio.class));
        tblModelSocios = new SocioTableModel(listSocios);
        tblSocios.setModel(tblModelSocios);

        int[] anchos = {5, 200, 50, 50, 50};
        new AjustaColumnas().ajustar(tblSocios, anchos);

        tblSocios.setRowHeight(25);
        tblSocios.getColumn("Categoria").setWidth(0);
        tblSocios.getColumn("Categoria").setPreferredWidth(0);
    }

    private void TblModelNumeros() {

        ((DefaultTableCellRenderer) tblNumeros.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tblModelNumeros = new NumerosTableModel(listNros);
        tblNumeros.setModel(tblModelNumeros);

        listModelNumeross = tblNumeros.getSelectionModel();
        listModelNumeross.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblNumeros.getSelectedRow() != -1) {

                        NumerosSelecionado = listNros.get(tblNumeros.getSelectedRow());
                        btnEditar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                    } else {

                        btnEditar.setEnabled(false);
                        btnEliminar.setEnabled(false);
                    }
                    muestraDetalles();
                }
            }
        });

    }

    private void muestraDetalles() {

        limpiaCampos();

        if (tblNumeros.getSelectedRow() != -1) {
            try {

                txtNro2.setText(NumerosSelecionado.getNro2().toString());
                txtNro1.setText(NumerosSelecionado.getNro1().toString());
                cbCampañas.setSelectedItem(NumerosSelecionado.getCampEconomica());

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "Error al mostrar detalles" + error);
                error.printStackTrace();
            }
        }

    }

    private void buscarNumeros() {

        listNros.clear();
        if (txtFiltro.getText().equals("")) {
            buscaNumeros();
        } else {
            numerosDAO = new NumerosDAO();
            listNros.addAll(numerosDAO.BuscaNumeros(Integer.valueOf(txtFiltro.getText()),
                    Integer.valueOf(txtFiltro.getText()), (CampEconomica) cbCampañas.getSelectedItem()));
            tblModelNumeros.fireTableDataChanged();
        }

    }

    private void limpiaCampos() {

        txtNro2.setText("");

    }

    private void habilitaCampos() {

        txtNro2.setEditable(true);
        txtNro1.setEditable(true);
        cbCampañas.setEditable(true);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnNuevo.setEnabled(false);

    }

    private void deshabilitaCampos() {

        txtNro2.setEditable(false);
        txtNro1.setEditable(false);
        cbCampañas.setEditable(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnNuevo.setEnabled(true);

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
        tblNumeros = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        cbCampañas1 = new javax.swing.JComboBox();
        btnNuevo1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        txtNro2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNro1 = new javax.swing.JTextField();
        cbCampañas = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSocios = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 500));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Numeros por Campaña"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Filtro por numero"); // NOI18N
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
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltro, gridBagConstraints);

        tblNumeros.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNumeros.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblNumeros);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jLabel8.setText("Campaña Economica"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel8, gridBagConstraints);

        cbCampañas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCampañas1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCampañas1, gridBagConstraints);

        btnNuevo1.setText("Buscar");
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnNuevo1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtNro2.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(txtNro2, gridBagConstraints);

        jLabel5.setText("Numero2 "); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Campaña Economica"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel6, gridBagConstraints);

        txtNro1.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(txtNro1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbCampañas, gridBagConstraints);

        jLabel7.setText("Numero 1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel7, gridBagConstraints);

        jTabbedPane1.addTab("Datos", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnNuevo, gridBagConstraints);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnEditar, gridBagConstraints);

        btnGuardar.setText("Salvar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(btnGuardar, new java.awt.GridBagConstraints());

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnCancelar, gridBagConstraints);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel6.add(btnEliminar, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblSocios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSocios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblSocios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

        rbCI.setText("Cédula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel7.add(rbCI, gridBagConstraints);

        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel7.add(rbNombre, gridBagConstraints);

        rbCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel7.add(rbCodigo, gridBagConstraints);

        txtFiltro2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltro2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFiltro2, gridBagConstraints);

        jLabel9.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel9, gridBagConstraints);

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

        jPanel4.add(jPanel7, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        buscarSocios();

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        NumerosSelecionado = new Numeros();
        limpiaCampos();
        habilitaCampos();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitaCampos();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        numerosDAO = new NumerosDAO();
        List numeros = numerosDAO.BuscaNumeros(Integer.valueOf(txtNro1.getText()), Integer.valueOf(txtNro2.getText()), (CampEconomica) cbCampañas.getSelectedItem());
        if (numeros.isEmpty()) {

            try {
                numerosDAO = new NumerosDAO();
                if (NumerosSelecionado.getId() != null) {

                    NumerosSelecionado.setNro2(Integer.valueOf(txtNro2.getText()));
                    NumerosSelecionado.setNro1(Integer.valueOf(txtNro1.getText()));
                    NumerosSelecionado.setCampEconomica((CampEconomica) cbCampañas.getSelectedItem());

                    numerosDAO.Actualizar(NumerosSelecionado);
                    JOptionPane.showMessageDialog(null, "Numeros actuaizado correctamente");
                } else {
                    NumerosSelecionado.setNro2(Integer.valueOf(txtNro2.getText()));
                    NumerosSelecionado.setNro1(Integer.valueOf(txtNro1.getText()));
                    NumerosSelecionado.setDisponible(Boolean.TRUE);
                    NumerosSelecionado.setCampEconomica((CampEconomica) cbCampañas.getSelectedItem());
                    numerosDAO.Salvar(NumerosSelecionado);
                    JOptionPane.showMessageDialog(null, "Numeros guardado correctamente");
                }
                deshabilitaCampos();
                buscaNumeros();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar datos" + ex);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los numeros ya se encuentran registrados para la campaña seleccionada", "Atención", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        limpiaCampos();
        deshabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {

            numerosDAO.EliminarPorId(Numeros.class, NumerosSelecionado.getId());
            JOptionPane.showMessageDialog(null, "Numeros eliminado correctamente");
            deshabilitaCampos();
            buscaNumeros();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el Numeros, posee transacciones vinculadas" + ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        buscarSocios();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void cbCampañas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCampañas1ActionPerformed
    }//GEN-LAST:event_cbCampañas1ActionPerformed

    private void txtFiltro2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltro2ActionPerformed

        buscarSocios();
    }//GEN-LAST:event_txtFiltro2ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        buscarSocios();
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JComboBox cbCampañas;
    private javax.swing.JComboBox cbCampañas1;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblNumeros;
    private javax.swing.JTable tblSocios;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtFiltro2;
    private javax.swing.JTextField txtNro1;
    private javax.swing.JTextField txtNro2;
    // End of variables declaration//GEN-END:variables
}
