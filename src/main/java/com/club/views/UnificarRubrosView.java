package com.club.views;

import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Caja;
import com.club.BEANS.Rubro;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.RubroDAO;
import com.club.modelos.RubroTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class UnificarRubrosView extends javax.swing.JInternalFrame {

    private RubroDAO rubrosDAO;
    private CajaDAO cajaDAO;
    private List<Rubro> listRubros;
    private List<Rubro> listRubroPrincipal;
    private List<Rubro> listRubrosSecundarios;
    private RubroTableModel tblModel;
    private ListSelectionModel listModelRubros;
    private DefaultListModel modelPrincial;
    private DefaultListModel modelSecundarios;
    private ListSelectionModel listModelRubroPrincipal;
    private ListSelectionModel listModelRubrosSecundarios;
    private Rubro rubroSelecionado;
    private Rubro rubroPrincialSelecionado;
    private Rubro rubroSecundarioSelecionado;

    public UnificarRubrosView() {
        initComponents();
        listRubroPrincipal = new ArrayList<>();
        listRubrosSecundarios = new ArrayList<>();
        listRubros = new ArrayList<>();
        modelPrincial = new DefaultListModel<>();
        modelSecundarios = new DefaultListModel<>();
        DefineModeloTbl();
        buscaTodosLosRegistros();
    }

    private void buscaTodosLosRegistros() {
        rubrosDAO = new RubroDAO();
        listRubros.clear();
        listRubros.addAll(rubrosDAO.BuscaTodos(Rubro.class));
        tblModel.fireTableDataChanged();
        modelPrincial.clear();
        modelSecundarios.clear();
        listRubroPrincipal.clear();
        listRubrosSecundarios.clear();

    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblRubros.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = new RubroTableModel(listRubros);
        tblRubros.setModel(tblModel);

        jListPrincipal.setModel(modelPrincial);
        listModelRubroPrincipal = jListPrincipal.getSelectionModel();
        listModelRubroPrincipal.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (jListPrincipal.getSelectedValue() != null) {

                        rubroPrincialSelecionado = (Rubro) modelPrincial.getElementAt(jListPrincipal.getSelectedIndex());

                        listRubros.remove(tblRubros.getSelectedRow());
                        tblModel.fireTableDataChanged();
                    } else {

                    }
                }
            }
        });

        jListSecundarios.setModel(modelSecundarios);
        listModelRubrosSecundarios = jListSecundarios.getSelectionModel();
        listModelRubrosSecundarios.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (jListSecundarios.getSelectedValue() != null) {

                        rubroSecundarioSelecionado = (Rubro) modelSecundarios.getElementAt(jListSecundarios.getSelectedIndex());
                        tblModel.eliminar(tblRubros.getSelectedRow());
                    } else {

                    }
                }
            }
        });

        listModelRubros = tblRubros.getSelectionModel();
        listModelRubros.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblRubros.getSelectedRow() != -1) {

                        if (modelPrincial.isEmpty()) {
                            btnSeleccionarPrincipal.setEnabled(true);
                        } else {
                            btnSeleccionarPrincipal.setEnabled(false);
                        }
                        rubroSelecionado = listRubros.get(tblRubros.getSelectedRow());

                    } else {

                    }
                }
            }
        });

    }

    void unificarRubros() {

        try {
            Rubro rubroPrincial = listRubroPrincipal.get(0);

            for (Rubro rubroSecundario : listRubrosSecundarios) {
                cajaDAO = new CajaDAO();
                List<Caja> movimientos = cajaDAO.BuscaMovimientosPorRubro(rubroSecundario);
                for (Caja movimiento : movimientos) {
                    cajaDAO = new CajaDAO();
                    movimiento.setRubro(rubroPrincial);
                    cajaDAO.Actualizar(movimiento);

                }
                rubrosDAO = new RubroDAO();
                rubrosDAO.EliminarPorId(Rubro.class, rubroSecundario.getId());
            }
            JOptionPane.showMessageDialog(null, "Actualización realizada correctamente");
            buscaTodosLosRegistros();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar registros", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRubros = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListPrincipal = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListSecundarios = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        btnQuitarSecundario = new javax.swing.JButton();
        btnQuitarPrincipal = new javax.swing.JButton();
        btnSeleccionarPrincipal = new javax.swing.JButton();
        btnSeleccionarSecundario = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(950, 700));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Rubros"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Rubro principal"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        tblRubros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Valor fijo", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Double.class
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
        tblRubros.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRubros);
        if (tblRubros.getColumnModel().getColumnCount() > 0) {
            tblRubros.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblRubros.getColumnModel().getColumn(3).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jScrollPane2.setViewportView(jListPrincipal);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jScrollPane3.setViewportView(jListSecundarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Rubros secundarios"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        btnQuitarSecundario.setText("Quitar secundario seleccionado");
        btnQuitarSecundario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarSecundarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        jPanel2.add(btnQuitarSecundario, gridBagConstraints);

        btnQuitarPrincipal.setText("Quitar principal seleccionado");
        btnQuitarPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarPrincipalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanel2.add(btnQuitarPrincipal, gridBagConstraints);

        btnSeleccionarPrincipal.setText("Seleccionar rubro principal");
        btnSeleccionarPrincipal.setEnabled(false);
        btnSeleccionarPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarPrincipalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel2.add(btnSeleccionarPrincipal, gridBagConstraints);

        btnSeleccionarSecundario.setText("Seleccionar rubro secundario");
        btnSeleccionarSecundario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarSecundarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel2.add(btnSeleccionarSecundario, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setText("Unificar Rubros");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        jPanel6.add(btnGuardar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        unificarRubros();

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnQuitarSecundarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarSecundarioActionPerformed

        modelSecundarios.removeElement(rubroSecundarioSelecionado);
        listRubrosSecundarios.remove(rubroSecundarioSelecionado);

    }//GEN-LAST:event_btnQuitarSecundarioActionPerformed

    private void btnQuitarPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarPrincipalActionPerformed

        modelPrincial.removeElement(rubroPrincialSelecionado);
        listRubroPrincipal.remove(rubroPrincialSelecionado);

    }//GEN-LAST:event_btnQuitarPrincipalActionPerformed

    private void btnSeleccionarPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarPrincipalActionPerformed

        modelPrincial.addElement(rubroSelecionado);
        listRubroPrincipal.add(rubroSelecionado);
        tblModel.eliminar(tblRubros.getSelectedRow());

    }//GEN-LAST:event_btnSeleccionarPrincipalActionPerformed

    private void btnSeleccionarSecundarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarSecundarioActionPerformed

        modelSecundarios.addElement(rubroSelecionado);
        listRubrosSecundarios.add(rubroSelecionado);
        tblModel.eliminar(tblRubros.getSelectedRow());

    }//GEN-LAST:event_btnSeleccionarSecundarioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitarPrincipal;
    private javax.swing.JButton btnQuitarSecundario;
    private javax.swing.JButton btnSeleccionarPrincipal;
    private javax.swing.JButton btnSeleccionarSecundario;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jListPrincipal;
    private javax.swing.JList jListSecundarios;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblRubros;
    // End of variables declaration//GEN-END:variables
}
