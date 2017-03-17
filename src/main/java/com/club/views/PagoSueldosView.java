package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.Funcionario;
import com.club.BEANS.Rubro;
import com.club.BEANS.SectoresFuncionario;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.FuncionarioDAO;
import com.club.DAOs.RubroDAO;
import com.club.DAOs.SectoresFuncionarioDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.control.utilidades.ImprimiRecibo;
import com.club.tableModels.FuncionarioTableModel;
import com.club.tableModels.SectoresFuncionarioTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class PagoSueldosView extends javax.swing.JInternalFrame {

    private FuncionarioDAO funcionarioDAO;
    CajaDAO cajaDAO;
    private List<Funcionario> listFuncionarios;
    private FuncionarioTableModel tblModelFuncionario;
    private ListSelectionModel listModelFuncionarios;
    private Funcionario funcionarioSeleccionado;
    private Funcionario funcionario;
    private HashMap parametros;
    private List<SectoresFuncionario> listSectoresFuncionario;
    SectoresFuncionarioTableModel tblModelSectoresFuncionario;
    ListSelectionModel listModelSectores;
    SectoresFuncionarioDAO sectoresFuncionarioDAO;
    RubroDAO rubroDAO;
    int cifras = (int) Math.pow(10, 2);
    private String nombreUsuario;

    public PagoSueldosView(String nombreUsuario) {
        initComponents();
        this.nombreUsuario = nombreUsuario;
        parametros = new HashMap();
        DefineModeloTbl();
        actualizaComboBox();
        buscaTodosLosRegistros();
        configTblSectores();

    }

    private void buscaTodosLosRegistros() {
        funcionarioDAO = new FuncionarioDAO();
        listFuncionarios.addAll(funcionarioDAO.BuscaTodos(Funcionario.class));
        tblModelFuncionario.fireTableDataChanged();
    }

    public void actualizaComboBox() {

        try {

            rubroDAO = new RubroDAO();
            List<Rubro> listRubros = rubroDAO.BuscaTodos(Rubro.class);
            for (Rubro rubro : listRubros) {
                cbRubro.addItem(rubro);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar comboboxes: " + ex);
            ex.printStackTrace();
        }
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
                        buscaSectoresAsignados();
                    }

                }
            }
        });

    }

    void configTblSectores() {
        ((DefaultTableCellRenderer) tblSectores.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSectoresFuncionario = new ArrayList<>();
        tblModelSectoresFuncionario = new SectoresFuncionarioTableModel(listSectoresFuncionario);
        tblSectores.setModel(tblModelSectoresFuncionario);

        tblSectores.setRowHeight(20);
        //tblSectores.removeColumn(tblSectores.getColumn("Inmueble"));
        //tblSectores.getColumn("Sector").setCellEditor(new ComboBoxCellEditor(new ComboSectores()));

    }

    void buscaSectoresAsignados() {
        listSectoresFuncionario.clear();

        sectoresFuncionarioDAO = new SectoresFuncionarioDAO();

        listSectoresFuncionario.addAll(sectoresFuncionarioDAO.BuscaPorFuncionario(funcionarioSeleccionado));
        tblModelSectoresFuncionario.fireTableDataChanged();
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
            listFuncionarios.clear();
            listFuncionarios.addAll(funcionarioDAO.BuscaPorCI(txtFiltro.getText()));
            tblModelFuncionario.fireTableDataChanged();

        } else if (rbNombre.isSelected()) {
            funcionarioDAO = new FuncionarioDAO();
            listFuncionarios.clear();
            listFuncionarios.addAll(funcionarioDAO.BuscaPorNombre(txtFiltro.getText()));
            tblModelFuncionario.fireTableDataChanged();

        }

    }

    Double buscaSaldoAnterior() {
        Double saldoAnterior = 0.0;
        cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnterior().getSaldo();
        return saldoAnterior;
    }

    Double buscaSaldoAnteriorFecha(Date fecha) {
        Double saldoAnterior = 0.0;
        cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnteriorFecha(fecha).getSaldo();
        return saldoAnterior;
    }

    void ajustaSaldos(Double saldo) {
        try {
            cajaDAO = new CajaDAO();
            List<Caja> todos = cajaDAO.BuscaTodosOrdenaPorIDFyFecha(dataPiker.getDate());

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
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbRubro = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtConcepto = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dataPiker = new org.jdesktop.swingx.JXDatePicker();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSectores = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(750, 550));
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

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Concepto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jLabel2, gridBagConstraints);

        cbRubro.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        cbRubro.setForeground(new java.awt.Color(153, 0, 0));
        cbRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRubroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbRubro, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Importe total");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(jLabel5, gridBagConstraints);

        txtValor.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        txtValor.setForeground(new java.awt.Color(153, 0, 0));
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtValor, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jButton1.setText("Registrar Pago");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jButton1, gridBagConstraints);

        txtConcepto.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtConcepto, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Rubro");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jLabel6, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel8, gridBagConstraints);

        dataPiker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPikerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(dataPiker, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribición Sectores"));
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();

    }//GEN-LAST:event_txtFiltroActionPerformed

    private void cbRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRubroActionPerformed
        Rubro rubroSeleccionado = (Rubro) cbRubro.getSelectedItem();
        if (rubroSeleccionado.getValorFijo() == true) {
            txtValor.setEditable(false);
            txtValor.setText(rubroSeleccionado.getValor().toString());

        } else {
            txtValor.setEditable(true);

        }
    }//GEN-LAST:event_cbRubroActionPerformed

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        txtValor.setText(txtValor.getText().replaceAll("[^0-9 | ^.]", ""));
    }//GEN-LAST:event_txtValorKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            Double importe = Double.valueOf(txtValor.getText());
            if (listSectoresFuncionario.isEmpty()) {
                throw new Exception("Debe asignar al menos 1 sector al 100%");
            }
            for (SectoresFuncionario sector : listSectoresFuncionario) {

                Caja movimiento = new Caja();
                movimiento.setConcepto(txtConcepto.getText());
                movimiento.setRubro((Rubro) cbRubro.getSelectedItem());
                movimiento.setFechaMovimiento(dataPiker.getDate());
                movimiento.setSectores(sector.getSector());

                movimiento.setEntrada(0.0);
                movimiento.setUsuario(nombreUsuario);
                movimiento.setSalida((importe * sector.getProcentageSector()) / 100);
                //movimiento.setSaldo(buscaSaldoAnterior() + movimiento.getEntrada());

                cajaDAO = new CajaDAO();
                cajaDAO.Salvar(movimiento);
                ajustaSaldos(buscaSaldoAnteriorFecha(dataPiker.getDate()));
                JOptionPane.showMessageDialog(null, "Movimiento registrado correctamente!");
                new ImprimiRecibo().imprimieRecibo(movimiento);

            }

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, "Error " + error);
            error.printStackTrace();
        }
        txtValor.setText("");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void dataPikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPikerActionPerformed


    }//GEN-LAST:event_dataPikerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbRubro;
    private org.jdesktop.swingx.JXDatePicker dataPiker;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblFuncionario;
    public javax.swing.JTable tblSectores;
    private javax.swing.JFormattedTextField txtConcepto;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
