/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smsmasivos;

import com.club.BEANS.Campanasms;
import com.club.BEANS.Sms;
import com.club.DAOs.SmsDAO;
import com.club.sms.webservice.ArrayOfClsRespuesta;
import com.club.sms.webservice.ClsRespuesta;
import com.club.sms.webservice.SMSMasivosAPI;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author Diego Noble
 */
public class ThreadRecibeRespuesta extends SwingWorker<Void, Void> {

    private final JTextArea txtStatus;
    private JInternalFrame view;
    private Campanasms campanasms;
    SmsDAO smsDAO;
    int respuestas = 0;

    public ThreadRecibeRespuesta(JTextArea txtStatus, JInternalFrame view, Campanasms campanasms) {
        this.txtStatus = txtStatus;
        this.campanasms = campanasms;
        this.view = view;
    }

    public int actualizaRespuestas(String origen, int nroRespuesta) throws Exception {
        SMSMasivosAPI ws = new SMSMasivosAPI();
        ArrayOfClsRespuesta recibirSMS = ws.getSMSMasivosAPISoap().recibirSMS("DIEGONOBLE", "DIEGONOBLE512", origen, Boolean.TRUE, Boolean.FALSE);
        List<ClsRespuesta> clsRespuesta = recibirSMS.getClsRespuesta();

        for (ClsRespuesta respuesta : clsRespuesta) {

            smsDAO = new SmsDAO();
            Sms sms = smsDAO.BuscarPorId(Integer.valueOf(respuesta.getIdinterno()));
            sms.setRespuesta(respuesta.getTexto());
            sms.setFecha_respuesta(respuesta.getFecha().toGregorianCalendar().getTime());
            smsDAO = new SmsDAO();
            smsDAO.Actualizar(sms);

            this.txtStatus.append("\n");
            this.txtStatus.append("Respuesta nro: " + nroRespuesta);
            
            this.txtStatus.append("\n");
            this.txtStatus.append("Socio " + sms.getSocio() + " " + sms.getSocio().getCelular() + " " + sms.getRespuesta());
            this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
        }
        return clsRespuesta.size();
    }

    @Override
    protected Void doInBackground() throws Exception {

        
        this.txtStatus.append("Procesando respuestas, aguarde un momento...\n");
        smsDAO = new SmsDAO();
        List<Sms> listSmsCampañaSeleccionada = smsDAO.BuscarPorCampaña(campanasms);

        for (Sms sms : listSmsCampañaSeleccionada) {
            respuestas += actualizaRespuestas(sms.getSocio().getCelular(), respuestas);
            this.txtStatus.append("\n");
            this.txtStatus.append("SMS id: " + sms.getId());

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
