/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import Utilidades.ThreadMensualidadesCobrosYa;
import com.club.BEANS.Mensualidades;
import java.util.List;

/**
 *
 * @author dnoble
 */
public class LogMensualidadesCobrosYa extends javax.swing.JDialog {

    /**
     * Creates new form LogMensualidadesCobrosYa
     */
    public LogMensualidadesCobrosYa(java.awt.Frame parent, boolean modal, List<Mensualidades> talonesPendientes) {
        super(parent, modal);
        initComponents();
        setSize(500, 900);
        ThreadMensualidadesCobrosYa thread = new ThreadMensualidadesCobrosYa(txtLog, talonesPendientes);
        thread.execute();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        txtLog.setColumns(20);
        txtLog.setRows(5);
        txtLog.setPreferredSize(new java.awt.Dimension(500, 600));
        jScrollPane2.setViewportView(txtLog);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
}
