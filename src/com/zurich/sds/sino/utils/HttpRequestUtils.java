package com.zurich.sds.sino.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.net.MalformedURLException;

/**
 *
 * @author candy.chiu
 */
public class HttpRequestUtils {

    public String httpPostRequest(String link, String data)
            throws MalformedURLException {

        HttpsURLConnection connection1 = null;
        HttpURLConnection connection2 = null;
        URL url = new URL(link);
        String result = "";
        BufferedReader in;
        String encoding = "utf-8";



        try {
//            data = "applyauthinfo=" + data + "&kwersd="+kwersd;
            data = new String(data.getBytes(encoding),
                    encoding);



            if (link.indexOf("s") == 4) {
                connection1 = (HttpsURLConnection) url.openConnection();
                connection1.setHostnameVerifier(new HostnameVerifier() {

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }

                });

                connection1.setRequestMethod("POST");
                connection1.setDoOutput(true);
                connection1.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                PrintStream out = new PrintStream(connection1.getOutputStream());

                out.print(data);
                out.close();

                in = new BufferedReader(new InputStreamReader(connection1.getInputStream(), encoding));

            } else {

                connection2 = (HttpURLConnection) url.openConnection();

                connection2.setRequestMethod("POST");
                connection2.setDoOutput(true);
                connection2.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                PrintStream out = new PrintStream(connection2.getOutputStream());

                out.print(data);
                out.close();

                in = new BufferedReader(new InputStreamReader(connection2.getInputStream(), encoding));

            }

            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }

            in.close();


        } catch (ProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection1 != null) {
                connection1.disconnect();
            }
            if (connection2 != null) {
                connection2.disconnect();
            }
            return result;
        }

    }

}
