/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.tableModels;

import com.club.BEANS.EntregaPremio;
import com.club.BEANS.Numeros;
import com.club.BEANS.Premio;
import com.club.BEANS.Socio;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class PremiosEntregadosTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{"Fecha", "Premio", "Socio", "Bonos"};
    //lista para a manipulacao do objeto
    private List<EntregaPremio> listEntregaPremios;

    public PremiosEntregadosTableModel() {
        listEntregaPremios = new LinkedList<EntregaPremio>();
    }

    public PremiosEntregadosTableModel(List<EntregaPremio> listEntregaPremios) {
        this.listEntregaPremios = listEntregaPremios;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listEntregaPremios.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterï¿½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EntregaPremio c = listEntregaPremios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return c.getFecha();
            case 1:
                return c.getPremio();
            case 2:
                return c.getVentaCampEco().getSocio();
            case 3:
                return c.getVentaCampEco().getNumeros();
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
                return Premio.class;
            case 2:
                return Socio.class;
            case 3:
                return Numeros.class;

            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(EntregaPremio propiedad) {
        listEntregaPremios.add(propiedad);

        this.fireTableRowsInserted(listEntregaPremios.size() - 1, listEntregaPremios.size() - 1);
    }

    public void eliminar(int row) {
        listEntregaPremios.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, EntregaPremio propiedad) {
        listEntregaPremios.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public EntregaPremio getCliente(int row) {
        return listEntregaPremios.get(row);
    }

}
