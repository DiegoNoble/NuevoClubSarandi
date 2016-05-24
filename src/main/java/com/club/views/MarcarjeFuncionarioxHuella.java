/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import com.club.BEANS.Marcas;
import com.club.BEANS.Funcionario;
import com.club.BEANS.TipoMarca;
import com.club.DAOs.FuncionarioDAO;
import com.club.huellas.BioMini;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego Noble
 */
public class MarcarjeFuncionarioxHuella extends javax.swing.JInternalFrame {

    BioMini bioMini;
    FuncionarioDAO dao;

    /**
     * Creates new form ConsultaFuncionarioxHuella
     */
    public MarcarjeFuncionarioxHuella() {
        initComponents();
        //btnEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/club/Imagenes/login.png")));
        //btnSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/club/Imagenes/logout.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagePanel1 = new imagepanel.ImagePanel();
        btnEntrada = new javax.swing.JButton();
        btnSalida = new javax.swing.JButton();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(400, 545));

        imagePanel1.setBackground(new java.awt.Color(255, 102, 102));
        imagePanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        imagePanel1.setForeground(new java.awt.Color(102, 255, 51));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

        btnEntrada.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/club/imagenes/login.png"))); // NOI18N
        btnEntrada.setText("ENTRADA");
        btnEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrada.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEntrada.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEntrada.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });

        btnSalida.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/club/imagenes/logout.png"))); // NOI18N
        btnSalida.setText("SALIDA");
        btnSalida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalida.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnSalida.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnEntrada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalida))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(341, 341, 341))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalida))
                .addGap(144, 144, 144))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed

        bioMini = new BioMini();
        dao = new FuncionarioDAO();
        bioMini.iniciar();
        Funcionario funcionario = (bioMini.BuscarHuellaFuncionario(imagePanel1, dao.BuscaFuncionariosxHuella()));
        if (funcionario != null) {
            try {
                Marcas entrada = new Marcas(new Date(), new Date(), funcionario, TipoMarca.ENTRADA);
                dao = new FuncionarioDAO();
                dao.Salvar(entrada);
                JOptionPane.showMessageDialog(null, "Marca ENTRADA - Funcionario Titular: " + funcionario.getId() + " " + funcionario.getNombre());
            } catch (Exception ex) {
                Logger.getLogger(MarcarjeFuncionarioxHuella.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Funcionario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalidaActionPerformed
        bioMini = new BioMini();
        dao = new FuncionarioDAO();
        bioMini.iniciar();
        Funcionario funcionario = (bioMini.BuscarHuellaFuncionario(imagePanel1, dao.BuscaFuncionariosxHuella()));
        if (funcionario != null) {
            try {
                Marcas salida = new Marcas(new Date(), new Date(), funcionario, TipoMarca.SALIDA);
                dao = new FuncionarioDAO();
                dao.Salvar(salida);
                JOptionPane.showMessageDialog(null, "Marca SALIDA - Funcionario : " + funcionario.getId() + " " + funcionario.getNombre());
            } catch (Exception ex) {
                Logger.getLogger(MarcarjeFuncionarioxHuella.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Funcionario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSalidaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnSalida;
    private imagepanel.ImagePanel imagePanel1;
    // End of variables declaration//GEN-END:variables
}
