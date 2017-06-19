/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Dependiente;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class DependienteTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cód.", "Nombre", "C.I.", "Situación", "Parentezco", "Fecha nacimiento"};
    //lista para a manipulacao do objeto
    private List<Dependiente> listDependientes;

    public DependienteTableModel() {
        listDependientes = new LinkedList<Dependiente>();
    }

    public DependienteTableModel(List<Dependiente> listDependientes) {
        this.listDependientes = listDependientes;
    }

    //numero de linhas
    public int getRowCount() {
        return listDependientes.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Dependiente c = listDependientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getNombre();
            case 2:
                return c.getCi();
            case 3:
                return c.getSituacion();
            case 4:
                return c.getParentesco();
            case 5:
                return c.getFechanacimiento();
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
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Date.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Dependiente Dependiente) {
        listDependientes.add(Dependiente);

        this.fireTableRowsInserted(listDependientes.size() - 1, listDependientes.size() - 1);
    }

    public void eliminar(int row) {
        listDependientes.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Dependiente Dependiente) {
        listDependientes.set(row, Dependiente);
        this.fireTableRowsUpdated(row, row);
    }

    public Dependiente getDependiente(int row) {
        return listDependientes.get(row);
    }

}
