/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.Renderers;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Diego Noble
 */
public class TableRendererColor extends DefaultTableCellRenderer {

    private int ColReferencia;

    public TableRendererColor(int ColReferencia) {
        this.ColReferencia = ColReferencia;
        setHorizontalAlignment(CENTER);
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            Date formatoRecibido = (Date) value;
            String toReturn = new SimpleDateFormat("dd/MM/yyyy").format(formatoRecibido);
            super.setValue(toReturn);
            setForeground(Color.BLUE);
        } else {
            setForeground(Color.red);
            setBorder(new LineBorder(Color.red));
            super.setValue("--");
        }
    }

}
