/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.Categoria;
import com.club.BEANS.Socio;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class SocioTableModelControlAcceso extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cód.", "Nombre", "CI", "Categoria", "Situación", "Cel"};
    //lista para a manipulacao do objeto
    private List<Socio> listSocios;

    public SocioTableModelControlAcceso() {
        listSocios = new LinkedList<Socio>();
    }

    public SocioTableModelControlAcceso(List<Socio> listSocios) {
        this.listSocios = listSocios;
    }

    //numero de linhas
    public int getRowCount() {
        return listSocios.size();
    }

    //numero de colunas
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    public Object getValueAt(int rowIndex, int columnIndex) {
        Socio c = listSocios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getNombre();
            case 2:
                return c.getCi();
            case 3:
                return c.getCategoria();
            case 4:
                return c.getSituacion();
            case 5:
                return c.getCelular();
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
                return Categoria.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Socio Socio) {
        listSocios.add(Socio);

        this.fireTableRowsInserted(listSocios.size() - 1, listSocios.size() - 1);
    }

    public void eliminar(int row) {
        listSocios.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Socio Socio) {
        listSocios.set(row, Socio);
        this.fireTableRowsUpdated(row, row);
    }

    public Socio getSocio(int row) {
        return listSocios.get(row);
    }

}
