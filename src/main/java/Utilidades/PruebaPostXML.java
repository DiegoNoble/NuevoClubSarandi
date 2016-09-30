/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.input.SAXBuilder;

public class PruebaPostXML {

    String error;

    public PruebaPostXML() {
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
        post = new PostMethod("http://192.185.112.100/~saltohoteluy/webserviceprueba.php");

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair[] parametersList = new NameValuePair[1];

        parametersList[0] = new NameValuePair("accion", "consulta");

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

            /*for (Element hijo : hijoRaiz) {
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

             }*/
            retorno = error;
        } catch (Exception e) {
            retorno = error;
            return retorno;
        } finally {

            post.releaseConnection();

        }
        if (httpstatus != 200) {
            System.out.println(error);

        }
        return retorno;

    }

    public static void main(String[] args) {
        try {
            PruebaPostXML cobrosYa = new PruebaPostXML();

            System.out.println(cobrosYa.enviarTalonMiWeb());
        } catch (Exception ex) {
            System.out.println(ex);
            Logger.getLogger(PruebaPostXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
