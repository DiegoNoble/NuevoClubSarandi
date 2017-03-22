/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import com.club.BEANS.Caja;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.CuotaCampEconomica;
import com.club.BEANS.Parametros;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import com.club.DAOs.CajaDAO;
import com.club.DAOs.CuotaCampEconomicaDAO;
import com.club.DAOs.ParametrosDAO;
import com.club.DAOs.RubroDAO;
import com.club.DAOs.SectorDAO;
import java.awt.Dialog;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Diego
 */
public class RegistrarPagosCuotasCampEco extends javax.swing.JDialog {

    /**
     * Creates new form RegistraPago
     */
    CuotaCampEconomicaDAO cuotaCampEcoDAO;
    CajaDAO cajaDAO;
    CcCobrador credito;
    SectorDAO sectorDAO;
    CuotaCampEconomica cuotaCampEconomica;
    Parametros parametros;

    public RegistrarPagosCuotasCampEco(Dialog owner, boolean modal, CuotaCampEconomica cuotaCampEconomica) {
        super(owner, modal);
        initComponents();
        this.cuotaCampEconomica = cuotaCampEconomica;
        actualizaComboBox();
        setLocationRelativeTo(null);
        ParametrosDAO parametrosDAO = new ParametrosDAO();
        parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
    }

    public void actualizaComboBox() {

        try {
            AutoCompleteDecorator.decorate(cbSectores);
            sectorDAO = new SectorDAO();
            List<Sectores> listSectores = sectorDAO.BuscaTodos(Sectores.class);
            for (Sectores sector : listSectores) {
                cbSectores.addItem(sector);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar comboboxes: " + ex);
            ex.printStackTrace();
        }
    }

    private void registraPago() {

        try {

            cuotaCampEcoDAO = new CuotaCampEconomicaDAO();
            if (cuotaCampEconomica.getPago() == Boolean.TRUE) {
                JOptionPane.showMessageDialog(null, "Esta cuota ya fue registrada como pago anteriormente", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                int confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro que desea registrar el pago "
                        + "de la cuota nro.: " + cuotaCampEconomica.getNroCuota() + " correspondiente a : " + cuotaCampEconomica.getVentaCampEco().getSocio().toString() + "");

                if (confirmacion == JOptionPane.YES_OPTION) {

                    if (rbEfectivoCaja.isSelected()) {

                        cuotaCampEconomica.setFechaPago(new Date());
                        cuotaCampEconomica.setPago(Boolean.TRUE);

                        cuotaCampEcoDAO = new CuotaCampEconomicaDAO();
                        cuotaCampEcoDAO.Actualizar(cuotaCampEconomica);

                        Caja pago = new Caja();

                        pago.setConcepto("Cobro cuota " + cuotaCampEconomica.getNroCuota() + " Campaña Económica, socio '" + cuotaCampEconomica.getVentaCampEco().getSocio().toString() + "'");
                        pago.setRubro(parametros.getRubroPagoCuotasCampEco());
                        pago.setFechaMovimiento(new Date());
                        pago.setEntrada(cuotaCampEconomica.getValor());
                        pago.setSalida(0.0);
                        pago.setSaldo(buscaSaldoAnterior() + pago.getEntrada());
                        pago.setSectores((Sectores) cbSectores.getSelectedItem());

                        cajaDAO = new CajaDAO();
                        cajaDAO.Salvar(pago);

                        JOptionPane.showMessageDialog(null, "Pago registrado");

                    }

                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar el SQL deseado : " + ex + "");
            ex.printStackTrace();
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        rbEfectivoCaja = new javax.swing.JRadioButton();
        btnConfirmaPago = new javax.swing.JButton();
        cbSectores = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 350));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        rbEfectivoCaja.setSelected(true);
        rbEfectivoCaja.setText("Efectivo en caja");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbEfectivoCaja)
                .addContainerGap(343, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbEfectivoCaja)
                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("Forma de cobro", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(jTabbedPane1, gridBagConstraints);

        btnConfirmaPago.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnConfirmaPago.setText("Confirmar");
        btnConfirmaPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmaPagoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(btnConfirmaPago, gridBagConstraints);

        cbSectores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSectoresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(cbSectores, gridBagConstraints);

        jLabel5.setText("Sector");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 400));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pagos camp. eco."); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmaPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmaPagoActionPerformed

        registraPago();
        this.dispose();
    }//GEN-LAST:event_btnConfirmaPagoActionPerformed

    private void cbSectoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSectoresActionPerformed

    }//GEN-LAST:event_cbSectoresActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmaPago;
    private javax.swing.JComboBox cbSectores;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbEfectivoCaja;
    // End of variables declaration//GEN-END:variables
}
