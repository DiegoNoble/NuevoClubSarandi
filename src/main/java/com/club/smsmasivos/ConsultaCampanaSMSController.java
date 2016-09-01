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
import com.club.Renderers.MeDateCellRenderer;
import com.club.Renderers.MeDefaultCellRenderer;
import com.club.tableModels.CampanaTableModel;
import com.club.tableModels.SmsTableModel;
import com.club.views.ConsultaCampanasSMSView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
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
    ListSelectionModel listModel;
    JDesktopPane desktopPane;

    public ConsultaCampanaSMSController(ConsultaCampanasSMSView view, JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.view = view;
        inicia();
    }

    private void inicia() {

        TableModel();
    }

    public void go() {
        desktopPane.add(view);
        this.view.setVisible(true);
        this.view.toFront();

    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) view.tblSMS.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCampanasms = new ArrayList<Campanasms>();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));

        tableModel = new CampanaTableModel(listCampanasms);
        view.tbl.setModel(tableModel);

        listSms = new ArrayList<Sms>();

        int[] anchos = {200, 50, 10, 100};
        new AjustaColumnas().ajustar(view.tblSMS, anchos);
        smsTableModel = new SmsTableModel(listSms);
        view.tblSMS.setModel(smsTableModel);

        view.tbl.getColumn("Mensaje").setPreferredWidth(400);
        view.tbl.getColumn("Fecha creación").setCellRenderer(new MeDateCellRenderer());

        view.tblSMS.getColumn("Socio").setCellRenderer(new MeDefaultCellRenderer());
        view.tblSMS.getColumn("Cel").setCellRenderer(new MeDefaultCellRenderer());
        view.tblSMS.getColumn("SMS").setPreferredWidth(400);
        view.tblSMS.setRowHeight(25);

        listModel = view.tbl.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (view.tbl.getSelectedRow() != -1) {
                    listSms.clear();
                    listSms.addAll(listCampanasms.get(view.tbl.getSelectedRow()).getSmsList());
                    smsTableModel.fireTableDataChanged();

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

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnNuevo":

                break;

            case "btnEditar":

                break;
            case "txtBusqueda":

                listCampanasms.clear();
                //listCampanasms.addAll(campanaSmsDAO.findCliente(view.txtBusqueda.getText()));
                tableModel.fireTableDataChanged();
                break;
            default:
                throw new AssertionError();
        }
    }

}
