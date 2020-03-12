/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.club.smsmasivos;

import com.club.BEANS.Parametros;
import com.club.DAOs.ParametrosDAO;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

public class EnvioSMSIndividual {

    Parametros parametros;

    public String enviarSMSIndividual(String idInterno, String nro, String mensaje, Parametros parametros) {

        this.parametros = parametros;
        //Se inicia el objeto HTTP
        HttpClient client = new HttpClient();

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
        post = new PostMethod("http://servicio.smsmasivos.com.ar/enviar_sms.asp?api=1");
//Se fija la codificaci ́on de caracteres en la cabecera de la petici ́on

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

//Se crea la lista de par ́ametros a enviar en la petici ́on POST
        NameValuePair[] parametersList = new NameValuePair[5];
//XX, YY y ZZ se corresponden con los valores de identificaci ́on del
//usuario en el sistema.
        String usuario = parametros.getUsuario_SMS();
        String clave = parametros.getPsw_SMS();
        parametersList[0] = new NameValuePair("USUARIO", usuario);
        parametersList[1] = new NameValuePair("CLAVE", clave);
        parametersList[2] = new NameValuePair("TOS", nro);
        parametersList[3] = new NameValuePair("TEXTO", mensaje);
        parametersList[4] = new NameValuePair("IDINTERNO", idInterno);
        //parametersList[4] = new NameValuePair("FECHADESDE", "2015-03-26 15:55:00");

//Se rellena el cuerpo de la peticion POST con los parametros
        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();

            System.out.println(response);

        } catch (Exception e) {
            System.out.println(e);
            //Habra que prever la captura de excepciones
            return e.toString();

        } finally {
//En cualquier caso se cierra la conexi ́on
            post.releaseConnection();
        }
//Habr ́a que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {

            return "Tiempo de espera del servidor proveedor agotado, error: " + response;

        } else {
//Se procesa la respuesta capturada en la cadena ‘‘response’’
        }

        return response;

    }

    public String pruebaEnviarSMSIndividual(String idInterno, String nro, String mensaje, Parametros parametros) {
        this.parametros = parametros;
        //Se inicia el objeto HTTP
        HttpClient client = new HttpClient();

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
        post = new PostMethod("http://servicio.smsmasivos.com.ar/enviar_sms.asp?api=1");
//Se fija la codificaci ́on de caracteres en la cabecera de la petici ́on

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

//Se crea la lista de par ́ametros a enviar en la petici ́on POST
        NameValuePair[] parametersList = new NameValuePair[6];
//XX, YY y ZZ se corresponden con los valores de identificaci ́on del
//usuario en el sistema.
        String usuario = parametros.getUsuario_SMS();
        String clave = parametros.getPsw_SMS();
        parametersList[0] = new NameValuePair("USUARIO", usuario);
        parametersList[1] = new NameValuePair("CLAVE", clave);
        parametersList[2] = new NameValuePair("TOS", nro);
        parametersList[3] = new NameValuePair("TEXTO", mensaje);
        parametersList[4] = new NameValuePair("IDINTERNO", idInterno);
        parametersList[5] = new NameValuePair("TEST", "1");
        //parametersList[4] = new NameValuePair("FECHADESDE", "2015-03-26 15:55:00");

//Se rellena el cuerpo de la peticion POST con los parametros
        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();

            System.out.println(response);

        } catch (Exception e) {
            System.out.println(e);
            //Habra que prever la captura de excepciones
            return e.toString();

        } finally {
//En cualquier caso se cierra la conexi ́on
            post.releaseConnection();
        }
//Habr ́a que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {

            return "Tiempo de espera del servidor proveedor agotado, error: " + response;

        } else {
//Se procesa la respuesta capturada en la cadena ‘‘response’’
        }

        return response;

    }

}
