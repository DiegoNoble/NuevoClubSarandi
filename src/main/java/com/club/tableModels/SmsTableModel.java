/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Sms;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class SmsTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"", "Sms", "Socio", "SMS", "Respondido"};
    //lista para a manipulacao do objeto
    private List<Sms> listSms;

    public SmsTableModel() {
        listSms = new LinkedList<Sms>();
    }

    public SmsTableModel(List<Sms> listSms) {
        this.listSms = listSms;
    }

    //numero de linhas
    public int getRowCount() {
        return listSms.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sms c = listSms.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getId();
            case 2:
                return c.getSocio().getId() + " " + c.getSocio().getNombre() + " " + c.getSocio().getCelular();
            case 3:
                return c.getStatus();
            case 4:
                return c.getRespondido();
            default:
                return null;
        }
    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irï¿½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Boolean.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Sms sms) {
        listSms.add(sms);

        this.fireTableRowsInserted(listSms.size() - 1, listSms.size() - 1);
    }

    public void eliminar(int row) {
        listSms.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Sms sms) {
        listSms.set(row, sms);
        this.fireTableRowsUpdated(row, row);
    }

    public Sms getCliente(int row) {
        return listSms.get(row);
    }

}
