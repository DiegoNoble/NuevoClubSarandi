package com.club.views;

import com.Renderers.MyDateCellRenderer;
import com.Renderers.MyDefaultCellRenderer;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
import com.club.Renderers.TableRendererColorSituacion;
import com.club.huellas.BioMini;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FormConsultaMensualidadesXHuella extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    SocioDAO socioDAO;
    Socio socioSeleccionado;
    DefaultTableModel tblModelMensualidades;
    List<Mensualidades> listMensualidades;

    BioMini bioMini;
    DepDAO daoD;
    Dependiente dependienteSeleccionado;

    public FormConsultaMensualidadesXHuella() {

        initComponents();
        defineModelo();
        socioDAO = new SocioDAO();
        daoD = new DepDAO();
    }

    private void defineModelo() {

        ((DefaultTableCellRenderer) tblMensualidades.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModelMensualidades = (DefaultTableModel) tblMensualidades.getModel();
        tblMensualidades.getColumn("Estado").setCellRenderer(new TableRendererColorSituacion(5));

    }

    private void filtros() {

        if (rbCodigo.isSelected()) {
            socioSeleccionado = new Socio();
            socioSeleccionado = (Socio) socioDAO.BuscaPorID(Socio.class, Integer.parseInt(txtFiltro.getText()));
            if (socioSeleccionado == null) {

                Dependiente dep = (Dependiente) daoD.BuscaPorID(Dependiente.class, Integer.parseInt(txtFiltro.getText()));
                if (dep != null) {
                    txtSocio.setText(dep.toString());
                    txtSituacion.setText(dep.getSituacion());
                    socioSeleccionado = dep.getSocio();
                }else{
                    JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                txtSocio.setText(socioSeleccionado.toString());
                txtSituacion.setText(socioSeleccionado.getSituacion());
                txtCategoria.setText(socioSeleccionado.getCategoria().toString());
            }
        } else if (rbCI.isSelected()) {
            socioDAO = new SocioDAO();
            socioSeleccionado = socioDAO.BuscaPorCIIgual(txtFiltro.getText());
            if (socioSeleccionado == null) {
                Dependiente dep = (Dependiente) daoD.BuscaPorCIIgual(txtFiltro.getText());
                if (dep != null) {
                    txtSocio.setText(dep.toString());
                    txtSituacion.setText(dep.getSituacion());
                    socioSeleccionado = dep.getSocio();
                }else{
                    JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                txtSocio.setText(socioSeleccionado.toString());
                txtSituacion.setText(socioSeleccionado.getSituacion());
                txtCategoria.setText(socioSeleccionado.getCategoria().toString());
            }
        }
        muestraMensualidades();
    }

    private void muestraMensualidades() {
        try {

            mensualidadesDAO = new MensualidadesDAO();
            listMensualidades = mensualidadesDAO.BuscaMensualidadesPorSocio(socioSeleccionado);

            tblModelMensualidades.setNumRows(0);
            for (Mensualidades mensualidades : listMensualidades) {
                tblModelMensualidades.addRow(new Object[]{
                    mensualidades.getSocio(),
                    mensualidades.getId(),
                    mensualidades.getFechaVencimiento(),
                    mensualidades.getFechaPago(),
                    mensualidades.getCobrador().getNombre(),
                    mensualidades.getPago(),
                    mensualidades.getValor()
                });
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiaCampos();
        }
    }

    public Socio getSocioSeleccionado() {
        return socioSeleccionado;
    }

    public void setSocioSeleccionado(Socio socioSeleccionado) {
        try {
            this.socioSeleccionado = socioSeleccionado;
            txtSocio.setText(socioSeleccionado.toString());
            txtSituacion.setText(socioSeleccionado.getSituacion());
            txtCategoria.setText(socioSeleccionado.getCategoria().toString());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiaCampos();
        }

        muestraMensualidades();
    }

    public Dependiente getDependienteSeleccionado() {
        return dependienteSeleccionado;
    }

    public void setDependienteSeleccionado(Dependiente dependienteSeleccionado) {
        this.dependienteSeleccionado = dependienteSeleccionado;
        try {
            this.socioSeleccionado = dependienteSeleccionado.getSocio();
            txtSocio.setText(dependienteSeleccionado.toString());
            txtSituacion.setText(dependienteSeleccionado.getSituacion());
            txtCategoria.setText("Socio dependiente!");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            limpiaCampos();
        }

        muestraMensualidades();
    }

    public void limpiaCampos() {
        tblModelMensualidades.setNumRows(0);
        txtCategoria.setText("");
        txtSituacion.setText("");
        txtSocio.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        rbCI = new javax.swing.JRadioButton();
        rbCodigo = new javax.swing.JRadioButton();
        txtFiltro = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnIdentificar = new javax.swing.JButton();
        txtSocio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSituacion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMensualidades = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 650));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel7.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(rbCI);
        rbCI.setSelected(true);
        rbCI.setText("Cédula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 0);
        jPanel7.add(rbCI, gridBagConstraints);

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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtFiltro, gridBagConstraints);

        jLabel3.setText("Categoría:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(btnBuscar, gridBagConstraints);

        btnIdentificar.setText("Buscar Socio por Huella");
        btnIdentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentificarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel7.add(btnIdentificar, gridBagConstraints);

        txtSocio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSocio.setDisabledTextColor(new java.awt.Color(255, 102, 102));
        txtSocio.setEnabled(false);
        txtSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSocioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtSocio, gridBagConstraints);

        jLabel4.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel4, gridBagConstraints);

        txtCategoria.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCategoria.setDisabledTextColor(new java.awt.Color(255, 102, 102));
        txtCategoria.setEnabled(false);
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtCategoria, gridBagConstraints);

        jLabel5.setText("Nombre socio:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel5, gridBagConstraints);

        txtSituacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSituacion.setDisabledTextColor(new java.awt.Color(255, 102, 102));
        txtSituacion.setEnabled(false);
        txtSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSituacionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtSituacion, gridBagConstraints);

        jLabel6.setText("Situación:"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 0);
        jPanel7.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jPanel7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta de Mensualidades"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        tblMensualidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Titular", "Nro. Recibo", "Fecha de Vencimiento", "Fecha de Pago", "Cobrador", "Estado", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        tblMensualidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMensualidadesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMensualidades);
        if (tblMensualidades.getColumnModel().getColumnCount() > 0) {
            tblMensualidades.getColumnModel().getColumn(1).setPreferredWidth(5);
            tblMensualidades.getColumnModel().getColumn(1).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblMensualidades.getColumnModel().getColumn(2).setCellRenderer(new MyDateCellRenderer());
            tblMensualidades.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblMensualidades.getColumnModel().getColumn(3).setCellRenderer(new MyDateCellRenderer());
            tblMensualidades.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblMensualidades.getColumnModel().getColumn(4).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(5).setPreferredWidth(30);
            tblMensualidades.getColumnModel().getColumn(5).setCellRenderer(new MyDefaultCellRenderer());
            tblMensualidades.getColumnModel().getColumn(6).setPreferredWidth(30);
            tblMensualidades.getColumnModel().getColumn(6).setCellRenderer(new MyDefaultCellRenderer());
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel6.add(jScrollPane2, gridBagConstraints);

        jTabbedPane1.addTab("Mensualidades", jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMensualidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMensualidadesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMensualidadesMouseClicked

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

    private void txtSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSocioActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void txtSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSituacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSituacionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnIdentificar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbCI;
    private javax.swing.JRadioButton rbCodigo;
    private javax.swing.JTable tblMensualidades;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtSituacion;
    private javax.swing.JTextField txtSocio;
    // End of variables declaration//GEN-END:variables
}
