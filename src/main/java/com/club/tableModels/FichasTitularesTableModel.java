/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.FichaMedica;
import com.club.BEANS.Socio;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class FichasTitularesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Ficha", "Emisión", "Vencimiento", "Socio", "Exámen médico", "Confirmar"};
    //lista para a manipulacao do objeto
    private List<FichaMedica> listFichaMedicas;

    public FichasTitularesTableModel() {
        listFichaMedicas = new LinkedList<FichaMedica>();
    }

    public FichasTitularesTableModel(List<FichaMedica> listFichaMedicas) {
        this.listFichaMedicas = listFichaMedicas;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listFichaMedicas.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FichaMedica c = listFichaMedicas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getId();
            case 1:
                return c.getFechaEmision();
            case 2:
                return c.getFechaVencimiento();
            case 3:
                if (c.getSocio() == null) {
                   return c.getDependiente();
                } else {
                    return c.getSocio();
                }

            case 4:
                return c.getExamenMedico();
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
                return Date.class;
            case 2:
                return Date.class;
            case 3:
                return String.class;
            case 4:
                return Boolean.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        if (columnIndex == 5) {
            return true;
        } else {
            return false;
        }
    }

    public void agregar(FichaMedica propiedad) {
        listFichaMedicas.add(propiedad);

        this.fireTableRowsInserted(listFichaMedicas.size() - 1, listFichaMedicas.size() - 1);
    }

    public void eliminar(int row) {
        listFichaMedicas.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, FichaMedica propiedad) {
        listFichaMedicas.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public FichaMedica getCliente(int row) {
        return listFichaMedicas.get(row);
    }

}
