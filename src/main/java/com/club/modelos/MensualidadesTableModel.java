/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.modelos;

import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diego Noble
 */
public class MensualidadesTableModel extends AbstractTableModel {

    //nome da coluna da table
    private final String[] colunas = new String[]{" ", "Socio", "Recibo", "Talón CobrosYa", "Enviado", "Situación", "Vencimiento", "Pago", "Valor"};
    //lista para a manipulacao do objeto
    private List<Mensualidades> listMensualidades;

    public MensualidadesTableModel() {
        listMensualidades = new LinkedList<Mensualidades>();
    }

    public MensualidadesTableModel(List<Mensualidades> listMensualidades) {
        this.listMensualidades = listMensualidades;
    }

    //numero de linhas
    @Override
    public int getRowCount() {
        return listMensualidades.size();
    }

    //numero de colunas
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    //define o que cada coluna conterÃ¯Â¿Â½ do objeto
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mensualidades c = listMensualidades.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return c.getSocio();
            case 2:
                return c.getId();
            case 3:
                return c.getNroTalonCobrosYa();
            case 4:
                return c.getEnviado();
            case 5:
                return c.getSituacionTalonCobrosYa();
            case 6:
                return c.getFechaVencimiento();
            case 7:
                return c.getFechaPago();
            case 8:
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

    //determina que tipo de objeto cada coluna irÃ¯Â¿Â½ suportar
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Socio.class;
            case 2:
                return Integer.class;
            case 3:
                return String.class;
            case 4:
                return Boolean.class;
            case 5:
                return String.class;
            case 6:
                return Date.class;
            case 7:
                return Date.class;
            case 8:
                return Double.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;

    }

    public void agregar(Mensualidades propiedad) {
        listMensualidades.add(propiedad);

        this.fireTableRowsInserted(listMensualidades.size() - 1, listMensualidades.size() - 1);
    }

    public void agregar(List<Mensualidades> mensualidades) {
        listMensualidades.addAll(mensualidades);

        this.fireTableDataChanged();
    }

    public void eliminar(int row) {
        listMensualidades.remove(row);
        this.fireTableRowsDeleted(row, row);
    }

    public void atualizar(int row, Mensualidades propiedad) {
        listMensualidades.set(row, propiedad);
        this.fireTableRowsUpdated(row, row);
    }

    public Mensualidades getCliente(int row) {
        return listMensualidades.get(row);
    }

}
