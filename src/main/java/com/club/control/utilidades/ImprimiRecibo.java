/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.control.utilidades;

import com.club.BEANS.Caja;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Diego Noble
 */
public class ImprimiRecibo {

    public void imprimieRecibo(Caja movCaja) {
        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibo_mov_caja.jasper");

            BufferedImage logo = ImageIO.read(getClass().getResource("/Imagenes/Escudo.jpg"));
            BufferedImage logoBN = ImageIO.read(getClass().getResource("/Imagenes/EscudoBN.png"));

            HashMap parametros = new HashMap<>();

            parametros.put("id", movCaja.getId());
            parametros.put("rubro", movCaja.getRubro().getNombreRubro());
            parametros.put("concepto", movCaja.getConcepto());
            parametros.put("sector", movCaja.getSectores().getNombreSector());
            parametros.put("fecha", movCaja.getFechaMovimiento());
            parametros.put("usuario", movCaja.getUsuario());
            if (movCaja.getEntrada() != 0.0) {
                parametros.put("valor", movCaja.getEntrada());
                parametros.put("tipoMovimiento", "ENTRADA");
            } else {
                parametros.put("valor", movCaja.getSalida());
                parametros.put("tipoMovimiento", "SALIDA");
            }

            parametros.put("logo", logo);
            parametros.put("logoBN", logoBN);
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);

            reporte.toFront();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
