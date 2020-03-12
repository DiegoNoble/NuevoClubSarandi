/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Categoria;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CategoriaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Id", "Nombre"};
    //lista para a manipulacao do objeto
    private List<Categoria> listCategorias;

    public CategoriaTableModel() {
        listCategorias = new LinkedList<Categoria>();
    }

    public CategoriaTableModel(List<Categoria> listCategorias) {
        this.listCategorias = listCategorias;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCategorias.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria c = listCategorias.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getDefinicion();
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

    public void agregar(Categoria propiedad) {
        listCategorias.add(propiedad);

        this.fireTableRowsInserted(listCategorias.size() - 1, listCategorias.size() - 1);
    }

    public void eliminar(int row) {
        listCategorias.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Categoria propiedad) {
        listCategorias.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Categoria getCliente(int row) {
        return listCategorias.get(row);
    }

}
