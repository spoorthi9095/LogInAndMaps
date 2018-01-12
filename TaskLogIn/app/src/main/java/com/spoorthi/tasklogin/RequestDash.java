package com.spoorthi.tasklogin;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Spoorthi on 1/12/2018.
 */

public class RequestDash extends AsyncTask<String,Void,String>
{
    public Context context;

    static int responseCode;

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.
        try {

            return String.valueOf(downloadUrl(urls[0],urls[1]));
        } catch (IOException e) {
            return "Unable to download the requested page.";
        }
    }

    private String downloadUrl(String urlString,String jstr) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            //Write
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jstr);
            writer.close();
            outputStream.close();

            responseCode = conn.getResponseCode();

            is = conn.getInputStream();
            String contentAsString = convertStreamToString(is);
//            Log.e("res=",""+contentAsString);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static int getResponse(){
        return  responseCode;
    }}
