/**
 * MyDateCellRenderer.java
 *
 * $Id$
 *
 */
package com.club.Renderers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Dyego Souza do Carmo
 * @since 
 */
public class MeCalendarCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * Construtor padrão
     *
     * @author Dyego Souza do Carmo
     * @version 1.0, 
     */
    public MeCalendarCellRenderer() {
        super();
        setHorizontalAlignment(CENTER);
    }

    /**
     *
     * @param value
     */
    @Override
    protected void setValue(Object value) {
        if (value != null) {
            // The original Property is not chaged
            // Only the view is converted !
            Calendar valueOfDate = (Calendar) value;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            super.setValue(sdf.format(valueOfDate.getTime()));
        } else {
            super.setValue(value);
        }
    }
}
