/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.club.BEANS.Mensualidades;
import com.club.BEANS.Parametros;
import com.club.BEANS.Socio;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.jdom.input.SAXBuilder;

public class EnvioTalonCobrosYa {

    String nroTalonCobrosYa = null;
    String idSecretoCobrosYa = null;
    String url_pdf = null;
    String situacionTransaccion = null;

    public void prueba() {

        HttpClient client = new HttpClient();

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        PostMethod post = null;

        post = new PostMethod("http://192.185.112.100/~saltohoteluy/webserviceprueba.php");

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair[] parametersList = new NameValuePair[1];

        /*parametersList[0] = new NameValuePair("id_secreto", "3uRAWi1UY6jlJJYY0LHEI0uMc3UcS95j");
         parametersList[1] = new NameValuePair("id_medio_pago", "6");
         parametersList[2] = new NameValuePair("medio_pago", "Banred");
         parametersList[3] = new NameValuePair("moneda", "858");
         parametersList[4] = new NameValuePair("monto", "50");
         SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd H:M:S");
         parametersList[5] = new NameValuePair("fecha_hora_pago", formatoFecha.format(new Date()));
         parametersList[6] = new NameValuePair("cuotas_codigo", "1");
         parametersList[7] = new NameValuePair("cuotas_texto", "pruieba");
         parametersList[8] = new NameValuePair("autorizacion", "1");*/
        parametersList[0] = new NameValuePair("accion", "consulta");

        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
            httpstatus = client.executeMethod(post);
            response = post.getResponseBodyAsString();

            System.out.println(response);

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new StringReader(response));
            Element rootNode = document.getRootElement();

            List<Element> hijoRaiz = rootNode.getChildren();

            /*for (Element hijo : hijoRaiz) {
                if (hijo.getName().equals("transaccion")) {
                    String transaccion = hijo.getValue();
                    System.out.println(transaccion);
                }
                /*if (hijo.getName().equals("nro_talon")) {
                 nroTalonCobrosYa = hijo.getValue();
                 }
                 if (hijo.getName().equals("id_secreto")) {
                 idSecretoCobrosYa = hijo.getValue();
                 }
                 if (hijo.getName().equals("url_pdf")) {
                 url_pdf = hijo.getValue();
                 }*/

