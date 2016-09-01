/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import Utilidades.AjustaColumnas;
import Utilidades.ControlarEntradaTexto;
import Utilidades.ThreadEnviaSMS;
import com.club.BEANS.Categoria;
import com.club.BEANS.Socio;
import com.club.DAOs.SocioDAO;
import com.club.DAOs.CampanaSmsDAO;
import com.club.DAOs.CategoriaDAO;
import com.club.tableModels.SocioTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.openswing.swing.table.columns.client.ButtonColumn;

/**
 *
 * @author Diego Noble
 */
public class SMSMasivosController implements ActionListener {

    SocioDAO socioDAO;
    CampanaSmsDAO daoCampanaSMS;
    SMSMasivosView view;
    List<Socio> listSocios;
    List<Socio> listSociosSMS;
    SocioTableModel tableModel;
    SocioTableModel tableModelSociosSMS;
    Socio nuevoSocio;
    ButtonColumn btnEditar;
    ButtonColumn btnEliminar;
    JDesktopPane jDesktopPane;

    public SMSMasivosController(SMSMasivosView view, JDesktopPane pane) {

        this.jDesktopPane = pane;
        this.view = view;
        view.setSize(jDesktopPane.getSize());

        Character chs[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '?', '#', '$', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', ' '};
        view.txtMensaje.setDocument(new ControlarEntradaTexto(160, chs));
        inicia();
    }

    private void inicia() {

        this.view.btnAgregar.setActionCommand("btnAgregar");
        this.view.btnAgregar.addActionListener(this);
        this.view.btnQuitar.setActionCommand("btnQuitar");
        this.view.btnQuitar.addActionListener(this);
        this.view.btnEnviar.setActionCommand("btnEnviar");
        this.view.btnEnviar.addActionListener(this);

        this.view.btnBuscar.setActionCommand("btnBuscar");
        this.view.btnBuscar.addActionListener(this);

        view.cbCategoria.removeAllItems();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> list = categoriaDAO.BuscaTodos(Categoria.class);
        for (Categoria categoria : list) {
            view.cbCategoria.addItem(categoria);
        }

        TableModel();
    }

    public void go() {
        this.jDesktopPane.add(view);
        this.view.setVisible(true);
        this.view.toFront();

    }

    void buscaSocios() {
        socioDAO = new SocioDAO();
        listSocios.clear();
        List<Socio> socios = socioDAO.BuscaPorCategoriaSituacionConCelular((Categoria) view.cbCategoria.getSelectedItem(), view.cbSituacion.getSelectedItem().toString());
        for (Socio socio : socios) {
            System.out.println(listSociosSMS.contains(socio));

            if (listSociosSMS.contains(socio) == false) {
                listSocios.add(socio);
            }

        }

        tableModel.fireTableDataChanged();
    }

    private void TableModel() {

        ((DefaultTableCellRenderer) view.tblSocios.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) view.tblSociosSMS.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        view.tblSocios.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        view.tblSociosSMS.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        listSocios = new ArrayList<Socio>();
        //socioDAO = new SocioDAO();
        //listSocios.addAll(socioDAO.BuscaTodos(Socio.class));
        tableModel = new SocioTableModel(listSocios);
        view.tblSocios.setModel(tableModel);

        listSociosSMS = new ArrayList<Socio>();
        tableModelSociosSMS = new SocioTableModel(listSociosSMS);
        view.tblSociosSMS.setModel(tableModelSociosSMS);

        int[] anchos = {5, 200, 50, 50, 50};
        new AjustaColumnas().ajustar(view.tblSocios, anchos);
        new AjustaColumnas().ajustar(view.tblSociosSMS, anchos);

        view.tblSocios.setRowHeight(25);
        view.tblSociosSMS.setRowHeight(25);
        view.txtMensaje.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (view.txtMensaje.getText().equals("")) {
                    view.btnEnviar.setEnabled(false);
                } else {
                    view.btnEnviar.setEnabled(true);
                }

            }
        });
    }

    void crearCampanaEnviarSMS() {

        int tamano = listSociosSMS.size();

        if (tamano == 0) {
            JOptionPane.showMessageDialog(view, "Debe seleccionar almenos 1 Socio", "Error", JOptionPane.ERROR_MESSAGE);

        } else if (tamano >= 500) {
            JOptionPane.showMessageDialog(view, "La campaña tiene " + tamano + " Socios seleccionados, puede enviar como máximo 500 sms por camapaña", "Error", JOptionPane.ERROR_MESSAGE);

        } else {

            ThreadEnviaSMS envia = new ThreadEnviaSMS(view.txtNombreCampaña.getText(), view.txtMensaje.getText(), view.txtLog, view, listSociosSMS);
            envia.execute();
        }
    }

    /*public void actualizaTbl() {
     listSocios.clear();
     socioDAO = new SocioDAO();
     listSocios.addAll(socioDAO.BuscaTodos(Socio.class));
     tableModel.fireTableDataChanged();
     }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            case "btnAgregar":

                int[] indexSocios = view.tblSocios.getSelectedRows();
                for (int c : indexSocios) {
                    Socio Socio = listSocios.get(c);

                    listSociosSMS.add(Socio);
                }
                listSocios.removeAll(listSociosSMS);
                tableModel.fireTableDataChanged();
                tableModelSociosSMS.fireTableDataChanged();

                break;

            case "btnQuitar":

                int[] indexSociosQuitar = view.tblSociosSMS.getSelectedRows();
                for (int c : indexSociosQuitar) {
                    Socio Socio = listSociosSMS.get(c);
                    listSocios.add(Socio);
                }
                listSociosSMS.removeAll(listSocios);
                tableModel.fireTableDataChanged();
                tableModelSociosSMS.fireTableDataChanged();

                break;

            case "btnEnviar":

                crearCampanaEnviarSMS();

                break;
            case "btnBuscar":

                buscaSocios();

                break;
            default:
                throw new AssertionError();
        }
    }

}
