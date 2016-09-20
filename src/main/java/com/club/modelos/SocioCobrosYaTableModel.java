/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.modelos;

import com.club.BEANS.Categoria;
import com.club.BEANS.Socio;
import com.club.BEANS.Socio;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class SocioCobrosYaTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cod.", "Nombre", "Cel", "Email", "Categoria", "Situación"};
    //lista para a manipulacao do objeto
    private List<Socio> listSocio;

    public SocioCobrosYaTableModel() {
        listSocio = new LinkedList<Socio>();
    }

    public SocioCobrosYaTableModel(List<Socio> listSocio) {
        this.listSocio = listSocio;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listSocio.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterÃ¯Â¿Â½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Socio c = listSocio.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getNombre();
            case 2:
                return c.getCelular();
            case 3:
                return c.getEmail();
            case 4:
                return c.getCategoria();
            case 5:
                return c.getSituacion();
            default:
                return null;
        }
    }

    //determina o nome das colunas
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    //determina que tipo de objeto cada coluna irÃ¯Â¿Â½ suportar
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
                return Categoria.class;
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

    public void agregar(Socio propiedad) {
        listSocio.add(propiedad);

        this.fireTableRowsInserted(listSocio.size() - 1, listSocio.size() - 1);
    }

    public void agregarTodos(List<Socio> socios) {
        listSocio.addAll(socios);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        listSocio.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Socio propiedad) {
        listSocio.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Socio getCliente(int row) {
        return listSocio.get(row);
    }

}
