/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CuotaCampEconomica;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CuotasCampEcoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Cuota", "Vencimiento", "Valor", "Situacion", "Fecha de Pago"};
    //lista para a manipulacao do objeto
    private List<CuotaCampEconomica> listCuotaCampEconomicas;

    public CuotasCampEcoTableModel() {
        listCuotaCampEconomicas = new LinkedList<CuotaCampEconomica>();
    }

    public CuotasCampEcoTableModel(List<CuotaCampEconomica> listCuotaCampEconomicas) {
        this.listCuotaCampEconomicas = listCuotaCampEconomicas;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCuotaCampEconomicas.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CuotaCampEconomica c = listCuotaCampEconomicas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getNroCuota();
            case 1:
                return c.getFechaVencimiento();
            case 2:
                return c.getValor();
            case 3:
                return c.getPago();
            case 4:
                return c.getFechaPago();
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
                return Double.class;
            case 3:
                return Boolean.class;
            case 4:
                return Date.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(CuotaCampEconomica propiedad) {
        listCuotaCampEconomicas.add(propiedad);

        this.fireTableRowsInserted(listCuotaCampEconomicas.size() - 1, listCuotaCampEconomicas.size() - 1);
    }

    public void eliminar(int row) {
        listCuotaCampEconomicas.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CuotaCampEconomica propiedad) {
        listCuotaCampEconomicas.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public CuotaCampEconomica getCliente(int row) {
        return listCuotaCampEconomicas.get(row);
    }

}
