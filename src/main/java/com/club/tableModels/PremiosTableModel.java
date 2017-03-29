/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CampEconomica;
import com.club.BEANS.Premio;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PremiosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"", "Descripción"};
    //lista para a manipulacao do objeto
    private List<Premio> listPremios;

    public PremiosTableModel() {
        listPremios = new LinkedList<Premio>();
    }

    public PremiosTableModel(List<Premio> listPremios) {
        this.listPremios = listPremios;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listPremios.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Premio c = listPremios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getDescripcion();
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
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Premio propiedad) {
        listPremios.add(propiedad);

        this.fireTableRowsInserted(listPremios.size() - 1, listPremios.size() - 1);
    }

    public void eliminar(int row) {
        listPremios.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Premio propiedad) {
        listPremios.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Premio getCliente(int row) {
        return listPremios.get(row);
    }

}
