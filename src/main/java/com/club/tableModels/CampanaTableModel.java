/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Campanasms;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CampanaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha creación", "Fecha", "Mensaje"};
    //lista para a manipulacao do objeto
    private List<Campanasms> listCamapanas;

    public CampanaTableModel() {
        listCamapanas = new LinkedList<Campanasms>();
    }

    public CampanaTableModel(List<Campanasms> listCamapanas) {
        this.listCamapanas = listCamapanas;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCamapanas.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Campanasms c = listCamapanas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFechacreacion();
            case 1:
                return c.getFechacreacion();
            case 2:
                return c.getNombre();

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
                return Date.class;
            case 1:
                return Date.class;
            case 2:
                return String.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Campanasms cliente) {
        listCamapanas.add(cliente);

        this.fireTableRowsInserted(listCamapanas.size() - 1, listCamapanas.size() - 1);
    }

    public void eliminar(int row) {
        listCamapanas.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Campanasms campanasms) {
        listCamapanas.set(row, campanasms);
        this.fireTableRowsUpdated(row, row);
    }

    public Campanasms getCliente(int row) {
        return listCamapanas.get(row);
    }

}
