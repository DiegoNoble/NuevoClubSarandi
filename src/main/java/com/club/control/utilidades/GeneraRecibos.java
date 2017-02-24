/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.control.utilidades;

import com.club.BEANS.Categoria;
import com.club.BEANS.CcCobrador;
import com.club.BEANS.Cobrador;
import com.club.BEANS.Dependiente;
import com.club.BEANS.Mensualidades;
import com.club.BEANS.Socio;
import com.club.DAOs.CategoriaDAO;
import com.club.DAOs.CcCobradorDAO;
import com.club.DAOs.CobradorDAO;
import com.club.DAOs.DepDAO;
import com.club.DAOs.MensualidadesDAO;
import com.club.DAOs.SocioDAO;
import com.club.views.FormMensualidadesNew;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Diego
 */
public class GeneraRecibos {

    private MensualidadesDAO mensualidadesDAO;
    private SocioDAO socioDAO;
    private DepDAO depDAO;
    private Mensualidades mensualidad;
    private List<Socio> listSocios;
    String fecha;
    SimpleDateFormat formato;
    String inicio;
    String fin;
    Cobrador cobrador;
    Categoria categoria;
    JTextArea txtLog;
    private CcCobrador debito;
    private CcCobradorDAO ccCobradorDAO;
    String Msj;

    public GeneraRecibos(String inicio, String fin, Cobrador cobrador, Categoria categoria, JTextArea txtLog, String fecha, String Msj) {
        this.categoria = categoria;
        this.cobrador = cobrador;
        this.fin = fin;
        this.inicio = inicio;
        this.txtLog = txtLog;
        this.fecha = fecha;
        this.Msj = Msj;
    }

    public void EmisionMensualidades() {

        mensualidadesDAO = new MensualidadesDAO();
        int emision = mensualidadesDAO.NroLanzamiento();
        txtLog.append("\n Emisión nro: " + emision);
        //txtLog.setCaretPosition(txtLog.getDocument().getLength());

        try {
            socioDAO = new SocioDAO();
            listSocios = socioDAO.BuscaSociosHabilitadosParaMensualidades(cobrador, categoria,
                    inicio, fin);
            txtLog.append("\n Cobrador: " + cobrador.toString());
            txtLog.append("\n Categoría: " + categoria.toString());
            txtLog.append("\n Se procesaran " + listSocios.size() + " recibos");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());

            SimpleDateFormat formatoBD = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaMensualidad = formatoBD.parse(fecha);
            txtLog.append("\n Fecha vencimientos: " + fecha);

            for (Socio socio : listSocios) {

                mensualidadesDAO = new MensualidadesDAO();
                if (mensualidadesDAO.VerificaSiYaFueEmitida(socio, fechaMensualidad) == true) {

                    mensualidadesDAO = new MensualidadesDAO();
                    if (mensualidadesDAO.VerificaCantidadVencimientos(socio) == true) {

                        //Cobrador cobrador = socio.getCobrador();
                        Double valorMensualidad = socio.getCategoria().getMensualidad();

                        mensualidad = new Mensualidades();
                        mensualidad.setCobrador(cobrador);
                        mensualidad.setLanzamiento(emision);
                        mensualidad.setPago("Pendiente de Pago");
                        mensualidad.setSocio(socio);
                        mensualidad.setValor(valorMensualidad);
                        mensualidad.setFechaVencimiento(fechaMensualidad);

                        mensualidadesDAO = new MensualidadesDAO();
                        mensualidadesDAO.Salvar(mensualidad);

                        registraEnCcCobrador(cobrador, mensualidad);
                        txtLog.append("\n Recibo nro.: " + mensualidad.getId() + ", socio: " + socio.toString() + " generado correctamente");
                        //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                    } else {
                        //Inactiva Socio y sus dependientes caso tenga más de 3 vencimientos pendientes
                        socio.setSituacion("Inactivo");
                        socioDAO = new SocioDAO();
                        socioDAO.Actualizar(socio);

                        txtLog.append("\n Socio: " + socio.toString() + " posee 2 o mas recibos pendientes!");
                        txtLog.append("\n Pasa a situación INACTIVA!");
                        //txtLog.setCaretPosition(txtLog.getDocument().getLength());

                        depDAO = new DepDAO();
                        List<Dependiente> listDependientes = depDAO.BuscaPorCodigodeSocio(socio);
                        for (Dependiente dep : listDependientes) {
                            dep.setSituacion("Inactivo");
                            depDAO = new DepDAO();
                            depDAO.Actualizar(dep);
                            txtLog.append("\n Dependiente: " + dep.toString() + " pasa a situación INACTIVA!");
                            //txtLog.setCaretPosition(txtLog.getDocument().getLength());
                        }

                    }
                }

            }
            txtLog.append("\n Emisión finalizada!");
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
            guardaLog(emision);
            /*HashMap parametros = new HashMap();
            parametros.clear();
            parametros.put("Msj", txtAreaMsj.getText());
            parametros.put("emision", emision);*/
            //btnRecibos.setReportParameters(parametros);
            //btnRecibos.setReportURL("/Reportes/recibos.jasper");

            try {
                InputStream resource = getClass().getClassLoader().getResourceAsStream("Reportes/recibos.jasper");
                HashMap parametros = new HashMap();
                parametros.clear();
                parametros.put("Msj", Msj);
                parametros.put("emision", emision);
                JasperPrint jasperPrint = JasperFillManager.fillReport(resource, parametros, new JREmptyDataSource());
                JasperViewer reporte = new JasperViewer(jasperPrint, false);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al imprimir recibo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            txtLog.append("\n Error al generar mensualidades!");
            txtLog.append(ex.toString());
            txtLog.setCaretPosition(txtLog.getDocument().getLength());
            guardaLogError();

        }
    }

    private void registraEnCcCobrador(Cobrador cobrador, Mensualidades mensualidades) {
        try {
            debito = new CcCobrador();
            debito.setCobrador(cobrador);
            debito.setFechaMovimiento(new Date());
            debito.setDescripcion("Recibo Nro: " + mensualidades.getId());
            debito.setDebito(mensualidades.getValor());
            debito.setCredito(0.0);
            ccCobradorDAO = new CcCobradorDAO();
            ccCobradorDAO.Salvar(debito);
        } catch (Exception ex) {
            Logger.getLogger(FormMensualidadesNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void guardaLog(int emision) throws IOException {

        File archivo = new File("logs/Emisión" + emision + ".txt");
        if (!archivo.exists()) {
            archivo.mkdirs();
        }
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(txtLog.getText());
        bw.close();
    }

    void guardaLogError() {

        try {
            File archivo = new File("logs/Error emisión.txt");
            if (!archivo.exists()) {
                archivo.mkdirs();
            }
            BufferedWriter bw;

            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(txtLog.getText());
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FormMensualidadesNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
