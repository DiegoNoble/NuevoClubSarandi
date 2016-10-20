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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom.input.SAXBuilder;

public class PruebaEnvioTalonCobrosYa {

    String nroTalonCobrosYa = null;
    String idSecretoCobrosYa = null;
    String url_pdf = null;
    String situacionTransaccion = null;
    String error;

    public PruebaEnvioTalonCobrosYa() {
    }

    public String enviarTalonMiWeb() throws Exception {
        String retorno = null;
        HttpClient client = new HttpClient();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        PostMethod post = null;

        //post = new PostMethod("http://192.185.112.100/~saltohoteluy/nuevo.php");
        post = new PostMethod("https://api.cobrosya.com/v4/crear");

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair[] parametersList = new NameValuePair[13];

//token String32 Token de la API asignado a su cuenta
        //parametersList[0] = new NameValuePair("token", parametros.getTokenCobrosYa());
        parametersList[0] = new NameValuePair("token", "a1ecdd664e419ee9e77e19e409242029");
//id_transaccion String50 Identificador único de la transaccion en su sistema
        parametersList[1] = new NameValuePair("id_transaccion", "666666");
//nombre String50 Nombre de la persona que va a efectuar el pago
        parametersList[2] = new NameValuePair("nombre", "Prueba");
//apellido String50 Apellido de la persona que va a efectuar el pago
        parametersList[3] = new NameValuePair("apellido", "Monto Vencido");
//email String50 Email de la persona que va a efectuar el pago
        parametersList[4] = new NameValuePair("email", "dnoblemello@gmail.com");
//celular String7 Celular de la persona que va a efectuar el pago. OPCIONAL
        parametersList[5] = new NameValuePair("celular", "091390000");
//concepto String200 Descripcion para el talón PDF. OPCIONAL
        parametersList[6] = new NameValuePair("concepto", "Prueba sin mail");
//moneda Numerico3 858=pesos, 840=dólares
        parametersList[7] = new NameValuePair("moneda", "858");
//monto Numerico10.2 Mondo de la transacción
        parametersList[8] = new NameValuePair("monto", "100");
//fecha_vencimineto Fecha YYYY-MM-DD Vencimiento del pago en redes de Cobranza. OPCIONAL
        parametersList[9] = new NameValuePair("fecha_vencimiento", "2016-10-19");
//url_respuesta String200 URL para redirigir al usuario al finalizar la transacción
        parametersList[10] = new NameValuePair("url_respuesta", "http://www.sarandiuniversitario.com/");
//consumo_final Numerico1 1=consumidor final, 0=venta con rut. Para ley de inclusión
        parametersList[11] = new NameValuePair("consumo_final", "1");
        parametersList[12] = new NameValuePair("monto_vencido", "110");

//factura String20 Numero de factura de la transacción para ley de inclusión
//monto_gravado Numerico10.2 Monto gravado con IVa para la leu de inclusión financiera
        //parametersList[12] = new NameValuePair("medioPago", "6");
        //URL para produccion
        //parametersList[12] = new NameValuePair("API_URL_CREAR", "https://api.cobrosya.com/v4/crear");
        //URL para pruebas
        //parametersList[12] = new NameValuePair("API_URL_CREAR", "http://api-sandbox.cobrosya.com/v4/crear");
        //parametersList[11] = new NameValuePair("API_URL_CREAR", "http://api-sandbox.cobrosya.com/v4/crear");
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
            error = null;

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

            retorno = error(error);
        } catch (Exception e) {
            retorno = error(error);
            return retorno;
        } finally {

            post.releaseConnection();

        }
        if (httpstatus != 200) {
            System.out.println(error);

        }
        return retorno;

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

    String error(String error) {
        String retorno = null;

        switch (error) {
            case "0":
                retorno = "Transacción iniciada correctamente";
                situacionTransaccion = retorno;
                break;
            case "1":
                retorno = "Falta campos";
                situacionTransaccion = retorno;
                break;
            case "2":
                retorno = "El token no es correcto";
                situacionTransaccion = retorno;
                break;
            case "3":
                retorno = "Error al crear talón";
                situacionTransaccion = retorno;
                break;
            case "4":
                retorno = "La fecha de vencimiento es incorrecta";
                situacionTransaccion = retorno;
                break;
            case "5":
                retorno = "El celular tiene un formato incorrecto (expresión regular para validar: /^09[0-9]{7}$/ )";
                situacionTransaccion = retorno;
                break;
            case "6":
                retorno = "El mail tiene un formato incorrecto";
                situacionTransaccion = retorno;
                break;
            case "7":
                retorno = "La moneda no es valida";
                situacionTransaccion = retorno;
                break;
            case "8":
                retorno = "El monto tiene un formato incorrecto";
                situacionTransaccion = retorno;
                break;
            case "9":
                retorno = "La transacción ya fue cobrada";
                situacionTransaccion = retorno;
                break;
            default:
                throw new AssertionError();

        }
        return retorno;
    }
    
    public static void main(String[] args) {
        try {
            PruebaEnvioTalonCobrosYa cobrosYa =new PruebaEnvioTalonCobrosYa();
            
            System.out.println(cobrosYa.enviarTalonMiWeb());
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PruebaEnvioTalonCobrosYa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
