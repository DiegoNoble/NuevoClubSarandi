package com.club.views;

import com.club.BEANS.Dependiente;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
import com.club.huellas.BioMini;
import com.club.tableModels.DependienteTableModel;
import com.club.tableModels.SocioTableModelControlAcceso;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsultaMensualidadesXHuella extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    SocioDAO socioDAO;
    Socio socioSeleccionado;
    SocioTableModelControlAcceso tblModelSocio;
    DependienteTableModel tblModelDependiente;
    List<Mensualidades> listMensualidades;
    List<Socio> listSocios;
    List<Dependiente> listDependientes;
    ImageIcon foto;
    Icon icono;

    BioMini bioMini;
    DepDAO daoD;
    Dependiente dependienteSeleccionado;

    public ConsultaMensualidadesXHuella() {

        initComponents();
        jlblActivo.setVisible(false);
        jlblInactivo.setVisible(false);
        defineModelo();

    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblSocios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listSocios = new ArrayList();
        tblModelSocio = new SocioTableModelControlAcceso(listSocios);
        tblSocios.setModel(tblModelSocio);
        int[] anchos = {5, 400, 5, 5, 5, 5};

        for (int i = 0; i < tblSocios.getColumnCount(); i++) {

            tblSocios.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        }
        tblSocios.setRowHeight(25);

        ((DefaultTableCellRenderer) tblDependientes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        listDependientes = new ArrayList();
        tblModelDependiente = new DependienteTableModel(listDependientes);
        tblDependientes.setModel(tblModelDependiente);
        int[] anchosD = {5, 400, 5, 5, 5, 5};

        for (int i = 0; i < tblDependientes.getColumnCount(); i++) {

            tblDependientes.getColumnModel().getColumn(i).setPreferredWidth(anchosD[i]);

        }
        tblDependientes.setRowHeight(25);

        ListSelectionModel listModelSocios = tblSocios.getSelectionModel();
        listModelSocios.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblSocios.getSelectedRow() != -1) {

                        socioSeleccionado = listSocios.get(tblSocios.getSelectedRow());
                    }
                    if (socioSeleccionado.getSituacion().equals("Activo")) {

                        jlblActivo.setVisible(true);
                        jlblInactivo.setVisible(false);
                    } else {
                        jlblActivo.setVisible(false);
                        jlblInactivo.setVisible(true);
                    }

                    muestraDependientes();
                }
            }

        });

    }

    private void filtros() {
        listSocios.clear();

        socioDAO = new SocioDAO();
        listSocios.addAll(socioDAO.FiltroInteligenteSocios(txtFiltro.getText()));
        tblModelSocio.fireTableDataChanged();

        daoD = new DepDAO();
        listDependientes.clear();
        listDependientes.addAll(daoD.FiltroInteligenteDependiente(txtFiltro.getText()));
        if (listDependientes.isEmpty() && listSocios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Dependiente dependiente : listDependientes) {
                daoD = new DepDAO();

                listSocios.add(daoD.BuscaTitular(dependiente).getSocio());
                tblModelSocio.fireTableDataChanged();
            }

        }

    }

    void muestraDependientes() {
        listDependientes.clear();
        socioDAO = new SocioDAO();
        listDependientes.addAll(socioDAO.BuscaDependientes(socioSeleccionado));
        tblModelDependiente.fireTableDataChanged();
    }

    public Socio getSocioSeleccionado() {
        return socioSeleccionado;
    }

    public void setSocioSeleccionado(Socio socioSeleccionado) {
        try {
            listSocios.clear();
            listSocios.add(socioSeleccionado);
            tblModelSocio.fireTableDataChanged();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiaCampos();
        }

    }

    public Dependiente getDependienteSeleccionado() {
        return dependienteSeleccionado;
    }

    public void setDependienteSeleccionado(Dependiente dependienteSeleccionado) {
        try {
            listSocios.clear();
            listSocios.add(dependienteSeleccionado.getSocio());
            tblModelSocio.fireTableDataChanged();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiaCampos();
        }

    }

    public void limpiaCampos() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnIdentificar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jlblActivo = new javax.swing.JLabel();
        jlblInactivo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSocios = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDependientes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel7.setLayout(new java.awt.GridBagLayout());

        txtFiltro.setToolTipText("Digite Cod. Socio o Nombre o C.I.");
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFiltro, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnBuscar, gridBagConstraints);

        btnIdentificar.setText("Buscar Socio por Huella");
        btnIdentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnIdentificar, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Buscar por:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel4, gridBagConstraints);

        jlblActivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pulgar_arriba.png"))); // NOI18N
        jlblActivo.setPreferredSize(new java.awt.Dimension(3, 4));

        jlblInactivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pare1.png"))); // NOI18N
        jlblInactivo.setPreferredSize(new java.awt.Dimension(3, 4));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 0, 51));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Control de accesos"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblSocios.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSociosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSocios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        tblDependientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDependientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDependientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDependientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDependientes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Dependientes:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel6.add(jLabel5, gridBagConstraints);

        jTabbedPane1.addTab("Socio titular", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Titular:");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSociosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSociosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSociosMouseClicked

    private void btnIdentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdentificarActionPerformed

        ConsultaHuella consultaHuella = new ConsultaHuella(null, true, this);
        consultaHuella.setVisible(true);
        consultaHuella.toFront();
    }//GEN-LAST:event_btnIdentificarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed

        filtros();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        filtros();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDependientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDependientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDependientesMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnIdentificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlblActivo;
    private javax.swing.JLabel jlblInactivo;
    private javax.swing.JTable tblDependientes;
    private javax.swing.JTable tblSocios;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
