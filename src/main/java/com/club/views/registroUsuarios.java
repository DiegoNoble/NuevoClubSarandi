package com.club.views;

import com.club.BEANS.Usuario;
import com.club.DAOs.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class registroUsuarios extends javax.swing.JInternalFrame {

    MaskFormatter formatoftxtFechaIngreso;
    List<Usuario> listUsuarios;
    UsuarioDAO usuarioDAO;
    DefaultTableModel tblModel;
    ListSelectionModel listModel;
    Usuario usuarioSeleccionado;

    public registroUsuarios() {
        initComponents();
        DefineModeloTbl();
        buscaTodosLosRegistros();
        muestraContenidoTbl();
        deshabilitaCampos();
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void buscaTodosLosRegistros() {
        listUsuarios = new ArrayList();
        usuarioDAO = new UsuarioDAO();
        listUsuarios.addAll(usuarioDAO.BuscaTodos(Usuario.class));
    }

    private void DefineModeloTbl() {

        ((DefaultTableCellRenderer) tblUsuarios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        tblModel = (DefaultTableModel) tblUsuarios.getModel();

        listModel = tblUsuarios.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    if (tblUsuarios.getSelectedRow() != -1) {

                        usuarioSeleccionado = listUsuarios.get(tblUsuarios.getSelectedRow());
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

    private void muestraContenidoTbl() {

        try {

            tblModel.setNumRows(0);

            for (Usuario usuario : listUsuarios) {
                tblModel.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getPerfil()});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No fue posible cargar los registros" + e);
            e.printStackTrace();
        }
    }

    private void muestraDetalles() {
        limpiaCampos();

        if (tblUsuarios.getSelectedRow() != -1) {

            jlbCodigo.setText(usuarioSeleccionado.getId().toString());
            txtNombre.setText(usuarioSeleccionado.getNombre().toString());
            ptxtPass.setText(usuarioSeleccionado.getPass().toString());
            cbPerfil.setSelectedItem(usuarioSeleccionado.getPerfil().toString());

        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        ptxtPass.setEditable(true);
        tblUsuarios.setEnabled(false);
        tblUsuarios.setVisible(false);
    }

    private void deshabilitaCampos() {
        txtNombre.setEditable(false);
        ptxtPass.setEditable(false);
        tblUsuarios.setEnabled(true);
        tblUsuarios.setVisible(true);
    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void habilitaBotones() {

        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void limpiaCampos() {
        txtNombre.setText("");
        ptxtPass.setText("");

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        ptxtPass = new javax.swing.JPasswordField();
        cbPerfil = new javax.swing.JComboBox();
        jlbCodigo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 400));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuarios"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Perfil"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Usuario"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Perfil"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel4, gridBagConstraints);

        jLabel14.setText("Pass"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel14, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtNombre, gridBagConstraints);

        ptxtPass.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(ptxtPass, gridBagConstraints);

        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Usuario", "Operador", "Gerencia", "Administrador", "Portero" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbPerfil, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jlbCodigo, gridBagConstraints);

        jTabbedPane1.addTab("Datos", jPanel5);

        jPanel3.add(jTabbedPane1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnNuevo.setText("Nuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel4.add(btnNuevo, gridBagConstraints);

        btnEditar.setText("Editar"); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel4.add(btnEditar, gridBagConstraints);

        btnSalvar.setText("Salvar"); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel4.add(btnSalvar, gridBagConstraints);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel4.add(btnCancelar, gridBagConstraints);

        btnEliminar.setText("Eliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel4.add(btnEliminar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        usuarioSeleccionado = new Usuario();
        limpiaCampos();
        habilitaCampos();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        try {
            //usuarioDAO = new UsuarioDAO();
            if (usuarioSeleccionado.getId() != null) {

                usuarioSeleccionado.setNombre(txtNombre.getText());
                usuarioSeleccionado.setPass(ptxtPass.getText());
                usuarioSeleccionado.setPerfil(cbPerfil.getSelectedItem().toString());
                usuarioDAO.Actualizar(usuarioSeleccionado);
                JOptionPane.showMessageDialog(null, "Rubro actuaizado correctamente");
            } else {
                usuarioSeleccionado.setNombre(txtNombre.getText());
                usuarioSeleccionado.setPass(ptxtPass.getText());
                usuarioSeleccionado.setPerfil(cbPerfil.getSelectedItem().toString());
                usuarioDAO.Salvar(usuarioSeleccionado);
                JOptionPane.showMessageDialog(null, "Rubro guardado correctamente");
            }
            deshabilitaCampos();
            buscaTodosLosRegistros();
            muestraContenidoTbl();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos" + ex);
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        limpiaCampos();
        deshabilitaCampos();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        habilitaCampos();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {

            usuarioDAO.EliminarPorId(Usuario.class, usuarioSeleccionado.getId());
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            deshabilitaCampos();
            buscaTodosLosRegistros();
            muestraContenidoTbl();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el usuario, posee transacciones vinculadas" + ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked


    }//GEN-LAST:event_tblUsuariosMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JPasswordField ptxtPass;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
