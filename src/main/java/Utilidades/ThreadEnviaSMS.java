/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.club.BEANS.Campanasms;
import com.club.BEANS.Sms;
import com.club.BEANS.Socio;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.SmsDAO;
import com.club.smsmasivos.EnvioSMSIndividual;

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
public class ThreadEnviaSMS extends SwingWorker<Void, Void> {

    private final JTextArea txtStatus;
    private JInternalFrame view;
    private String mensaje;
    private String nombreCampaña;
    List<Socio> listSociosSMS;
    CampanaSmsDAO daoCampanaSMS;
    SmsDAO smsDAO;
    String status;
    int tamanoLista;

    public ThreadEnviaSMS(String nombreCampaña, String mensaje, JTextArea txtStatus, JInternalFrame view, List listSociosSMS) {
        this.txtStatus = txtStatus;
        this.mensaje = mensaje;
        this.nombreCampaña = nombreCampaña;
        this.view = view;
        this.listSociosSMS = listSociosSMS;
        this.daoCampanaSMS = daoCampanaSMS;
        tamanoLista = listSociosSMS.size();
    }

    @Override
    protected Void doInBackground() throws Exception {

        this.txtStatus.append("Procesando envios, aguarde un momento...\n");

        Campanasms nuevaCampana = new Campanasms();
        nuevaCampana.setFechacreacion(new Date());
        nuevaCampana.setNombre(nombreCampaña);

        List<Sms> listSmsEnviados = new ArrayList<>();
        int i = 0;
        for (Socio socio : listSociosSMS) {
            i++;
            Sms sms = new Sms();
            sms.setSocio(socio);
            sms.setCampanasms(nuevaCampana);
            listSmsEnviados.add(sms);
            nuevaCampana.setSmsList(listSmsEnviados);

        }

        daoCampanaSMS = new CampanaSmsDAO();
        daoCampanaSMS.Salvar(nuevaCampana);
        
        for (Sms sms : listSmsEnviados) {

            EnvioSMSIndividual enviaSMS = new EnvioSMSIndividual();

            status = enviaSMS.enviarSMSIndividual(sms.getId().toString(), sms.getSocio().getCelular(), mensaje);

            sms.setStatus(status);
            sms.setFechahoraemision(new Date());
            smsDAO = new SmsDAO();
            smsDAO.Actualizar(sms);

            this.txtStatus.append("\n");
            this.txtStatus.append(status);
            this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
        }

        return null;
    }

    @Override
    protected void done() {

        this.txtStatus.append("\n Listo!");
        this.txtStatus.setCaretPosition(this.txtStatus.getDocument().getLength());
    }

}
