/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author dnoble
 */
public class AchicarURL {

    private String googUrl = "https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&key=AIzaSyC4194r9lTCsPoVurluDWK0ho5Eh-NWEQU";

    public String achicar(String UrlCobrosYa) {
        int cantidad = UrlCobrosYa.length();
        String codigoCobrosYa = UrlCobrosYa.substring(cantidad - 40, cantidad);

        String longUrl = "https://api.cobrosya.com/mostrador/cobrar?t=" + codigoCobrosYa;
        String shortUrl = null;

        try {
            URLConnection conn = new URL(googUrl).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr
                    = new OutputStreamWriter(conn.getOutputStream());
            wr.write("{\"longUrl\":\"" + longUrl + "\"}");
            wr.flush();

            // Get the response
            BufferedReader rd
                    = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                if (line.indexOf("id") > -1) {
                    // I'm sure there's a more elegant way of parsing
                    // the JSON response, but this is quick/dirty =)
                    shortUrl = line.substring(8, line.length() - 2);
                    break;
                }
            }

            wr.close();
            rd.close();
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return shortUrl;
    }

}
