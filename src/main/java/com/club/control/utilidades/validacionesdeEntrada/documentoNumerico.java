package com.club.control.utilidades.validacionesdeEntrada;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class documentoNumerico extends tamanhodelDocumento {
    public documentoNumerico(int maxlen) {
        super(maxlen);
    }

  
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null)
            return;

        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return;
        }

        super.insertString(offset, str, attr);
    }
}