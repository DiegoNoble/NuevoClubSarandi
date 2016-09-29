/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import com.club.BEANS.Campanasms;
import com.club.BEANS.Parametros;
import com.club.BEANS.RespuestaSMS;
import com.club.BEANS.Sms;
import com.club.DAOs.RespuestaSmsDAO;
import com.club.DAOs.SmsDAO;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import smsmasivos.ArrayOfClsRespuesta;
import smsmasivos.ClsRespuesta;
import smsmasivos.SMSMasivosAPI;

/**
 *
 * @author Diego Noble
 */
public class ThreadRecibeRespuesta extends SwingWorker<Void, Void> {

    private final JTextArea txtStatus;
    private JInternalFrame view;
    private Campanasms campanasms;
    private Parametros parametros;
    Boolean soloNoLeidos;
    Boolean marcarComoLeidos;
    SmsDAO smsDAO;
    RespuestaSmsDAO respuestaSmsDAO;
    int respuestas = 1;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public ThreadRecibeRespuesta(JTextArea txtStatus, JInternalFrame view, Campanasms campanasms, Parametros parametros, Boolean soloNoLeidos, Boolean marcarComoLeidos) {
        this.txtStatus = txtStatus;
        this.campanasms = campanasms;
        this.parametros = parametros;
        this.soloNoLeidos = soloNoLeidos;
        this.marcarComoLeidos = marcarComoLeidos;
        this.view = view;
    }

    @Override
    protected Void doInBackground() throws Exception {

        this.txtStatus.append("Procesando respuestas, aguarde un momento...\n");

        SMSMasivosAPI ws = new SMSMasivosAPI();
        ArrayOfClsRespuesta verificaTotos = ws.getSMSMasivosAPISoap().recibirSMS(parametros.getUsuario_SMS(), parametros.getPsw_SMS(), "", soloNoLeidos, Boolean.FALSE);
        List<ClsRespuesta> todos = verificaTotos.getClsRespuesta();

        Map<Long, String> numeros = new HashMap<>();
        for (ClsRespuesta res : todos) {
            numeros.put(res.getNumero(), res.getIdinterno());
        }
        for (Map.Entry<Long, String> valores : numeros.entrySet()) {

            ArrayOfClsRespuesta recibirSMS = ws.getSMSMasivosAPISoap().recibirSMS(parametros.getUsuario_SMS(), parametros.getPsw_SMS(), String.valueOf(valores.getKey()), soloNoLeidos, marcarComoLeidos);
            List<ClsRespuesta> clsRespuesta = recibirSMS.getClsRespuesta();

            smsDAO = new SmsDAO();
            Sms sms = smsDAO.BuscarPorId(Integer.parseInt(valores.getValue()));

            smsDAO = new SmsDAO();
            sms.setRespondido(true);
            smsDAO.Actualizar(sms);
            
            this.txtStatus.append("\n-------------------------------\n");
            this.txtStatus.append("SMS id: " + sms.getId());
            this.txtStatus.append("\n");
            this.txtStatus.append("Socio " + sms.getSocio() + " " + sms.getSocio().getCelular());

            for (ClsRespuesta respuesta : clsRespuesta) {
                respuestas++;

                RespuestaSMS respuestaSMS = new RespuestaSMS();
                respuestaSMS.setId(respuesta.getIdsms());
                respuestaSMS.setSms(sms);
                respuestaSMS.setFecha_respuesta(respuesta.getFecha().toGregorianCalendar().getTime());
                respuestaSMS.setRespuesta(respuesta.getTexto());
                respuestaSmsDAO = new RespuestaSmsDAO();
                respuestaSmsDAO.SalvarOActualizar(respuestaSMS);

//                this.txtStatus.append("Respuesta nro: " + respuestas);
                this.txtStatus.append("\n");
                this.txtStatus.append("Fecha: " + dateFormat.format(respuestaSMS.getFecha_respuesta()) + " Responde: " + respuestaSMS.getRespuesta());
                //              this.txtStatus.append("\n");
                this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
            }

        }

        return null;

    }

    @Override
    protected void done() {

        this.txtStatus.append("\n");
        this.txtStatus.append("\n Se recibieron " + respuestas + " respuestas!");
        this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
    }

}
