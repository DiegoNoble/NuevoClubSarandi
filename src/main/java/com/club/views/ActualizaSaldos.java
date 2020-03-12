/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import com.club.BEANS.Caja;
import com.club.DAOs.CajaDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JOptionPane;

public class ActualizaSaldos extends javax.swing.JDialog {

    public ActualizaSaldos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        new Thread() {
            @Override
            public void run() {
                actualiza();

            }
        }.start();
    }

    public void actualiza() {
        try {

            CajaDAO cajaDAO = new CajaDAO();
            List<Caja> BuscaTodosOrdenaPorID = cajaDAO.BuscaTodosOrdenaPorID();
            Double saldo = 0.0;
            for (Caja mov : BuscaTodosOrdenaPorID) {
                
                saldo = saldo + (mov.getEntrada() - mov.getSalida());
                mov.setSaldo(new BigDecimal(saldo).setScale(2,RoundingMode.CEILING).doubleValue());
                cajaDAO = new CajaDAO();

                cajaDAO.Actualizar(mov);
                log.append("\n Fecha: "+mov.getFechaMovimiento() + " Saldo: " + mov.getSaldo());
                log.setCaretPosition(this.log.getDocument().getLength());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        log.setColumns(20);
        log.setRows(5);
        jScrollPane1.setViewportView(log);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea log;
    // End of variables declaration//GEN-END:variables
}
