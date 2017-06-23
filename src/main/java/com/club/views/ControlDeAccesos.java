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
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ControlDeAccesos extends javax.swing.JInternalFrame {

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

    public ControlDeAccesos() {

        initComponents();
        status.setVisible(false);
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
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                jlblActivo.setVisible(false);
                                jlblInactivo.setVisible(false);
                            }
                        }).start();

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

        }
        );

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

        status.setVisible(false);
        status.setBusy(false);
        txtFiltro.setEnabled(true);
        tblSocios.setEnabled(true);
        tblDependientes.setEnabled(true);
        btnBuscar.setEnabled(true);
        btnIdentificar.setEnabled(true);

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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtFiltro = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnIdentificar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        status = new org.jdesktop.swingx.JXBusyLabel();
        jPanel3 = new javax.swing.JPanel();
        jlblActivo = new javax.swing.JLabel();
        jlblInactivo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSocios = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDependientes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

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

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel7.setLayout(new java.awt.GridBagLayout());

        txtFiltro.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtFiltro.setToolTipText("Digite Cod. Socio o Nombre o C.I.");
        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFiltro, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnIdentificar, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Buscar por:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel4, gridBagConstraints);

        status.setText("Aguarde un momento...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel7.add(status, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel7, gridBagConstraints);

        jlblActivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pulgar_arriba.png"))); // NOI18N
        jlblActivo.setPreferredSize(new java.awt.Dimension(3, 4));

        jlblInactivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pare1.png"))); // NOI18N
        jlblInactivo.setPreferredSize(new java.awt.Dimension(3, 4));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jlblActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jlblInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlblActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlblInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblSocios.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblSocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSocios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSociosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSocios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2.0;
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Dependientes:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel6.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Titulares"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel6.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSociosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSociosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSociosMouseClicked

    private void tblDependientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDependientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDependientesMouseClicked

    private void btnIdentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdentificarActionPerformed

        ConsultaHuella consultaHuella = new ConsultaHuella(null, true, this);
        consultaHuella.setVisible(true);
        consultaHuella.toFront();
    }//GEN-LAST:event_btnIdentificarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {

                status.setVisible(true);
                status.setBusy(true);
                txtFiltro.setEnabled(false);
                tblSocios.setEnabled(false);
                tblDependientes.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnIdentificar.setEnabled(false);
                filtros();

            }
        }).start();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {

                status.setVisible(true);
                status.setBusy(true);
                txtFiltro.setEnabled(false);
                tblSocios.setEnabled(false);
                tblDependientes.setEnabled(false);
                btnBuscar.setEnabled(false);
                btnIdentificar.setEnabled(false);
                filtros();

            }
        }).start();
    }//GEN-LAST:event_txtFiltroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnIdentificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlblActivo;
    private javax.swing.JLabel jlblInactivo;
    private org.jdesktop.swingx.JXBusyLabel status;
    private javax.swing.JTable tblDependientes;
    private javax.swing.JTable tblSocios;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
