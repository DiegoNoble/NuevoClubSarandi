package com.club.views;

import Utilidades.AjustaColumnas;
import com.club.BEANS.Campanasms;
import com.club.BEANS.RespuestaSMS;
import com.club.BEANS.Sms;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.RespuestaSmsDAO;
import com.club.DAOs.SmsDAO;
import com.club.Renderers.MeDateCellRenderer;
import com.club.Renderers.MeDefaultCellRenderer;
import com.club.tableModels.CampanaTableModel;
import com.club.tableModels.SmsTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsultaRespuestasCamapanSMSView extends javax.swing.JInternalFrame {

    CampanaTableModel tableModel;
    SmsTableModel smsTableModel;
    List<Sms> listSms;
    List<RespuestaSMS> listRespuestaSMS;
    List<Campanasms> listCampanasms;
    List<Sms> listSmsCampañaSeleccionada;
    CampanaSmsDAO campanaSmsDAO;
    SmsDAO smsDAO;
    Sms smsSelecciodo;
    RespuestaSmsDAO respuestaSmsDAO;
    ListSelectionModel listModel;
    Campanasms campanaSeleccionada;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public ConsultaRespuestasCamapanSMSView() {
        initComponents();
        TableModel();
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) tblCampañas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) tblSMS.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        listCampanasms = new ArrayList<Campanasms>();
        campanaSmsDAO = new CampanaSmsDAO();
        listCampanasms.addAll(campanaSmsDAO.BuscaTodos(Campanasms.class));

        tableModel = new CampanaTableModel(listCampanasms);
        tblCampañas.setModel(tableModel);

        listSms = new ArrayList<Sms>();

        int[] anchosCampañas = {20, 50, 400};
        new AjustaColumnas().ajustar(tblCampañas, anchosCampañas);

        smsTableModel = new SmsTableModel(listSms);
        tblSMS.setModel(smsTableModel);
        int[] anchos = {25, 30, 300, 50, 50};
        new AjustaColumnas().ajustar(tblSMS, anchos);

        tblCampañas.getColumn("Fecha creación").setCellRenderer(new MeDateCellRenderer());

        tblSMS.getColumn("Socio").setCellRenderer(new MeDefaultCellRenderer());
        tblSMS.setRowHeight(25);

        listModel = tblCampañas.getSelectionModel();
        listModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblCampañas.getSelectedRow() != -1) {
                    campanaSeleccionada = listCampanasms.get(tblCampañas.getSelectedRow());
                    listSms.clear();
                    smsDAO = new SmsDAO();
                    listSms.addAll(smsDAO.BuscarPorCampaña(campanaSeleccionada));
                    smsTableModel.fireTableDataChanged();

                }
            }
        });

        ListSelectionModel listModelSMS = tblSMS.getSelectionModel();
        listModelSMS.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblSMS.getSelectedRow() != -1) {
                    smsSelecciodo = listSms.get(tblSMS.getSelectedRow());

                    listRespuestaSMS = new ArrayList<>();
                    respuestaSmsDAO = new RespuestaSmsDAO();
                    listRespuestaSMS = respuestaSmsDAO.BuscarPorSMS(smsSelecciodo);
                    txtRespuestas.setText("");
                    for (RespuestaSMS respuestaSMS : listRespuestaSMS) {

                        txtRespuestas.append(dateFormat.format(respuestaSMS.getFecha_respuesta()) + " " + respuestaSMS.getRespuesta());
                        txtRespuestas.append("\n");
                    }

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCampañas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSMS = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRespuestas = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        tblCampañas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCampañas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblCampañas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        tblSMS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblSMS);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jScrollPane4, gridBagConstraints);

        txtRespuestas.setColumns(20);
        txtRespuestas.setRows(5);
        jScrollPane2.setViewportView(txtRespuestas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Arial", 3, 48)); // NOI18N
        jLabel3.setText("Consulta campañas SMS");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel5, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblCampañas;
    private javax.swing.JTable tblSMS;
    private javax.swing.JTextArea txtRespuestas;
    // End of variables declaration//GEN-END:variables

}
