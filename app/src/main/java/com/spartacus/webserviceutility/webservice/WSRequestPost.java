package com.spartacus.webserviceutility.webservice;

import android.util.Log;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.HttpsURLConnection;


public class WSRequestPost
{
    private static final String TAG = WSRequestPost.class.getSimpleName();
    private String url;
    private JSONObject jsonData;
    private final Lock lock = new ReentrantLock();

    public WSRequestPost(String url, JSONObject jsonData) {
        this.url = url;
        this.jsonData = jsonData;
    }


    public <Response> Response execute(
            Class<Response> responseType) throws Exception {

        Response response;
        InputStream inputStream;
        HttpURLConnection urlConnection;
        int statusCode = 0;

        Log.e(TAG, "request object : " + jsonData);

        try {

            /* forming th java.net.URL object */
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            /* pass post data */
            byte[] outputBytes = jsonData.toString().getBytes("UTF-8");
            OutputStream os = urlConnection.getOutputStream();
            os.write(outputBytes);
            os.close();

            /* Get Response and execute WebService request*/
            statusCode = urlConnection.getResponseCode();
            InputStream ErrorStream = urlConnection.getErrorStream();


            Tracerror(statusCode,ErrorStream);

            /* 200 represents HTTP OK */
            if (statusCode == HttpsURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String responseString = convertInputStreamToString(inputStream);
                Log.e(TAG, "response object : " + responseString);
                Gson g = new Gson();
                responseString = responseString.replace("{\"d\":null}","");
                responseString = responseString.trim();
               response= g.fromJson(responseString,responseType);
            } else {
                response = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Status code: " + Integer.toString(statusCode)
                    + " Exception thrown: " + e.getMessage());
            throw e;
        }
        return response;

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String result = "";
        // String s=   bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        inputStream.close();
        return result;
    }

    private void Tracerror(int responseCode,InputStream ErrorStream) throws IOException {
        if (responseCode == 200 || responseCode == 201 || responseCode >= 400) {
            BufferedReader in;
            if (responseCode >= 400) {
                in = new BufferedReader(new InputStreamReader(ErrorStream));
            }
            else {
                return ;//in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            }
            String inputLine;
            StringBuilder ree = new StringBuilder();
            ree.append(responseCode);
            ree.append(":");

            while((inputLine = in.readLine()) != null) {
                ree.append(inputLine);
            }
            //in.close();


                Log.d("ConnectionHandler", ree.toString());
                //return response.toString();
            }
            else {
                throw new IOException("Received bad response from server.");
            }

        }
}