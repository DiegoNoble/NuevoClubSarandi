/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import Utilidades.AchicarURL;
import com.club.BEANS.Campanasms;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Sms;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.SmsDAO;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author Diego Noble
 */
public class ThreadEnviaRecordatorioSMSTalonCobrosYa extends SwingWorker<Void, Void> {

    private final JTextArea txtStatus;
    private JInternalFrame view;
    private String nombreCampaña;
    private Boolean prueba;
    List<Mensualidades> listMensualidadesSMS;
    CampanaSmsDAO daoCampanaSMS;
    SmsDAO smsDAO;
    String status;
    int tamanoLista;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    public ThreadEnviaRecordatorioSMSTalonCobrosYa(String nombreCampaña, JTextArea txtStatus, JInternalFrame view, List listMensualidadesSMS, Boolean prueba) {
        this.txtStatus = txtStatus;
        this.nombreCampaña = nombreCampaña;
        this.view = view;
        this.prueba = prueba;
        this.listMensualidadesSMS = listMensualidadesSMS;
        tamanoLista = listMensualidadesSMS.size();
    }

    @Override
    protected Void doInBackground() throws Exception {

        this.txtStatus.append("Procesando envios, aguarde un momento...\n");

        if (prueba == Boolean.FALSE) {
            Campanasms nuevaCampana = new Campanasms();

            nuevaCampana.setFechacreacion(new Date());
            nuevaCampana.setNombre(nombreCampaña);
            //nuevaCampana.setMensaje("Talones vencimiento");

            List<Sms> listSmsEnviados = new ArrayList<>();
            int i = 0;
            for (Mensualidades m : listMensualidadesSMS) {
                AchicarURL uRL = new AchicarURL();
                i++;
                Sms sms = new Sms();
                sms.setSocio(m.getSocio());
                sms.setCampanasms(nuevaCampana);
                sms.setMensajeTalonCobrosYa("Recorda pagar $" + m.getValor() + " a Club Sarandi Universitario en Redpagos "
                        + "Servicio CobrosYA Nro Talon " + m.getNroTalonCobrosYa() + " Vto " + formato.format(m.getFechaVencimiento()) + " " + uRL.achicar(m.getUrlPDF()));
                listSmsEnviados.add(sms);
                nuevaCampana.setSmsList(listSmsEnviados);

            }

            daoCampanaSMS = new CampanaSmsDAO();
            daoCampanaSMS.Salvar(nuevaCampana);

            for (Sms sms : listSmsEnviados) {

                EnvioSMSIndividual enviaSMS = new EnvioSMSIndividual();

                status = enviaSMS.enviarSMSIndividual(sms.getId().toString(), sms.getSocio().getCelular(), sms.getSocio().getNombre() + " " + sms.getMensajeTalonCobrosYa());

                sms.setStatus(status);
                sms.setFechahoraemision(new Date());
                smsDAO = new SmsDAO();
                smsDAO.Actualizar(sms);

                this.txtStatus.append("\n");
                this.txtStatus.append("Socio " + sms.getSocio() + " " + sms.getSocio().getCelular() + " " + status);
                this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
            }

            return null;
        } else if (prueba == Boolean.TRUE) {

            List<Sms> listSmsEnviados = new ArrayList<>();
            int i = 0;
            for (Mensualidades m : listMensualidadesSMS) {
                i++;
                AchicarURL uRL = new AchicarURL();
                Sms sms = new Sms();
                sms.setSocio(m.getSocio());
                sms.setMensajeTalonCobrosYa("Recorda pagar $" + m.getValor() + " a Club Sarandi Universitario en Redpagos "
                        + "Servicio CobrosYA Nro Talon " + m.getNroTalonCobrosYa() + " Vto " + formato.format(m.getFechaVencimiento()) + " " + uRL.achicar(m.getUrlPDF()));
                listSmsEnviados.add(sms);

            }
int j = 0;
            for (Sms sms : listSmsEnviados) {
                j++;
                EnvioSMSIndividual enviaSMS = new EnvioSMSIndividual();

                status = enviaSMS.pruebaEnviarSMSIndividual(String.valueOf(listSmsEnviados.indexOf(sms)), sms.getSocio().getCelular(), sms.getMensajeTalonCobrosYa());

                sms.setStatus(status);
                sms.setFechahoraemision(new Date());

                this.txtStatus.append("\n");
                this.txtStatus.append(j+" - "+status);
                this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
            }

        }
        return null;
    }

    @Override
    protected void done() {

        this.txtStatus.append("\n Listo!");
        this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
    }

}
