/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.modelos;

import com.club.BEANS.Rubro;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class RubroTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"id", "Nombre", "Valor Fijo", "Valor"};
    //lista para a manipulacao do objeto
    private List<Rubro> listRubros;

    public RubroTableModel() {
        listRubros = new LinkedList<Rubro>();
    }

    public RubroTableModel(List<Rubro> listPropiedades) {
        this.listRubros = listPropiedades;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listRubros.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rubro c = listRubros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getNombreRubro();
            case 2:
                return c.getValorFijo();
            case 3:
                return c.getValor();
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
            case 2:
                return Boolean.class;
            case 3:
                return Double.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Rubro propiedad) {
        listRubros.add(propiedad);

        this.fireTableRowsInserted(listRubros.size() - 1, listRubros.size() - 1);
    }

    public void eliminar(int row) {
        listRubros.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Rubro propiedad) {
        listRubros.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Rubro getCliente(int row) {
        return listRubros.get(row);
    }

}
