/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.modelos;

import com.club.BEANS.Funcionario;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class FuncionarioTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Nombre", "CI", "Ingreso", "Egreso"};
    //lista para a manipulacao do objeto
    private List<Funcionario> listFuncionarios;

    public FuncionarioTableModel() {
        listFuncionarios = new LinkedList<Funcionario>();
    }

    public FuncionarioTableModel(List<Funcionario> listFuncionarios) {
        this.listFuncionarios = listFuncionarios;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listFuncionarios.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Funcionario c = listFuncionarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getCi();
            case 2:
                return c.getFechaingreso();
            case 3:
                return c.getFechaegreso();
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
            case 2:
                return Date.class;
            case 3:
                return Date.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Funcionario propiedad) {
        listFuncionarios.add(propiedad);

        this.fireTableRowsInserted(listFuncionarios.size() - 1, listFuncionarios.size() - 1);
    }

    public void eliminar(int row) {
        listFuncionarios.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Funcionario propiedad) {
        listFuncionarios.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Funcionario getCliente(int row) {
        return listFuncionarios.get(row);
    }

}
