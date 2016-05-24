/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import com.club.BEANS.Dependiente;
import com.club.BEANS.Socio;
import com.club.DAOs.DepDAO;
import com.club.DAOs.SocioDAO;
import com.club.huellas.BioMini;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class ConsultaSocioxHuella extends javax.swing.JInternalFrame {

    BioMini bioMini;
    SocioDAO daoS;
    DepDAO daoD;

    /**
     * Creates new form ConsultaSocioxHuella
     */
    public ConsultaSocioxHuella() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagePanel1 = new imagepanel.ImagePanel();
        btnIdentificar = new javax.swing.JButton();

        setClosable(true);

        imagePanel1.setBackground(new java.awt.Color(255, 102, 102));
        imagePanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        imagePanel1.setForeground(new java.awt.Color(102, 255, 51));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );

        btnIdentificar.setText("Buscar Socio");
        btnIdentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdentificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(btnIdentificar)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIdentificar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIdentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdentificarActionPerformed

        bioMini = new BioMini();
        daoS = new SocioDAO();
        bioMini.iniciar();
        Socio socio = (bioMini.BuscarHuellaSocioTitular(imagePanel1, daoS.BuscaSociosxHuella()));
        daoD = new DepDAO();
        Dependiente dep = (bioMini.BuscarHuellaDep(imagePanel1, daoD.BuscaDepXHuella()));
        if (socio != null) {
            JOptionPane.showMessageDialog(null, "Socio Titular: "+socio.getId() + " " + socio.getNombre());
        } else if (dep != null) {
            JOptionPane.showMessageDialog(null, "Socio Dependiente: "+dep.getId() + " " + dep.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnIdentificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIdentificar;
    private imagepanel.ImagePanel imagePanel1;
    // End of variables declaration//GEN-END:variables
}
