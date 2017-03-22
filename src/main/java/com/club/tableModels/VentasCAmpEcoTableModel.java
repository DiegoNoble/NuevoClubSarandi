/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.CampEconomica;
import com.club.BEANS.VentaCampEco;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class VentasCAmpEcoTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"", "Socio", "Números", "Cobrador","Cuotas","Valor total"};
    //lista para a manipulacao do objeto
    private List<VentaCampEco> listVentaCampEcos;

    public VentasCAmpEcoTableModel() {
        listVentaCampEcos = new LinkedList<VentaCampEco>();
    }

    public VentasCAmpEcoTableModel(List<VentaCampEco> listVentaCampEcos) {
        this.listVentaCampEcos = listVentaCampEcos;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listVentaCampEcos.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VentaCampEco c = listVentaCampEcos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getSocio().getId() + ", " + c.getSocio().getNombre();
            case 2:
                return c.getNumeros().getNro1() + ", " + c.getNumeros().getNro2();
            case 3:
                return c.getCobrador().getNombre();
            case 4:
                return c.getCantidadCuotas();
            case 5:
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
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            case 5:
                return Double.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(VentaCampEco propiedad) {
        listVentaCampEcos.add(propiedad);

        this.fireTableRowsInserted(listVentaCampEcos.size() - 1, listVentaCampEcos.size() - 1);
    }

    public void eliminar(int row) {
        listVentaCampEcos.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, VentaCampEco propiedad) {
        listVentaCampEcos.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public VentaCampEco getCliente(int row) {
        return listVentaCampEcos.get(row);
    }

}
