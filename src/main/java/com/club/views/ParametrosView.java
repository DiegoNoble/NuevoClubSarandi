package com.club.views;

import com.club.BEANS.Cobrador;
import com.club.BEANS.Parametros;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.ParametrosDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParametrosView extends javax.swing.JInternalFrame {

    private ParametrosDAO parametrosDAO;
    private Parametros parametros;

    public ParametrosView() {
        initComponents();
        parametrosDAO = new ParametrosDAO();
        parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
        inicio();

    }

    private void inicio() {
        comboCobradores();
        txtEmail.setText(parametros.getCasilla_email());
        txtPswEmail.setText(parametros.getPsw_email());
        txtPswSms.setText(parametros.getPsw_SMS());
        txtTokenCobrosYa.setText(parametros.getTokenCobrosYa());
        txtUrlPost.setText(parametros.getUrlPostCobrosYa());
        txtUsuarioSMS.setText(parametros.getUsuario_SMS());
        cbCobrosYa.setSelectedItem(parametros.getCobradorCobrosYa());
        txtApiUrlCrear.setText(parametros.getApiUrlCrear());
    }

    void comboCobradores() {
        cbCobrosYa.removeAllItems();
        CobradorDAO cobradorDAO = new CobradorDAO();
        List<Cobrador> cobradores = cobradorDAO.BuscaTodos(Cobrador.class);
        for (Cobrador cobrador : cobradores) {
            cbCobrosYa.addItem(cobrador);
        }
    }

    private void habilitaCampos() {

        txtEmail.setEnabled(true);
        txtPswEmail.setEnabled(true);
        txtPswSms.setEnabled(true);
        txtTokenCobrosYa.setEnabled(true);
        txtUrlPost.setEnabled(true);
        txtApiUrlCrear.setEnabled(true);
        txtUsuarioSMS.setEnabled(true);
        cbCobrosYa.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEditar.setEnabled(false);
        

    }

    private void deshabilitaCampos() {
        txtEmail.setEnabled(false);
        txtPswEmail.setEnabled(false);
        txtPswSms.setEnabled(false);
        txtTokenCobrosYa.setEnabled(false);
        txtUrlPost.setEnabled(false);
        txtApiUrlCrear.setEnabled(false);
        txtUsuarioSMS.setEnabled(false);
        cbCobrosYa.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtTokenCobrosYa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuarioSMS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUrlPost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPswEmail = new javax.swing.JTextField();
        txtPswSms = new javax.swing.JTextField();
        cbCobrosYa = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtApiUrlCrear = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Control de Socios - Club Sarandi Universitario"); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 350));
        setRequestFocusEnabled(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Parametros"); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        txtTokenCobrosYa.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTokenCobrosYa, gridBagConstraints);

        jLabel2.setText("Casilla email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtUsuarioSMS.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUsuarioSMS, gridBagConstraints);

        jLabel5.setText("Usuario SMS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtUrlPost.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtUrlPost, gridBagConstraints);

        jLabel7.setText("Token CobrosYa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel9.setText("API URL CobrosYA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtEmail.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmail, gridBagConstraints);

        jLabel10.setText("Psw Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setText("Psw SMS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel11, gridBagConstraints);

        txtPswEmail.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPswEmail, gridBagConstraints);

        txtPswSms.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPswSms, gridBagConstraints);

        cbCobrosYa.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbCobrosYa, gridBagConstraints);

        jLabel12.setText("Cobrador CobrosYa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel12, gridBagConstraints);

        jLabel13.setText("URL Post CobrosYA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel13, gridBagConstraints);

        txtApiUrlCrear.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtApiUrlCrear, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnEditar, gridBagConstraints);

        btnGuardar.setText("Salvar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(btnGuardar, new java.awt.GridBagConstraints());

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel6.add(btnCancelar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel6, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jasperRunnerButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FichaMedicaActionPerformed


    }//GEN-LAST:event_FichaMedicaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        habilitaCampos();

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {
            deshabilitaCampos();

            parametros.setCasilla_email(txtEmail.getText());

            parametros.setPsw_SMS(txtPswSms.getText());
            parametros.setPsw_email(txtPswEmail.getText());
            parametros.setTokenCobrosYa(txtTokenCobrosYa.getText());
            parametros.setUrlPostCobrosYa(txtUrlPost.getText());
            parametros.setUsuario_SMS(txtUsuarioSMS.getText());
            parametros.setApiUrlCrear(txtApiUrlCrear.getText());
            parametros.setCobradorCobrosYa((Cobrador) cbCobrosYa.getSelectedItem());
            parametrosDAO = new ParametrosDAO();
            parametrosDAO.Actualizar(parametros);
        } catch (Exception ex) {
            Logger.getLogger(ParametrosView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed


    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cbCobrosYa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField txtApiUrlCrear;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPswEmail;
    private javax.swing.JTextField txtPswSms;
    private javax.swing.JTextField txtTokenCobrosYa;
    private javax.swing.JTextField txtUrlPost;
    private javax.swing.JTextField txtUsuarioSMS;
    // End of variables declaration//GEN-END:variables
}
