/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.views;

import Utilidades.EnviarEmail;
import Utilidades.EnvioTalonCobrosYa;
import Utilidades.ValidaCelular;
import Utilidades.ValidaEmail;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Parametros;
import com.club.BEANS.Socio;
import com.club.DAOs.MensualidadesDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author dnoble
 */
public class LogMensualidadesCobrosYa extends javax.swing.JDialog {

    List<Mensualidades> listMensualidades;
    Cobrador cobradorCobrosYa;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Parametros parametros;
    MensualidadesDAO mensualidadesDAO;

    public LogMensualidadesCobrosYa(java.awt.Frame parent, boolean modal, Cobrador cobradorCobrosYa, Parametros parametros, List<Mensualidades> listMensualidades) {
        super(parent, modal);
        this.cobradorCobrosYa = cobradorCobrosYa;
        this.parametros = parametros;
        this.listMensualidades = listMensualidades;
        initComponents();

        setSize(900, 500);

        DefaultCaret caret = (DefaultCaret) txtLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        new Thread() {
            @Override
            public void run() {
                prueba();

            }
        }.start();

    }

    void prueba() {

        this.txtLog.append("Analizando mensualidades....\n");

        //mensualidadesDAO = new MensualidadesDAO();
        //talonesPendientes = mensualidadesDAO.BuscaPorCobradorSituacion(cobradorCobrosYa, "Pendiente de Pago");
        for (Mensualidades m : listMensualidades) {
            if (m.getEnviado() == true) {
                this.txtLog.append("\nSocio " + m.getSocio());
                this.txtLog.append("\nRecibo " + m.getId() + ", ya enviado, nro Talón CobrosYa " + m.getNroTalonCobrosYa());
                this.txtLog.append("\n--------------------------");
            } else if (m.getFechaVencimiento().before(new Date())) {
                this.txtLog.append("\nSocio " + m.getSocio());
                this.txtLog.append("\nRecibo " + m.getId() + ", Fecha de vencimiento inválida, debe ser mayor que hoy.");
                this.txtLog.append("\n--------------------------");
            } else if (m.getPago().equals("Pago")) {
                this.txtLog.append("\nSocio " + m.getSocio());
                this.txtLog.append("\nRecibo " + m.getId() + ", Nro Talón CobrosYa " + m.getNroTalonCobrosYa() + "Pago " + formato.format(m.getFechaHoraTransaccionCobrosYa()));
                this.txtLog.append("\n--------------------------");
            }  else if (ValidaCelular.validateEmail(m.getSocio().getCelular()) == false) {
                this.txtLog.append("\n Formato de celular inválido");
            } else {
                enviarTalon(m.getSocio(), m);
            }
        }

        this.txtLog.append("\n");
        this.txtLog.append("\n Listo!");
        this.txtLog.setCaretPosition(this.txtLog.getDocument().getLength());
    }

    void enviarTalon(Socio socio, Mensualidades mensualidadSeleccionada) {
        try {
            String email = socio.getEmail();
            if (ValidaEmail.validateEmail(socio.getEmail()) == false) {
                this.txtLog.append("\n Formato de email inválido, se utilizara la casilla Default: " + parametros.getEmailPadron());
                email = parametros.getEmailPadron();
            }

            this.txtLog.append("\n");
            this.txtLog.append("Socio: " + socio + ", Cuota social Nro.: " + mensualidadSeleccionada.getId() + ", Vencimiento " + formato.format(mensualidadSeleccionada.getFechaVencimiento()));
            this.txtLog.append("\n Generando talón CobrosYa...");

            EnvioTalonCobrosYa cobrosYa = new EnvioTalonCobrosYa(parametros);
            String retorno = cobrosYa.enviarTalonMiWeb(socio, email, mensualidadSeleccionada);
            if (retorno.equals("Transacción iniciada correctamente")) {
                this.txtLog.append("\n Transacción iniciada correctamente");
                if (mensualidadSeleccionada.getEnviado() == false) {

                    mensualidadSeleccionada.setEnviado(true);
                    mensualidadSeleccionada.setFechaHoraTransaccionCobrosYa(new Date());
                    mensualidadSeleccionada.setSituacionTalonCobrosYa(cobrosYa.getSituacionTransaccion());
                    mensualidadSeleccionada.setNroTalonCobrosYa(cobrosYa.getNroTalonCobrosYa());
                    mensualidadSeleccionada.setUrlPDF(cobrosYa.getUrl_pdf());
                    mensualidadSeleccionada.setIdSecreto(cobrosYa.getIdSecretoCobrosYa());

                    mensualidadesDAO = new MensualidadesDAO();
                    mensualidadesDAO.Actualizar(mensualidadSeleccionada);
                    this.txtLog.append("\n Talón: " + mensualidadSeleccionada.getNroTalonCobrosYa() + " creado correctamente!");

                    this.txtLog.append("\n Enviando talón via Email...");

                    EnviarEmail enviarEmail = new EnviarEmail(cobrosYa.getUrl_pdf(), email);
                    Boolean enviaMail = enviarEmail.enviaMail();
                    if (enviaMail == true) {
                        this.txtLog.append("\n Notificación por Email enviada correctamente a: " + email);
                    } else {
                        this.txtLog.append("\n Error al enviar Email a: " + email);
                    }

                } else {
                    this.txtLog.append("\n El rebibo ya habia sido enviado, nro. talón: " + mensualidadSeleccionada.getNroTalonCobrosYa());
                }
            } else {
                this.txtLog.append("\n Error al iniciar transacción: " + retorno);
            }
            this.txtLog.append("\n--------------------------");
        } catch (Exception ex) {
            Logger.getLogger(GeneraTalonCobrosYa.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            this.txtLog.append("\n Error: " + ex);
        }
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