            //}

        } catch (Exception e) {
            System.out.println(e);

        } finally {
            //En cualquier caso se cierra la conexi ́on
            post.releaseConnection();
        }
        //Habra que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {
            JOptionPane.showMessageDialog(null, "Error al enviar talón: " + response, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(response);

        }

    }

    public void enviarTalonMiWeb(Parametros parametros, Socio socio, Mensualidades mensualidad) throws Exception {

        //Se inicia el objeto HTTP
        HttpClient client = new HttpClient();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        client.setStrictMode(true);
//Se fija el tiempo m ́aximo de espera de la respuesta del servidor
        client.setTimeout(60000);
//Se fija el tiempo m ́aximo de espera para conectar con el servidor
        client.setConnectionTimeout(5000);
        PostMethod post = null;
//Se fija la URL sobre la que enviar la petici ́on POST
//Como ejemplo la petici ́on se env ́ıa a www.altiria.net/sustituirPOSTsms
//Se debe reemplazar la cadena ’/sustituirPOSTsms’ por la parte correspondiente
//de la URL suministrada por Altiria al dar de alta el servicio
        //post = new PostMethod("http://api-sandbox.cobrosya.com/v4/crear");
        post = new PostMethod("http://192.185.112.100/~saltohoteluy/nuevo.php");
//Se fija la codificaci ́on de caracteres en la cabecera de la petici ́on

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

//Se crea la lista de parámetros a enviar en la petición POST
        NameValuePair[] parametersList = new NameValuePair[13];

//token String32 Token de la API asignado a su cuenta
        //parametersList[0] = new NameValuePair("token", parametros.getTokenCobrosYa());
        parametersList[0] = new NameValuePair("token", "a1ecdd664e419ee9e77e19e409242029");
//id_transaccion String50 Identificador único de la transaccion en su sistema
        parametersList[1] = new NameValuePair("id_transaccion", mensualidad.getId().toString());
//nombre String50 Nombre de la persona que va a efectuar el pago
        parametersList[2] = new NameValuePair("nombre", socio.getNombre());
//apellido String50 Apellido de la persona que va a efectuar el pago
        parametersList[3] = new NameValuePair("apellido", "");
//email String50 Email de la persona que va a efectuar el pago
        parametersList[4] = new NameValuePair("email", socio.getEmail());
//celular String7 Celular de la persona que va a efectuar el pago. OPCIONAL
        parametersList[5] = new NameValuePair("celular", socio.getCelular());
//concepto String200 Descripcion para el talón PDF. OPCIONAL
        parametersList[6] = new NameValuePair("concepto", "Cuota social Sarandí Universitario " + mensualidad.getId());
//moneda Numerico3 858=pesos, 840=dólares
        parametersList[7] = new NameValuePair("moneda", "858");
//monto Numerico10.2 Mondo de la transacción
        parametersList[8] = new NameValuePair("monto", mensualidad.getValor().toString());
//fecha_vencimineto Fecha YYYY-MM-DD Vencimiento del pago en redes de Cobranza. OPCIONAL
        parametersList[9] = new NameValuePair("fecha_vencimiento", formatoFecha.format(mensualidad.getFechaVencimiento()));
//url_respuesta String200 URL para redirigir al usuario al finalizar la transacción
        parametersList[10] = new NameValuePair("url_respuesta", "http://www.sarandiuniversitario.com/");
//consumo_final Numerico1 1=consumidor final, 0=venta con rut. Para ley de inclusión
        parametersList[11] = new NameValuePair("consumo_final", "1");

//factura String20 Numero de factura de la transacción para ley de inclusión
//monto_gravado Numerico10.2 Monto gravado con IVa para la leu de inclusión financiera
        //parametersList[12] = new NameValuePair("medioPago", "6");
        //URL para produccion
        //parametersList[12] = new NameValuePair("API_URL_CREAR", "https://api.cobrosya.com/v4/crear");
        //URL para pruebas
        parametersList[12] = new NameValuePair("API_URL_CREAR", "http://api-sandbox.cobrosya.com/v4/crear");
//Se rellena el cuerpo de la peticion POST con los parametros
        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();

            //DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new StringReader(response));
            Element rootNode = document.getRootElement();

            List<Element> hijoRaiz = rootNode.getChildren();
            String error = null;

            for (Element hijo : hijoRaiz) {
                if (hijo.getName().equals("error")) {
                    error = hijo.getValue();
                }
                if (hijo.getName().equals("nro_talon")) {
                    nroTalonCobrosYa = hijo.getValue();
                }
                if (hijo.getName().equals("id_secreto")) {
                    idSecretoCobrosYa = hijo.getValue();
                }
                if (hijo.getName().equals("url_pdf")) {
                    url_pdf = hijo.getValue();
                }

            }

            switch (error) {
                case "0":
                    situacionTransaccion = "Transacción iniciada correctamente";
                    break;
                case "1":
                    situacionTransaccion = "Falta campos";
                    break;
                case "2":
                    situacionTransaccion = "El token no es correcto";
                    break;
                case "3":
                    situacionTransaccion = "Error al crear talón";
                    break;
                case "4":
                    situacionTransaccion = "La fecha de vencimiento es incorrecta";
                    break;
                case "5":
                    situacionTransaccion = "El celular tiene un formato incorrecto (expresión regular para validar: /^09[0-9]{7}$/ )";
                    break;
                case "6":
                    situacionTransaccion = "El mail tiene un formato incorrecto";
                    break;
                case "7":
                    situacionTransaccion = "La moneda no es valida";
                    break;
                case "8":
                    situacionTransaccion = "El monto tiene un formato incorrecto";
                    break;
                case "9":
                    situacionTransaccion = "La transacción ya fue cobrada";
                    break;
                default:
                    throw new AssertionError();

            }

        } catch (Exception e) {
            System.out.println(response);
            //JOptionPane.showMessageDialog(null, "Error " + response, "Error", JOptionPane.ERROR_MESSAGE);
            throw new Exception(response);
        } finally {
//En cualquier caso se cierra la conexi ́on
            post.releaseConnection();
        }
//Habra que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {
            JOptionPane.showMessageDialog(null, "Error al enviar talón: " + response, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(response);

        }

    }

    public String getNroTalonCobrosYa() {
        return nroTalonCobrosYa;
    }

    public void setNroTalonCobrosYa(String nroTalonCobrosYa) {
        this.nroTalonCobrosYa = nroTalonCobrosYa;
    }

    public String getIdSecretoCobrosYa() {
        return idSecretoCobrosYa;
    }

    public void setIdSecretoCobrosYa(String idSecretoCobrosYa) {
        this.idSecretoCobrosYa = idSecretoCobrosYa;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }

    public void setUrl_pdf(String url_pdf) {
        this.url_pdf = url_pdf;
    }

    public String getSituacionTransaccion() {
        return situacionTransaccion;
    }

    public void setSituacionTransaccion(String situacionTransaccion) {
        this.situacionTransaccion = situacionTransaccion;
    }

    public static void main(String[] args) {
        EnvioTalonCobrosYa cobrosYa = new EnvioTalonCobrosYa();
        cobrosYa.prueba();
    }
}
