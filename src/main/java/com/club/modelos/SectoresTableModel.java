/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.modelos;

import com.club.BEANS.Sectores;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class SectoresTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cod. Referencia", "Nombre"};
    //lista para a manipulacao do objeto
    private List<Sectores> listSectoress;

    public SectoresTableModel() {
        listSectoress = new LinkedList<Sectores>();
    }

    public SectoresTableModel(List<Sectores> listSectoress) {
        this.listSectoress = listSectoress;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listSectoress.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sectores c = listSectoress.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getCodRef();
            case 1:
                return c.getNombreSector();
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
                return String.class;
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

    public void agregar(Sectores propiedad) {
        listSectoress.add(propiedad);

        this.fireTableRowsInserted(listSectoress.size() - 1, listSectoress.size() - 1);
    }

    public void eliminar(int row) {
        listSectoress.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Sectores propiedad) {
        listSectoress.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Sectores getCliente(int row) {
        return listSectoress.get(row);
    }

}
