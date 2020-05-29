package com.example.gestionpharmacie;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);




            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {


                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                    StringBuilder rsp = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        rsp.append(responseLine.trim());
                    }

                response = rsp.toString();

                } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

/*

    @Override
    protected String doInBackground(String... args) {
        HashMap<String, String> postDataParams;
        String action= "scanner";
        String simage=args[0];
        String sville = args[1];
        String response="";
        HashMap<String, String> postDataParams;

        try {
            URL url =new URL ("http://192.168.1.162/android/pharmacie/testws.php");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json; utf-8");
            con.setRequestProperty("Accept","application/json");
            con.setDoOutput(true);
           // String postDataParams = "{\"action\": \""+action+"\",\"image\":\""+simage+"\", \"ville\": \""+sville+"\"}";


            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            int responseCode=con.getResponseCode();
                if(responseCode==HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    response=br.readLine();
                }
                else
                    {
                    response="Error Registering";
                }
            }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

*/

}