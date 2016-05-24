package com.club.control.categoria;

import com.club.control.acceso.conexion;
import com.club.control.utilidades.data;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class CategoriaFrame extends javax.swing.JInternalFrame {

    conexion conexionCategoria;
    MaskFormatter formatoftxtFechaIngreso;
    data muestraData;

    public CategoriaFrame() {
        initComponents();
        btnEliminar.setVisible(false);
        
        conexionCategoria = new conexion();
        conexionCategoria.conecta();
        conexionCategoria.ejecutaSQL("select * from tbcategoria");
        muestraContenidoTabla();

    }

    private void muestraContenidoTabla() {
        // modelo de la jtablCobrador, tamaño de la columnas y contenido de las columnas
        conexionCategoria.ejecutaSQL("select * from tbcategoria where definicion like'" + txtFiltroNombre.getText() + "%'");
        DefaultTableModel modelo = (DefaultTableModel) tblCategoria.getModel();
        modelo.setNumRows(0);

        try {
            while (conexionCategoria.resultSet.next()) {
                Object[] linha = new Object[5];
                linha[0] = conexionCategoria.resultSet.getString("id");
                linha[1] = conexionCategoria.resultSet.getString("definicion");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date data = conexionCategoria.resultSet.getDate("fechacriacion");
                linha[2] = formato.format(data);
                linha[3] = conexionCategoria.resultSet.getString("descripcion");
                linha[4] = conexionCategoria.resultSet.getString("mensualidad");
                modelo.addRow(linha);

            }
        } catch (SQLException error) {

            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + error);
        }

    }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        ftxtFechaCreacion.setEditable(true);
        txtAreaDescripcion.setEditable(true);
        tblCategoria.setVisible(false); //desabilita el click del mouse sobre la jtable
        tblCategoria.setEnabled(false);
        txtMensualidad.setEditable(true);
        txtAreaDescripcion.setEditable(true);
       
        

    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        ftxtFechaCreacion.setEditable(false);
        txtAreaDescripcion.setEditable(false);
        tblCategoria.setEnabled(true);
        tblCategoria.setVisible(true);
        txtAreaDescripcion.setEditable(true);
        txtMensualidad.setEditable(true);
     
        

    }

    private void desabilitaBotones() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnAlterar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void habilitaBotones() {

        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void limpaCampos() {
        jlCodigoCategoria.setText("");
        txtNombre.setText("");
        txtAreaDescripcion.setText("");
        ftxtFechaCreacion.setText("");
        txtAreaDescripcion.setText("");
        txtMensualidad.setText("");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFiltroNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        try{
            formatoftxtFechaIngreso = new MaskFormatter("##/##/####");
        }catch (Exception error){
            JOptionPane.showMessageDialog(null, "No fue posible crear la mascara, error ="+error);
        }
        ftxtFechaCreacion = new JFormattedTextField(formatoftxtFechaIngreso);
        jlCodigoCategoria = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAreaDescripcion = new java.awt.TextArea();
        jLabel6 = new javax.swing.JLabel();
        txtMensualidad = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Categorías"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("Filtro por Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtFiltroNombre, gridBagConstraints);

        tblCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de Categoría", "Nombre", "Fecha de Creación", "Descripción", "Mensualidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCategoria.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCategoria);
        tblCategoria.getColumnModel().getColumn(3).setMinWidth(0);
        tblCategoria.getColumnModel().getColumn(3).setPreferredWidth(0);
        tblCategoria.getColumnModel().getColumn(3).setMaxWidth(0);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        btnBuscar.setText("Buscar"); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Código de Categoría"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        txtNombre.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtNombre, gridBagConstraints);

        jLabel14.setText("Fecha de Creación"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel14, gridBagConstraints);

        ftxtFechaCreacion.setEditable(false);
        ftxtFechaCreacion.setFont(new java.awt.Font("Tahoma", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ftxtFechaCreacion, gridBagConstraints);

        jlCodigoCategoria.setFont(new java.awt.Font("Times New Roman", 1, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jlCodigoCategoria, gridBagConstraints);

        jLabel5.setText("Descripción"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel5, gridBagConstraints);

        txtAreaDescripcion.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtAreaDescripcion, gridBagConstraints);

        jLabel6.setText("Mensualidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel6, gridBagConstraints);

        txtMensualidad.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtMensualidad, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnNuevo.setText("Nuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo);

        btnAlterar.setText("Editar"); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnEliminar.setText("Eliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminar);

        btnSalvar.setText("Salvar"); // NOI18N
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

        muestraContenidoTabla();

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        habilitaCampos();
        limpaCampos();
        habilitaBotones();
        txtNombre.requestFocus();

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlCodigoCategoria.getText().equals("")) {
            
            try {
                String sqlinsert = "insert into tbcategoria (definicion, fechacriacion, descripcion, mensualidad) values ('"
                        + txtNombre.getText() + "','"
                        + data.getDataTelaToBD(ftxtFechaCreacion.getText()) + "','"
                        + txtAreaDescripcion.getText() + "','"
                        + txtMensualidad.getText() + "')" ;

                conexionCategoria.statement.executeUpdate(sqlinsert);

                JOptionPane.showMessageDialog(null, "Categoria registrada correctamente!");



            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado " + error);
            }

        } else {

            try {
                String sqlUpdate = " update tbcategoria set definicion = '" + txtNombre.getText() + "',"
                        +" fechacriacion = '"+ data.getDataTelaToBD(ftxtFechaCreacion.getText()) + "',"
                        + " descripcion = '" + txtAreaDescripcion.getText() + "',"
                        + " mensualidad = '" + txtMensualidad.getText() + "'"
                        + " where id = '" + jlCodigoCategoria.getText() + "'";


                conexionCategoria.statement.executeUpdate(sqlUpdate);

                JOptionPane.showMessageDialog(null, "Categoria modificada correctamente!");

            } catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado " + error);
            }

        }

        muestraContenidoTabla();
        desabilitaCampos();
        desabilitaBotones();

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        desabilitaBotones();
        desabilitaCampos();
       

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (tblCategoria.getSelectedRow() != -1) {
            habilitaBotones();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una Categoria en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        try {
            if (tblCategoria.getSelectedRow() != -1) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro que desea excluir la Categoría?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {

                    String sql = "delete from tbcategoria where id = " + jlCodigoCategoria.getText();
                    int excluir = conexionCategoria.statement.executeUpdate(sql);
                    if (excluir == 1) {
                        JOptionPane.showMessageDialog(this, "Exclución bien sucedida!");
                        conexionCategoria.ejecutaSQL("Select * from tbcategoria");
                        conexionCategoria.resultSet.first();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una Categoría en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "No fue posible ejecutar el SQL deseado" + error);
        }
        muestraContenidoTabla();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriaMouseClicked

       limpaCampos();
       
        try {

            int filaSeleccionada = tblCategoria.getSelectedRow();
            jlCodigoCategoria.setText(tblCategoria.getValueAt(filaSeleccionada, 0).toString());
            txtNombre.setText(tblCategoria.getValueAt(filaSeleccionada, 1).toString());
            ftxtFechaCreacion.setValue(tblCategoria.getValueAt(filaSeleccionada, 2).toString());
            txtAreaDescripcion.setText(tblCategoria.getValueAt(filaSeleccionada, 3).toString());
            txtMensualidad.setText(tblCategoria.getValueAt(filaSeleccionada, 4).toString());

        } catch (Exception error) {
        }

    }//GEN-LAST:event_tblCategoriaMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JFormattedTextField ftxtFechaCreacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlCodigoCategoria;
    private javax.swing.JTable tblCategoria;
    private java.awt.TextArea txtAreaDescripcion;
    private javax.swing.JTextField txtFiltroNombre;
    private javax.swing.JTextField txtMensualidad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
