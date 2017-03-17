/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CampEconomica;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CampEconomicaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"id", "Fecha", "Nombre"};
    //lista para a manipulacao do objeto
    private List<CampEconomica> listCampEconomicas;

    public CampEconomicaTableModel() {
        listCampEconomicas = new LinkedList<CampEconomica>();
    }

    public CampEconomicaTableModel(List<CampEconomica> listCampEconomicas) {
        this.listCampEconomicas = listCampEconomicas;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCampEconomicas.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CampEconomica c = listCampEconomicas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
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
                return Integer.class;
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

    public void agregar(CampEconomica propiedad) {
        listCampEconomicas.add(propiedad);

        this.fireTableRowsInserted(listCampEconomicas.size() - 1, listCampEconomicas.size() - 1);
    }

    public void eliminar(int row) {
        listCampEconomicas.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CampEconomica propiedad) {
        listCampEconomicas.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public CampEconomica getCliente(int row) {
        return listCampEconomicas.get(row);
    }

}
