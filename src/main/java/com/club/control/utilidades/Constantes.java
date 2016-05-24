package com.club.control.utilidades;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class Constantes {

    public void formataciondeCamposFecha(JFormattedTextField jFormattedTextField) {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####"); //macara del campo que será formatado
            //mascara.setValueContainsLiteralCharacters(false); //esta propiedad en false hace que al hacer un getValue ignore los caracteres del formato por ej. los /  /
            DefaultFormatterFactory formato = new DefaultFormatterFactory(mascara); //esta es la propiedad del JfTXT que contiene su formatación padron, indico que será el formato "mascara"
            jFormattedTextField.setFormatterFactory(formato); // seteamos el formato "mascara"
        } catch (ParseException ex) {
            Logger.getLogger(Constantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void formatoCamposNumericos(JFormattedTextField jFormattedTextField) {
        try {
            MaskFormatter mascara = new MaskFormatter("##########");
            DefaultFormatterFactory formato = new DefaultFormatterFactory(mascara);
            jFormattedTextField.setFormatterFactory(formato);
        } catch (ParseException ex) {
            Logger.getLogger(Constantes.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
