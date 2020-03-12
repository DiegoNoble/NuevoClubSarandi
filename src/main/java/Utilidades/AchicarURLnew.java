/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;

/*
 *
 * @author dnoble
 */
public class AchicarURLnew {

    /* public String bitly(String longUrl) {

        BitlyClient client = new BitlyClient("514c4c837c6a4a89f1920ecf22345adbb2915e83");
        Response<ShortenResponse> resp = client.shorten()
                .setLongUrl(longUrl).call();
        if (resp.status_code != 0) {
            return resp.data.url;
        }
        return resp.data.toString();

    }*/
    public void tinyUrl() {

        /*  try {
            String longURL = "www.riveracasinoresort.com";
            String encodedUrl = URLEncoder.encode(longURL, "UTF-8");
            String url = "https://is.gd/create.php?format=simple&url=" + encodedUrl;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            //con.setRequestProperty("encode", "UTF-8");
            /*con.setRequestProperty("end", "1430297279988");
            con.setRequestProperty("id", "140621");
            con.setRequestProperty("time", "FIFTEEN_MINUTE");*/
 /*int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } catch (Exception ex) {
            Logger.getLogger(AchicarURLnew.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    /*void igbdd() {
        try {

            /*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("format", "simple"));
            formparams.add(new BasicNameValuePair("url", "www.riveracasinoresort.com"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            HttpPost httppost = new HttpPost("http://is.gd/create.php");
            httppost.setEntity(entity);*/
 /* String postURL = "http://is.gd/create.php";

            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("format", "simple"));
            params.add(new BasicNameValuePair("url", "www.riveracasinoresort.com"));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(ent);

            HttpClient client = new DefaultHttpClient();
            HttpResponse responsePOST = client.execute(post);

            BufferedReader reader = new BufferedReader(new InputStreamReader(responsePOST.getEntity().getContent()), 2048);

            if (responsePOST != null) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(" line : " + line);
                    sb.append(line);
                }
                String getResponseString = "";
                getResponseString = sb.toString();
//use server output getResponseString as string value.
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
 /*public String prueba(String urlLargo) {
        String retorno = null;
        HttpClient client = new HttpClient();

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        PostMethod post = null;

        post = new PostMethod("https://tinyurl.com/create.php");

        post.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");

        NameValuePair[] parametersList = new NameValuePair[1];

        parametersList[0] = new NameValuePair("url", urlLargo);

        post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();
            int index = response.indexOf("id=\"success");
            retorno = response.substring(index + 40, index + 68);

        } catch (IOException ex) {
            Logger.getLogger(AchicarURLnew.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            post.releaseConnection();

        }
        return retorno;

    }*/
    public String prueba(String urlLargo) {
        String retorno = null;
        HttpClient client = new HttpClient();

        client.setStrictMode(true);
        client.setTimeout(60000);
        client.setConnectionTimeout(5000);
        PostMethod post = null;

        post = new PostMethod("https://api.rebrandly.com/v1/links");

        post.setRequestHeader("Content-type", "application/json; charset=UTF-8");
        post.setRequestHeader("apikey", "72f31ec659864094a267653daa663bbc");

       post.setParameter("destination", urlLargo);

        //NameValuePair[] parametersList = new NameValuePair[1];

        //parametersList[0] = new NameValuePair("destination", urlLargo);
        //parametersList[1] = new NameValuePair("apikey", "72f31ec659864094a267653daa663bbc");
        //parametersList[2] = new NameValuePair("workspace", "Main Workspace");

        //post.setRequestBody(parametersList);
        int httpstatus = 0;
        String response = null;

        try {
//Se envıa la peticion
            httpstatus = client.executeMethod(post);
            System.out.println(httpstatus);
//Se consigue la respuesta
            response = post.getResponseBodyAsString();
            retorno = response.toString();

        } catch (IOException ex) {
            Logger.getLogger(AchicarURLnew.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            post.releaseConnection();

        }
        return retorno;

    }

    void leerString() {

        System.out.println("\n " + prueba("https://www.facebook.com"));

    }

    public static void main(String[] args) {
        new AchicarURLnew().leerString();

    }
}
