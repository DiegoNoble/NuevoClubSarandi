
package com.club.control.utilidades;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;



public class DesktopPaneImagen extends JDesktopPane {

    private String caminodeImagen;

    public DesktopPaneImagen(String caminodeImagen){
        this.caminodeImagen = caminodeImagen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon img = new ImageIcon(caminodeImagen);

        g.drawImage(img.getImage(), 0, 0, this);
    }
    

}
