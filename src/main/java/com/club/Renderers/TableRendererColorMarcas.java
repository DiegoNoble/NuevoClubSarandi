/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.Renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class TableRendererColorMarcas extends DefaultTableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(
                table, value, selected, focused, row, column);
        //setBackground(Color.white);//color de fondo
        table.setForeground(Color.black);//color de texto
        //Si la celda corresponde a una fila con estado FALSE, se cambia el color de fondo a rojo
        Integer valor = (Integer) table.getValueAt(row, 4);
        Color background = null;
        if (valor == 1) {
            background = Color.RED;
        }
        renderer.setBackground(background);
        return renderer;
    }

}
