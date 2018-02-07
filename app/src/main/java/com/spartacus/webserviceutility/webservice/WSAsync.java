package com.spartacus.webserviceutility.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.spartacus.webserviceutility.utility.Utility;

import org.json.JSONObject;

import java.util.Random;

public class WSAsync extends AsyncTask<String, Void, Object> {
    private final String TAG = WSAsync.class.getSimpleName();
    private Exception error = null;
    private Context context;
    private WSResponseListener responseListener;
    private JSONObject jsonObject;
    private boolean showLoader;
    private int wsId;
    private String wsUrl, message;
    private boolean StartListener;
    public Class<?> cls;
    public WSAsync(Context context, JSONObject jsonObject,String wsUrl, WSResponseListener responseListener) {
        this.context = context;
        this.responseListener = responseListener;
        this.jsonObject = jsonObject;
        this.wsUrl = wsUrl;
        this.wsId = RandomInt();
        this.message = "CONNECTING_TO_SERVER";
        this.showLoader = true;
        this.StartListener = true;
        this.cls = WSResponse.class;
    }
    public WSAsync(Context context, JSONObject jsonObject,
                   String wsUrl, String message, boolean showLoader, WSResponseListener responseListener) {
        this.context = context;
        this.responseListener = responseListener;
        this.jsonObject = jsonObject;
        this.wsUrl = wsUrl;
        this.wsId = RandomInt();
        this.message = message;
        this.showLoader = showLoader;
        this.StartListener = true;
        this.cls = WSResponse.class;

    }

    public WSAsync(Context context, JSONObject jsonObject,
                   String wsUrl, String message, boolean showLoader) {
        this.context = context;
        this.jsonObject = jsonObject;
        this.wsUrl = wsUrl;
        this.wsId = RandomInt();
        this.message = message;
        this.showLoader = showLoader;
        this.StartListener = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showLoader) {
            Utility.showProgressDialog(context, message, false, false);
        }
    }

    @Override
    protected Object doInBackground(String... params) {
        if(!Utility.isNetworkAvailable(context,true))
            responseListener.onError(new Exception("no internet connection "));
        try {
            return new WSRequest().getPostRequest(wsUrl, jsonObject, cls);
        } catch (Exception e) {
            this.error = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (StartListener) {
            Utility.hideProgressDialog();

            onWSResponse(wsId, result, error);
        }
    }

    public static int RandomInt() {
        int min = 0;
        int max = 500;

        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


    void onWSResponse(int wsId, Object data, Exception error) {
        try {
            if (data != null) {
                WSResponse response = (WSResponse) data;
                responseListener.onResponse(response);
            } else if (error != null) {
                responseListener.onError(error);
                Log.e(TAG, " api Exception : " + error.toString());
            } else {
                //  ShowErrorMessage(getResources().getString(R.string.Not_Valid_QRCode));
            }
        } catch (Exception e) {
            responseListener.onError(e);
            Log.e(TAG, " Exception : " + e.toString());
            e.printStackTrace();
        }
    }

}