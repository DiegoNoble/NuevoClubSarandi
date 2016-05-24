
package com.club.control.socios;

import com.club.control.acceso.conexion;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class informeSocios {

    conexion conexionInforme = new conexion();

    public informeSocios(){
        try {
            conexionInforme.conecta();
            conexionInforme.ejecutaSQL("select * from tbsocio");
            JRResultSetDataSource jrRS = new JRResultSetDataSource(conexionInforme.resultSet);
             InputStream resource = getClass().getResourceAsStream("/com/club/control/socios/InformeSocios.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, new HashMap(), jrRS);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException ex) {
            Logger.getLogger(informeSocios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main (String args[]){

        new informeSocios();
    }

}
