/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CajaCampEco;
import com.club.BEANS.Rubro;
import com.club.BEANS.Sectores;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class CajaCampEcoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Rubro", "Sector", "Concepto", "Entrada", "Salida", "Saldo"};
    //lista para a manipulacao do objeto
    private List<CajaCampEco> listCajaCampEcos;

    public CajaCampEcoTableModel() {
        listCajaCampEcos = new LinkedList<CajaCampEco>();
    }

    public CajaCampEcoTableModel(List<CajaCampEco> listCajaCampEcos) {
        this.listCajaCampEcos = listCajaCampEcos;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listCajaCampEcos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CajaCampEco c = listCajaCampEcos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFechaMovimiento();
            case 1:
                return c.getRubro();
            case 2:
                return c.getSectores();
            case 3:
                return c.getConcepto();
            case 4:
                return c.getEntrada();
            case 5:
                return c.getSalida();
            case 6:
                return c.getSaldo();
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
                return Date.class;
            case 1:
                return Rubro.class;
            case 2:
                return Sectores.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(CajaCampEco propiedad) {
        listCajaCampEcos.add(propiedad);

        this.fireTableRowsInserted(listCajaCampEcos.size() - 1, listCajaCampEcos.size() - 1);
    }

    public void eliminar(int row) {
        listCajaCampEcos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, CajaCampEco propiedad) {
        listCajaCampEcos.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public CajaCampEco getCliente(int row) {
        return listCajaCampEcos.get(row);
    }

}
