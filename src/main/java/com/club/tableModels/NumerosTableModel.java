/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CampEconomica;
import com.club.BEANS.Numeros;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class NumerosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"","Campaña", "Nro 1 ", "Nro 2", "Disponibilidad"};
    //lista para a manipulacao do objeto
    private List<Numeros> listNumeross;

    public NumerosTableModel() {
        listNumeross = new LinkedList<Numeros>();
    }

    public NumerosTableModel(List<Numeros> listNumeross) {
        this.listNumeross = listNumeross;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listNumeross.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Numeros c = listNumeross.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return c.getCampEconomica();
            case 2:
                return c.getNro1();
            case 3:
                return c.getNro2();
            case 4:
                return c.getDisponible();
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
                return CampEconomica.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
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

    public void agregar(Numeros propiedad) {
        listNumeross.add(propiedad);

        this.fireTableRowsInserted(listNumeross.size() - 1, listNumeross.size() - 1);
    }

    public void eliminar(int row) {
        listNumeross.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Numeros propiedad) {
        listNumeross.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Numeros getCliente(int row) {
        return listNumeross.get(row);
    }

}
