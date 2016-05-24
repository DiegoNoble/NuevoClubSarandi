package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public final class FormArqueos extends javax.swing.JInternalFrame {

    private CobradorDAO cobradorDAO;
    private CcCobradorDAO ccCobradorDAO;
    private List<CcCobrador> listMovimientos;
    private Cobrador cobradorSeleccionado;
    private DefaultTableModel tblModel;
    private ListSelectionModel listModelSocios;

    public FormArqueos() {
        initComponents();

        DefineModeloTbl();
        cargaComboBox();

    }

    private void cargaComboBox() {

        try {
            cobradorDAO = new CobradorDAO();
            List<Cobrador> listCobradores = cobradorDAO.BuscaTodos(Cobrador.class);
            for (Cobrador cobrador : listCobradores) {
                cbCobrador.addItem(cobrador);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar categorias y cobradores" + e);
            e.printStackTrace();
        }

    }

    private void buscaTodosLosRegistros(Cobrador cobrador) {

        try {

            ccCobradorDAO = new CcCobradorDAO();
            listMovimientos = ccCobradorDAO.BuscaCcCobrador(cobradorSeleccionado);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar movimientos " + ex, "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblSocio.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = (DefaultTableModel) tblSocio.getModel();


    }

    private void muestraContenidoTbl() {

        try {

            tblModel.setNumRows(0);

            Double debitos = 0.0;
            Double creditos = 0.0;

            if (!listMovimientos.isEmpty()) {

                for (CcCobrador movimientos : listMovimientos) {
                    tblModel.addRow(new Object[]{
                        movimientos.getId(),
                        movimientos.getCobrador(),
                        movimientos.getFechaMovimiento(),
                        movimientos.getDescripcion(),
                        movimientos.getDebito(),
                        movimientos.getCredito()});
                    debitos = debitos + movimientos.getDebito();
                    creditos = creditos + movimientos.getCredito();
                }

                txtDebitos.setText(String.valueOf(debitos));
                txtCreditos.setText(String.valueOf(creditos));
                txtSaldo.setText(String.valueOf(debitos - creditos));
            }
        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No fue posible cargar los socios" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void habitlitaCampos() {
        cbTipo.setEnabled(true);
        txtDescripcion.setEnabled(true);
        txtValor.setEnabled(true);
        btnSalvar.setEnabled(true);
    }

    private void deshabitlitaCampos() {
        cbTipo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        txtValor.setEnabled(false);
        btnSalvar.setEnabled(false);
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
        cbCobrador = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        txtCreditos = new javax.swing.JTextField();
        txtDebitos = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtDescripcion = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        cbTipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnAcientoManual = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 650));
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
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblSocio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Movimiento", "Cobrador", "Fecha Movimiento", "Descripción", "Débito", "Crédito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        tblSocio.getColumnModel().getColumn(0).setPreferredWidth(15);
        tblSocio.getColumnModel().getColumn(0).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(1).setPreferredWidth(40);
        tblSocio.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(2).setPreferredWidth(60);
        tblSocio.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
        tblSocio.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblSocio.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblSocio.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
        tblSocio.getColumnModel().getColumn(5).setPreferredWidth(30);
        tblSocio.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        cbCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCobradorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCobrador, gridBagConstraints);

        jLabel4.setText("Cobrador"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("Debitos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(jLabel6, gridBagConstraints);

        jLabel5.setText("Saldo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(jLabel5, gridBagConstraints);

        jLabel2.setText("Créditos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        jPanel4.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSaldo, gridBagConstraints);

        txtCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreditosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtCreditos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtDebitos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(jPanel4, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Aciento Manual"));
        jPanel3.setName(""); // NOI18N
        jPanel3.setLayout(new java.awt.GridBagLayout());

        txtDescripcion.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtDescripcion, gridBagConstraints);

        txtValor.setEnabled(false);
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtValor, gridBagConstraints);

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Débito", "Crédito" }));
        cbTipo.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbTipo, gridBagConstraints);

        jLabel3.setText("Tipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel7.setText("Descripción");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel8, gridBagConstraints);

        btnSalvar.setText("Confirmar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel3.add(btnSalvar, gridBagConstraints);

        btnAcientoManual.setText("Nuevo");
        btnAcientoManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcientoManualActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanel3.add(btnAcientoManual, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jPanel3, gridBagConstraints);
        jPanel3.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCobradorActionPerformed

        cobradorSeleccionado = (Cobrador) cbCobrador.getSelectedItem();
        buscaTodosLosRegistros(cobradorSeleccionado);
        muestraContenidoTbl();
    }//GEN-LAST:event_cbCobradorActionPerformed

    private void txtCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreditosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreditosActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro que desea registrar el aciento?");

        if (confirmacion == JOptionPane.YES_OPTION) {


            String tipo = (String) cbTipo.getSelectedItem();

            try {
                switch (tipo) {

                    case "Débito":
                        CcCobrador debito = new CcCobrador();
                        debito.setCobrador((Cobrador) cbCobrador.getSelectedItem());
                        debito.setCredito(0.0);
                        debito.setDebito(Double.valueOf(txtValor.getText()));
                        debito.setDescripcion(txtDescripcion.getText());
                        debito.setFechaMovimiento(new Date());

                        ccCobradorDAO = new CcCobradorDAO();
                        ccCobradorDAO.Salvar(debito);
                        break;

                    case "Crédito":
                        CcCobrador credito = new CcCobrador();
                        credito.setCobrador((Cobrador) cbCobrador.getSelectedItem());
                        credito.setCredito(Double.valueOf(txtValor.getText()));
                        credito.setDebito(0.0);
                        credito.setDescripcion(txtDescripcion.getText());
                        credito.setFechaMovimiento(new Date());

                        ccCobradorDAO = new CcCobradorDAO();
                        ccCobradorDAO.Salvar(credito);
                        break;


                }
                JOptionPane.showMessageDialog(null, "Correcto!");
                deshabitlitaCampos();
                cobradorSeleccionado = (Cobrador) cbCobrador.getSelectedItem();
                buscaTodosLosRegistros(cobradorSeleccionado);
                muestraContenidoTbl();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al guardar en BD", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased

        txtValor.setText(txtValor.getText().replaceAll("^[0-9],^[.]", ""));
    }//GEN-LAST:event_txtValorKeyReleased

    private void btnAcientoManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcientoManualActionPerformed

        habitlitaCampos();

    }//GEN-LAST:event_btnAcientoManualActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcientoManual;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbCobrador;
    private javax.swing.JComboBox cbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSocio;
    private javax.swing.JTextField txtCreditos;
    private javax.swing.JTextField txtDebitos;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
