/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import com.club.BEANS.Mensualidades;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.jdom.input.SAXBuilder;

public class ConsultaTalonesCobrosYa {

    String response;
    List<Mensualidades> mensualidadesPagas = new ArrayList<>();

    public ConsultaTalonesCobrosYa() {
    }

    public List<Mensualidades> enviarTalonMiWeb(Date fecha) throws Exception {
        HttpClient client = new HttpClient();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        PostMethod post = null;

        //post = new PostMethod("http://192.185.112.100/~saltohoteluy/nuevo.php");
        post = new PostMethod("http://192.185.112.100/~saltohoteluy/webserviceprueba.php");

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair[] parametersList = new NameValuePair[2];

        parametersList[0] = new NameValuePair("accion", "consulta");
        parametersList[1] = new NameValuePair("fecha", formatoBD.format(fecha));

        post.setRequestBody(parametersList);
        int httpstatus = 0;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();

            //DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(new StringReader(response));

            Element rootNode = document.getRootElement();

            //Tag <Talones>
            List<Element> hijoRaiz = rootNode.getChildren();

            //Recorre cada <Talon>
            for (Element hijo : hijoRaiz) {
                Mensualidades talonCobrosYaPago = new Mensualidades();
                List<Element> talones = hijo.getContent();
                //Reorre cada tag hijo de <Talon>

                for (Element tags : talones) {

                    if (tags.getName().equals("transaccion")) {
                        talonCobrosYaPago.setId(Integer.valueOf(tags.getValue()));
                    }
                    if (tags.getName().equals("medioPagoId")) {
                        if (!tags.getValue().equals("")) {
                            talonCobrosYaPago.setMedioPagoId(Integer.valueOf(tags.getValue()));
                        }
                    }
                    if (tags.getName().equals("medioPago")) {
                        talonCobrosYaPago.setMedioPago(tags.getValue());
                    }
                    if (tags.getName().equals("idSecreto")) {
                        talonCobrosYaPago.setIdSecreto(tags.getValue());
                    }
                    if (tags.getName().equals("nroTalon")) {
                        talonCobrosYaPago.setNroTalonCobrosYa(tags.getValue());
                    }
                    if (tags.getName().equals("fechaHoraPago")) {

                        if (!tags.getValue().equals("")) {
                            talonCobrosYaPago.setFechaHoraTransaccionCobrosYa(formato.parse(tags.getValue()));
                        }
                    }
                    if (tags.getName().equals("paga")) {
                        talonCobrosYaPago.setPago(tags.getValue());
                    }

                }
                mensualidadesPagas.add(talonCobrosYaPago);
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {

            post.releaseConnection();

        }
        if (httpstatus
                != 200) {
            System.out.println(response);

        }
        return mensualidadesPagas;

    }

    /*public static void main(String[] args) {
     try {
     ConsultaTalonesCobrosYa cobrosYa = new ConsultaTalonesCobrosYa();

     System.out.println(cobrosYa.enviarTalonMiWeb());
     } catch (Exception ex) {
     System.out.println(ex);
     Logger.getLogger(ConsultaTalonesCobrosYa.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
