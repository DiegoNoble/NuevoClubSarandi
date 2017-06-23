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

public final class ConsultaHuella extends javax.swing.JDialog {

    BioMini bioMini;
    SocioDAO daoS;
    DepDAO daoD;
    ControlDeAccesos form;

    public ConsultaHuella(java.awt.Frame parent, boolean modal, ControlDeAccesos form) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.form = form;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                identificar();
                dispose();
            }
        });
        //identificar();
    }

    void identificar() {
        bioMini = new BioMini();
        daoS = new SocioDAO();
        bioMini.iniciar();
        Socio socio = (bioMini.BuscarHuellaSocioTitular(imagePanel1, daoS.BuscaSociosxHuella()));
        if (socio != null) {
            form.setSocioSeleccionado(socio);
        } else {
            daoD = new DepDAO();
            Dependiente dep = (bioMini.BuscarHuellaDep(imagePanel1, daoD.BuscaDepXHuella()));
            if (dep != null) {
                form.setDependienteSeleccionado(dep);
            } else {
                JOptionPane.showMessageDialog(null, "Socio no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                form.limpiaCampos();
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagePanel1 = new imagepanel.ImagePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(71, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private imagepanel.ImagePanel imagePanel1;
    // End of variables declaration//GEN-END:variables
}
