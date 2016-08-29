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

public class ConsultaFechaServidor {
 ParametrosDAO parametrosDAO;
    public void  ConsultaFechaServidor() {

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
        post = new PostMethod("http://servicio.smsmasivos.com.ar/get_fecha.asp?iso=1");
//Se fija la codificaci ́on de caracteres en la cabecera de la petici ́on

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

//Se crea la lista de par ́ametros a enviar en la petici ́on POST
        NameValuePair[] parametersList = new NameValuePair[2];
//XX, YY y ZZ se corresponden con los valores de identificaci ́on del
//usuario en el sistema.
        parametrosDAO = new ParametrosDAO();
        Parametros parametros = (Parametros) parametrosDAO.BuscaPorID(Parametros.class, 1);
        String usuario = parametros.getUsuario_SMS();
        String clave = parametros.getPsw_SMS();
        
        parametersList[0] = new NameValuePair("USUARIO", usuario);
        parametersList[1] = new NameValuePair("CLAVE", clave);
        
//Se rellena el cuerpo de la peticion POST con los parametros
        
        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();
            
        } catch (Exception e) {
            System.out.println(e);
            //Habra que prever la captura de excepciones
            return;
            
        } finally {
//En cualquier caso se cierra la conexi ́on
            post.releaseConnection();
        }
//Habr ́a que prever posibles errores en la respuesta del servidor
        if (httpstatus != 200) {
            
            return;
            
        } else {
//Se procesa la respuesta capturada en la cadena ‘‘response’’
            System.out.println(response);
        }
        
    }
    public static void main(String[] args) {
        ConsultaFechaServidor c = new ConsultaFechaServidor();
        c.ConsultaFechaServidor();
    }
}

