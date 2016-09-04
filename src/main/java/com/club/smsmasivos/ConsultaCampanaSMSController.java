/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import Utilidades.AjustaColumnas;
import com.club.BEANS.Campanasms;
import com.club.BEANS.Sms;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.SmsDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.Renderers.MeDefaultCellRenderer;
import com.club.sms.webservice.ArrayOfClsRespuesta;
import com.club.sms.webservice.ClsRespuesta;
import com.club.sms.webservice.SMSMasivosAPI;
import com.club.tableModels.CampanaTableModel;
import com.club.tableModels.SmsTableModel;
import com.club.views.ConsultaCampanasSMSView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class ConsultaCampanaSMSController implements ActionListener {

    public ConsultaCampanasSMSView view;
    CampanaTableModel tableModel;
    SmsTableModel smsTableModel;
    List<Sms> listSms;
    List<Campanasms> listCampanasms;
    CampanaSmsDAO campanaSmsDAO;
    SmsDAO smsDAO;
    ListSelectionModel listModel;
    JDesktopPane desktopPane;
    Campanasms campanaSeleccionada;

    public ConsultaCampanaSMSController(ConsultaCampanasSMSView view, JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.view = view;
        inicia();
    }

    private void inicia() {
        this.view.btnActualizarRespuestas.setActionCommand("btnActualizarRespuestas");
        this.view.btnActualizarRespuestas.addActionListener(this);

        TableModel();
    }

    public void go() {
        desktopPane.add(view);
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblCampañas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) view.tblSMS.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCampanasms = new ArrayList<Campanasms>();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));

        tableModel = new CampanaTableModel(listCampanasms);
        view.tblCampañas.setModel(tableModel);

        listSms = new ArrayList<Sms>();

        int[] anchos = {500, 50, 10, 100};
        new AjustaColumnas().ajustar(view.tblSMS, anchos);
        
        int[] anchosCampañas = {20, 50, 400};
        new AjustaColumnas().ajustar(view.tblCampañas, anchosCampañas);

        
        smsTableModel = new SmsTableModel(listSms);
        view.tblSMS.setModel(smsTableModel);

        view.tblCampañas.getColumn("Fecha creación").setCellRenderer(new MeDateCellRenderer());

        view.tblSMS.getColumn("Socio").setCellRenderer(new MeDefaultCellRenderer());
        view.tblSMS.getColumn("Cel").setCellRenderer(new MeDefaultCellRenderer());
        view.tblSMS.setRowHeight(25);

        listModel = view.tblCampañas.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tblCampañas.getSelectedRow() != -1) {
                    listSms.clear();
                    listSms.addAll(listCampanasms.get(view.tblCampañas.getSelectedRow()).getSmsList());
                    smsTableModel.fireTableDataChanged();

                    campanaSeleccionada = listCampanasms.get(view.tblCampañas.getSelectedRow());
                    view.btnActualizarRespuestas.setEnabled(true);
                } else {
                    view.btnActualizarRespuestas.setEnabled(false);
                }
            }
        });

    }

    public void actualizaTbl() {
        listCampanasms.clear();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));
        tableModel.fireTableDataChanged();
    }

    public int actualizaRespuestas(String origen) throws Exception {
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
        }
        return clsRespuesta.size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnActualizarRespuestas":
                try {
                    List<Sms> listSmsCampañaSeleccionada = campanaSeleccionada.getSmsList();
                    int respuestas = 0;
                    for (Sms sms : listSmsCampañaSeleccionada) {
                        respuestas += actualizaRespuestas(sms.getRespuesta());
                    }
                    JOptionPane.showMessageDialog(null, respuestas + " Respuestas recibidas", "Correcto", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            default:
                throw new AssertionError();
        }
    }

}
