/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Sectores;
import com.club.BEANS.SectoresFuncionario;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class SectoresFuncionarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Sector", "Procentage %"};
    //lista para a manipulacao do objeto
    private List<SectoresFuncionario> list;

    public SectoresFuncionarioTableModel() {
        list = new LinkedList<SectoresFuncionario>();
    }

    public SectoresFuncionarioTableModel(List<SectoresFuncionario> listPropietariosHasPropiedads) {
        this.list = listPropietariosHasPropiedads;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return list.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SectoresFuncionario c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getSector();
            case 1:
                return c.getProcentageSector();
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
                return Sectores.class;
            case 1:
                return Double.class;
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SectoresFuncionario c = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                c.setSector((Sectores) aValue);
                break;
            case 1:
                c.setProcentageSector((Double) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void agregar(SectoresFuncionario sectoresFuncionario) {
        list.add(sectoresFuncionario);

        this.fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }

    public void eliminar(int row) {
        list.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, SectoresFuncionario sectoresFuncionario) {
        list.set(row, sectoresFuncionario);
        this.fireTableRowsUpdated(row, row);
    }

    public SectoresFuncionario getFuncionario(int row) {
        return list.get(row);
    }

}
