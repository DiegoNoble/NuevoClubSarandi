package com.club.views;

import Utilidades.AjustaColumnas;
import com.club.BEANS.CampEconomica;
import com.club.BEANS.Cobrador;
import com.club.BEANS.CuotaCampEconomica;
import com.club.BEANS.EntregaPremio;
import com.club.BEANS.Numeros;
import com.club.BEANS.Premio;
import com.club.BEANS.VentaCampEco;
import com.club.DAOs.CampEconomicaDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.CuotaCampEconomicaDAO;
import com.club.DAOs.EntregaPremioDAO;
import com.club.DAOs.NumerosDAO;
import com.club.DAOs.PremioDAO;
import com.club.DAOs.VentaCampEcoDAO;
import com.club.tableModels.VentasCAmpEcoTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class AsignarPremiosCampEcoView extends javax.swing.JInternalFrame {

    private VentaCampEcoDAO ventaCampEcoDAO;
    private CampEconomicaDAO campEconomicaDAO;
    private List<VentaCampEco> listVentas;
    private VentasCAmpEcoTableModel tblModelVentas;
    private ListSelectionModel listModelVentaCampEcos;
    private VentaCampEco VentaCampEcoSelecionado;
    NumerosDAO numerosDAO;
    PremioDAO premioDAO;

    public AsignarPremiosCampEcoView() {
        initComponents();
        listVentas = new ArrayList<>();
        DefineModeloTbl();
        buscaCobradores();
        buscaCampañas();

        buscaPremios();
        dpFecha.setDate(new Date());
        filtros();
    }

    private void buscaCampañas() {
        campEconomicaDAO = new CampEconomicaDAO();
        List<CampEconomica> campañas = campEconomicaDAO.BuscaTodos(CampEconomica.class);
        for (CampEconomica campaña : campañas) {
            cbCampañas.addItem(campaña);
        }
    }

    void buscaPremios() {
        PremioDAO premiosDAO = new PremioDAO();
        List<Premio> premios = premiosDAO.BuscaTodosPorCampaña((CampEconomica) cbCampañas.getSelectedItem());
        cbPremios.removeAllItems();
        for (Premio premio : premios) {

            cbPremios.addItem(premio);
        }
    }

    void buscaCobradores() {
        CobradorDAO cobradorDAO = new CobradorDAO();
        List<Cobrador> listCobradores = cobradorDAO.BuscaTodos(Cobrador.class);
        cbCobrador.removeAllItems();
        cbCobrador.addItem("Todos");
        for (Cobrador cobrador : listCobradores) {

            cbCobrador.addItem(cobrador);
        }
    }

    public void filtros() {
        listVentas.clear();
        if (cbCobrador.getSelectedItem().equals("Todos")) {
            if (rbCodigo.isSelected() && txtFiltroSocio.getText().equals("")) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorCamp((CampEconomica) cbCampañas.getSelectedItem()));
                tblModelVentas.fireTableDataChanged();

            } else if (rbNombre.isSelected()) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorNombreSocio((CampEconomica) cbCampañas.getSelectedItem(), txtFiltroSocio.getText()));
                tblModelVentas.fireTableDataChanged();

            } else if (rbCodigo.isSelected() && !txtFiltroSocio.getText().equals("")) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorIdSocio((CampEconomica) cbCampañas.getSelectedItem(), Integer.valueOf(txtFiltroSocio.getText())));
                tblModelVentas.fireTableDataChanged();
            }
        } else {
            listVentas.clear();
            if (rbCodigo.isSelected() && txtFiltroSocio.getText().equals("")) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorCampYCobraor((CampEconomica) cbCampañas.getSelectedItem(), (Cobrador) cbCobrador.getSelectedItem()));
                tblModelVentas.fireTableDataChanged();

            } else if (rbNombre.isSelected()) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorNombreSocioyCobrador((CampEconomica) cbCampañas.getSelectedItem(), txtFiltroSocio.getText(), (Cobrador) cbCobrador.getSelectedItem()));
                tblModelVentas.fireTableDataChanged();

            } else if (rbCodigo.isSelected() && !txtFiltroSocio.getText().equals("")) {
                ventaCampEcoDAO = new VentaCampEcoDAO();
                listVentas.addAll(ventaCampEcoDAO.BuscaVentasPorIdSocioYCobrador((CampEconomica) cbCampañas.getSelectedItem(), Integer.valueOf(txtFiltroSocio.getText()), (Cobrador) cbCobrador.getSelectedItem()));

                tblModelVentas.fireTableDataChanged();
            }
        }
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        tblModelVentas = new VentasCAmpEcoTableModel(listVentas);
        tbl.setModel(tblModelVentas);
        int[] anchos = {10, 300, 100, 50, 50, 50};
        new AjustaColumnas().ajustar(tbl, anchos);

        listModelVentaCampEcos = tbl.getSelectionModel();
        listModelVentaCampEcos.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tbl.getSelectedRow() != -1) {
                        VentaCampEcoSelecionado = listVentas.get(tbl.getSelectedRow());
                        btnRegistraPago.setEnabled(true);
                    } else {
                        btnRegistraPago.setEnabled(false);
                    }
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupoBusqueda = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        cbCampañas = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        rbNombre = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltroSocio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        cbCobrador = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        btnRegistraPago = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cbPremios = new javax.swing.JComboBox();
        dpFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel11 = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

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
        jLabel1.setText("Premios por camp económica"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbl);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jLabel8.setText("Campaña Economica"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel8, gridBagConstraints);

        cbCampañas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCampañasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCampañas, gridBagConstraints);

        jPanel7.setLayout(new java.awt.GridBagLayout());

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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        jPanel2.add(jPanel7, gridBagConstraints);

        jLabel13.setText("Cobrador"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jLabel13, gridBagConstraints);

        cbCobrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCobradorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCobrador, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnRegistraPago.setText("Asignar premio");
        btnRegistraPago.setEnabled(false);
        btnRegistraPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraPagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(btnRegistraPago, gridBagConstraints);

        jLabel10.setText("Observaciones"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel6.add(jLabel10, gridBagConstraints);

        cbPremios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPremiosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cbPremios, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(dpFecha, gridBagConstraints);

        jLabel11.setText("Premios"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel6.add(jLabel11, gridBagConstraints);

        txtObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtObs, gridBagConstraints);

        jLabel12.setText("Premios"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel6.add(jLabel12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnRegistraPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraPagoActionPerformed

        try {
            EntregaPremio entregaPremio = new EntregaPremio();
            entregaPremio.setFecha(dpFecha.getDate());
            entregaPremio.setPremio((Premio) cbPremios.getSelectedItem());
            entregaPremio.setObs(txtObs.getText());
            entregaPremio.setVentaCampEco(VentaCampEcoSelecionado);
            EntregaPremioDAO entregaPremioDAO = new EntregaPremioDAO();
            entregaPremioDAO.Salvar(entregaPremio);
            JOptionPane.showMessageDialog(null, "Premio asignado correctamente!", "Correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar datos", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnRegistraPagoActionPerformed

    private void cbCampañasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCampañasActionPerformed

        buscaPremios();
    }//GEN-LAST:event_cbCampañasActionPerformed

    private void txtFiltroSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroSocioActionPerformed

        filtros();
    }//GEN-LAST:event_txtFiltroSocioActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void cbPremiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPremiosActionPerformed

        //VentaCampEcoSelecionado = listVentas.get(tbl.getSelectedRow());

    }//GEN-LAST:event_cbPremiosActionPerformed

    private void txtObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObsActionPerformed

    private void cbCobradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCobradorActionPerformed

        //VentaCampEcoSelecionado = listVentas.get(tbl.getSelectedRow());
    }//GEN-LAST:event_cbCobradorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegistraPago;
    private javax.swing.JComboBox cbCampañas;
    private javax.swing.JComboBox cbCobrador;
    private javax.swing.JComboBox cbPremios;
    private org.jdesktop.swingx.JXDatePicker dpFecha;
    private javax.swing.ButtonGroup grupoBusqueda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtFiltroSocio;
    private javax.swing.JTextField txtObs;
    // End of variables declaration//GEN-END:variables
}
