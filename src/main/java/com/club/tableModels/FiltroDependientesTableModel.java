/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Tuple;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class FiltroDependientesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"", "Cód.", "Nombre dependiente", "Cumpleaños", "Edad", "Situación", "Titular", "Categoria"};
    //lista para a manipulacao do objeto
    private List<Tuple> listTuples;

    public FiltroDependientesTableModel() {
        listTuples = new LinkedList<Tuple>();
    }

    public FiltroDependientesTableModel(List<Tuple> listTuples) {
        this.listTuples = listTuples;
    }

    //numero de linhas
    public int getRowCount() {
        return listTuples.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tuple c = listTuples.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.get(0);
            case 2:
                return c.get(1);
            case 3:
                return c.get(2);
            case 4:
                return c.get(3);
            case 5:
                return c.get(4);
            case 6:
                return c.get(5) + "  " + c.get(6);
            case 7:
                return c.get(8);
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
                return Date.class;
            case 4:
                return Integer.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Tuple Tuple) {
        listTuples.add(Tuple);

        this.fireTableRowsInserted(listTuples.size() - 1, listTuples.size() - 1);
    }

    public void eliminar(int row) {
        listTuples.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Tuple Tuple) {
        listTuples.set(row, Tuple);
        this.fireTableRowsUpdated(row, row);
    }

    public Tuple getTuple(int row) {
        return listTuples.get(row);
    }

}
