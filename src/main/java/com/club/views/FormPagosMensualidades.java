package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.MensualidadesDAO;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FormPagosMensualidades extends javax.swing.JInternalFrame {

    MensualidadesDAO mensualidadesDAO;
    CcCobradorDAO ccCobradorDAO;
    CajaDAO cajaDAO;
    CcCobrador credito;
    Mensualidades reciboAPagar;

    public FormPagosMensualidades() {
        initComponents();

    }

    private void registraPago() {

        try {

            String recibo = txtIdRecibo.getText();

            mensualidadesDAO = new MensualidadesDAO();
            reciboAPagar = (Mensualidades) mensualidadesDAO.BuscaPorID(Mensualidades.class, Integer.parseInt(recibo));
            if (reciboAPagar.getPago().equals("Pago")) {
                JOptionPane.showMessageDialog(null, "Este recibo ya fue registrado como pago anteriormente", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro que desea registrar el pago "
                        + "del recibo nro.: " + recibo + " correspondiente a : " + reciboAPagar.getSocio().getNombre() + "");

                if (confirmacion == JOptionPane.YES_OPTION) {

                    if (rbEfectivoCaja.isSelected()) {

                        reciboAPagar.setFechaPago(new Date());
                        reciboAPagar.setPago("Pago");

                        mensualidadesDAO = new MensualidadesDAO();
                        mensualidadesDAO.Actualizar(reciboAPagar);
                        registraCreditoCuentaCobrador(reciboAPagar);

                        Caja pago = new Caja();

                        pago.setConcepto("Cobro mensualidad socio '" + reciboAPagar.getSocio().getNombre() + "'");
                        pago.setRubro(new Rubro(1));
                        pago.setFechaMovimiento(new Date());
                        pago.setEntrada(reciboAPagar.getValor());
                        pago.setSalida(0.0);
                        pago.setSaldo(buscaSaldoAnterior() + pago.getEntrada());
                        pago.setSectores(new Sectores(1));

                        cajaDAO = new CajaDAO();
                        cajaDAO.Salvar(pago);

                        JOptionPane.showMessageDialog(null, "Pago registrado");

                    }

                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el SQL deseado : " + ex + "");
        }

    }

    private void registraCreditoCuentaCobrador(Mensualidades pago) {
        try {
            credito = new CcCobrador();
            credito.setCobrador(pago.getCobrador());
            credito.setCredito(pago.getValor());
            credito.setDebito(0.0);
            credito.setDescripcion("Recibo Nro: " + pago.getId());
            credito.setFechaMovimiento(new Date());

            ccCobradorDAO = new CcCobradorDAO();
            ccCobradorDAO.Salvar(credito);
        } catch (Exception ex) {
            Logger.getLogger(FormPagosMensualidades.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Double buscaSaldoAnterior() {
        Double saldoAnterior = 0.0;
        cajaDAO = new CajaDAO();
        saldoAnterior = cajaDAO.BuscaSaldoAnterior().getSaldo();
        return saldoAnterior;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIdRecibo = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        rbEfectivoCaja = new javax.swing.JRadioButton();
        btnConfirmaPago = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Recibo Nro.:");

        txtIdRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdReciboActionPerformed(evt);
            }
        });
        txtIdRecibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdReciboKeyReleased(evt);
            }
        });

        buttonGroup1.add(rbEfectivoCaja);
        rbEfectivoCaja.setText("Efectivo en caja");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbEfectivoCaja)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbEfectivoCaja)
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("Forma de cobro", jPanel3);

        btnConfirmaPago.setText("Confirmar");
        btnConfirmaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmaPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfirmaPago)
                            .addComponent(txtIdRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirmaPago)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pagos"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarPagoActionPerformed
    }//GEN-LAST:event_btnConfirmarPagoActionPerformed

    private void btnConfirmaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmaPagoActionPerformed

        registraPago();

    }//GEN-LAST:event_btnConfirmaPagoActionPerformed

    private void txtIdReciboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdReciboKeyReleased

        txtIdRecibo.setText(txtIdRecibo.getText().replaceAll("[^0-9]", ""));

    }//GEN-LAST:event_txtIdReciboKeyReleased

    private void txtIdReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdReciboActionPerformed

        registraPago();

    }//GEN-LAST:event_txtIdReciboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmaPago;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbEfectivoCaja;
    private javax.swing.JTextField txtIdRecibo;
    // End of variables declaration//GEN-END:variables
}
