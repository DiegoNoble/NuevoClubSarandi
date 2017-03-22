package com.club.views;

import Utilidades.AjustaColumnas;
import Utilidades.ControlarEntradaTexto;
import com.club.BEANS.CampEconomica;
import com.club.BEANS.Cobrador;
import com.club.BEANS.CuotaCampEconomica;
import com.club.BEANS.Numeros;
import com.club.BEANS.Socio;
import com.club.BEANS.VentaCampEco;
import com.club.DAOs.CampEconomicaDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.CuotaCampEconomicaDAO;
import com.club.DAOs.NumerosDAO;
import com.club.DAOs.SocioDAO;
import com.club.DAOs.VentaCampEcoDAO;
import com.club.tableModels.NumerosTableModel;
import com.club.tableModels.SocioTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public final class VentasCampEcoView extends javax.swing.JInternalFrame {

    NumerosDAO numerosDAO;
    CampEconomicaDAO campEconomicaDAO;
    List<Numeros> listNros;
    NumerosTableModel tblModelNumeros;
    ListSelectionModel listModelNumeross;
    ListSelectionModel listModelSocios;
    Numeros numerosSelecionados;
    Socio socioSeleccionado;
    SocioDAO socioDAO;
    SocioTableModel tblModelSocios;
    List<Socio> listSocios;

    public VentasCampEcoView() {
        initComponents();
        dpVencimiento.setDate(new Date());
        listNros = new ArrayList<>();
        buscaCampañas();
        TblModelNumeros();
        TblModelSocios();
        buscaTodoslosNumeros();
        buscarSocios();
        buscaCobradores();

        Character chs[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        txtFiltro.setDocument(new ControlarEntradaTexto(3, chs));
        txtValor.setDocument(new ControlarEntradaTexto(4, chs));
        txtCuotas.setDocument(new ControlarEntradaTexto(2, chs));
    }

    void buscaCobradores() {
        CobradorDAO cobradorDAO = new CobradorDAO();
        List<Cobrador> listCobradores = cobradorDAO.BuscaTodos(Cobrador.class);
        cbCobrador.removeAllItems();
        for (Cobrador cobrador : listCobradores) {
            cbCobrador.addItem(cobrador);
        }
    }

    private void buscaCampañas() {
        campEconomicaDAO = new CampEconomicaDAO();
        List<CampEconomica> campañas = campEconomicaDAO.BuscaTodos(CampEconomica.class);
        for (CampEconomica campaña : campañas) {
            cbCampañas1.addItem(campaña);
        }
    }

    private void buscaTodoslosNumeros() {
        listNros.clear();
        numerosDAO = new NumerosDAO();
        listNros.addAll(numerosDAO.BuscaTodos(Numeros.class));
        tblModelNumeros.fireTableDataChanged();
    }

    private void buscarSocios() {

        if (rbCodigo.isSelected()) {
            Socio socio = new Socio();
            socioDAO = new SocioDAO();
            socio = (Socio) socioDAO.BuscaPorID(Socio.class, Integer.parseInt(txtFiltroSocio.getText()));

            listSocios.clear();
            listSocios.add(socio);
            tblModelSocios.fireTableDataChanged();

        } else if (rbCI.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios.clear();
            listSocios.addAll(socioDAO.BuscaPorCI(txtFiltroSocio.getText()));
            tblModelSocios.fireTableDataChanged();

        } else if (rbNombre.isSelected()) {
            socioDAO = new SocioDAO();
            listSocios.clear();
            listSocios.addAll(socioDAO.BuscaPorNombre(txtFiltroSocio.getText()));
            tblModelSocios.fireTableDataChanged();

        }

    }

    private void TblModelSocios() {

        ((DefaultTableCellRenderer) tblSocios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listSocios = new ArrayList<Socio>();
        socioDAO = new SocioDAO();
        listSocios.addAll(socioDAO.BuscaTodos(Socio.class));
        tblModelSocios = new SocioTableModel(listSocios);
        tblSocios.setModel(tblModelSocios);

        int[] anchos = {60, 300, 17, 100, 100};
        new AjustaColumnas().ajustar(tblSocios, anchos);

        //tblSocios.setRowHeight(25);
        TableColumn column = tblSocios.getColumn("Categoria");
        tblSocios.removeColumn(column);

        listModelSocios = tblSocios.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocios.getSelectedRow() != -1) {

                        socioSeleccionado = listSocios.get(tblSocios.getSelectedRow());
                    } else {
                    }
                }
            }
        });
    }

    private void TblModelNumeros() {

        ((DefaultTableCellRenderer) tblNumeros.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tblModelNumeros = new NumerosTableModel(listNros);
        tblNumeros.setModel(tblModelNumeros);

        int[] anchos = {20, 100, 100, 100, 50};
        new AjustaColumnas().ajustar(tblNumeros, anchos);

        listModelNumeross = tblNumeros.getSelectionModel();
        listModelNumeross.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblNumeros.getSelectedRow() != -1) {

                        numerosSelecionados = listNros.get(tblNumeros.getSelectedRow());
                    } else {
                    }
                }
            }
        });

    }

    private void buscarNumeros() {

        listNros.clear();
        if (txtFiltro.getText().equals("")) {
            buscaTodoslosNumeros();
        } else {
            numerosDAO = new NumerosDAO();
            listNros.addAll(numerosDAO.BuscaNumeros(Integer.valueOf(txtFiltro.getText()),
                    Integer.valueOf(txtFiltro.getText()), (CampEconomica) cbCampañas1.getSelectedItem()));
            tblModelNumeros.fireTableDataChanged();
        }

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
        txtCuotas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dpVencimiento = new org.jdesktop.swingx.JXDatePicker();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbCobrador = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        btnGenerar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSocios = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltroSocio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1024, 500));
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

        tblNumeros.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(txtCuotas, gridBagConstraints);

        jLabel5.setText("Valor"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(txtValor, gridBagConstraints);

        jLabel10.setText("Primer vencimiento"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dpVencimiento, gridBagConstraints);

        jLabel11.setText("Cantidad de cuotas"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel11, gridBagConstraints);

        jLabel12.setText("Cobrador"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel12, gridBagConstraints);

        cbCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCobradorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbCobrador, gridBagConstraints);

        jTabbedPane1.addTab("Datos", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnGenerar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        tblSocios.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
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

        grupoBusqueda.add(rbCI);
        rbCI.setText("Cédula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel7.add(rbCI, gridBagConstraints);

        grupoBusqueda.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel7.add(rbNombre, gridBagConstraints);

        grupoBusqueda.add(rbCodigo);
        rbCodigo.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel7.add(rbCodigo, gridBagConstraints);

        txtFiltroSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroSocioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel7.add(txtFiltroSocio, gridBagConstraints);

        jLabel9.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
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
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
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

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed

        if (socioSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un socio", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (numerosSelecionados == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un nro", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtCuotas.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la cantidada de cuotas", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (txtValor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el valor", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (numerosSelecionados.getDisponible() == false) {
            JOptionPane.showMessageDialog(null, "El nro seleccionado no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Double valor = Double.valueOf(txtValor.getText());
                Integer cuotas = Integer.valueOf(txtCuotas.getText());
                Date fecha = dpVencimiento.getDate();
                Cobrador cobrador = (Cobrador) cbCobrador.getSelectedItem();

                VentaCampEco venta = new VentaCampEco();
                venta.setCantidadCuotas(cuotas);
                venta.setCobrador(cobrador);
                venta.setFechaVenta(new Date());
                venta.setNumeros(numerosSelecionados);
                venta.setSocio(socioSeleccionado);
                venta.setValor(valor);
                venta.setCampEconomica((CampEconomica) cbCampañas1.getSelectedItem());
                VentaCampEcoDAO campEcoDAO = new VentaCampEcoDAO();
                campEcoDAO.Salvar(venta);
                
                numerosSelecionados.setDisponible(false);
                numerosDAO = new NumerosDAO();
                numerosDAO.Actualizar(numerosSelecionados);

                for (int i = 1; i <= cuotas; i++) {
                    CuotaCampEconomica cuota = new CuotaCampEconomica();
                    cuota.setEnviado(false);

                    Calendar vencimiento = Calendar.getInstance();
                    vencimiento.setTime(fecha);
                    vencimiento.add(Calendar.MONTH, i);
                    cuota.setFechaVencimiento(vencimiento.getTime());

                    cuota.setPago(false);
                    cuota.setValor(valor / cuotas);
                    cuota.setVentaCampEco(venta);
                    cuota.setNroCuota(i);

                    CuotaCampEconomicaDAO cuotaDAO = new CuotaCampEconomicaDAO();
                    cuotaDAO.Salvar(cuota);
                }
                
                JOptionPane.showMessageDialog(null, "Cuotas generadas correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        buscarNumeros();
        

    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        buscarNumeros();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void cbCampañas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCampañas1ActionPerformed
    }//GEN-LAST:event_cbCampañas1ActionPerformed

    private void txtFiltroSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroSocioActionPerformed

        buscarSocios();
    }//GEN-LAST:event_txtFiltroSocioActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        buscarSocios();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCobradorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCobradorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JComboBox cbCampañas1;
    private javax.swing.JComboBox cbCobrador;
    private org.jdesktop.swingx.JXDatePicker dpVencimiento;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField txtCuotas;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtFiltroSocio;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
