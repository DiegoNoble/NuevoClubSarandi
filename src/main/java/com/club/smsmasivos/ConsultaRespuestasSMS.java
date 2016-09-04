/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import com.club.BEANS.Parametros;
import com.club.DAOs.ParametrosDAO;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;

public class ConsultaRespuestasSMS {

    ParametrosDAO parametrosDAO;

    public void ConsultaRespuestas() {

        HttpClient client = new HttpClient();

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        GetMethod get = null;

        get = new GetMethod("http://servicio.smsmasivos.com.ar/obtener_sms_entrada.asp?");
        get.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        GetMethod method = new GetMethod("example.com/page");
        method.setQueryString(new NameValuePair[]{
            new NameValuePair("key", "value")
        });
        NameValuePair[] parametersList = new NameValuePair[3];

        parametrosDAO = new ParametrosDAO();
        Parametros parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
        String usuario = parametros.getUsuario_SMS();
        String clave = parametros.getPsw_SMS();

        parametersList[0] = new NameValuePair("USUARIO", usuario);
        parametersList[1] = new NameValuePair("CLAVE", clave);
        parametersList[2] = new NameValuePair("TRAERIDINTERNO", "1");
        /*parametersList[3] = new NameValuePair("ORIGEN", "91390000");
         parametersList[4] = new NameValuePair("SOLONOLEIDOS", "1");
         parametersList[5] = new NameValuePair("MARCARCOMOLEIDOS", "0");*/
        get.setQueryString(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(get);
//Se consigue la respuesta
            response = get.getResponseBodyAsString();

        } catch (Exception e) {
            System.out.println(e);
            //Habra que prever la captura de excepciones
            return;

        } finally {
//En cualquier caso se cierra la conexi ́on
            get.releaseConnection();
        }
//Habr ́a que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {

            return;

        } else {
//Se procesa la respuesta capturada en la cadena ‘‘response’’
            System.out.println(response);

            String[] columnDetail = new String[11];
            columnDetail = response.split(System.lineSeparator());
            for (String detalle : columnDetail) {
                String[] camposIndividuales = new String[110];
                camposIndividuales = detalle.split("\t");
                for (String individuale : camposIndividuales) {
                    System.out.println(individuale);
                    
                }
            }
        }

    }

    public static void main(String[] args) {
        ConsultaRespuestasSMS c = new ConsultaRespuestasSMS();
        c.ConsultaRespuestas();
    }
}
