package com.spartacus.webserviceutility.webservice;

import android.content.Context;
import android.os.AsyncTask;


import com.spartacus.webserviceutility.utility.Utility;

import org.json.JSONObject;

public class WSAsync extends AsyncTask<String, Void, Object>
{
    private final String TAG = WSAsync.class.getSimpleName();
    private Exception error = null;
    private Context context;
    private WSResponseListener responseListener;
    private JSONObject jsonObject;
    private boolean showLoader;
    private int wsId;
    private String wsUrl, message;
    private boolean StartListener;

    public WSAsync(Context context, WSResponseListener responseListener, JSONObject jsonObject,
                   String wsUrl, int wsId, String message, boolean showLoader)
    {
        this.context = context;
        this.responseListener = responseListener;
        this.jsonObject = jsonObject;
        this.wsUrl = wsUrl;
        this.wsId = wsId;
        this.message = message;
        this.showLoader = showLoader;
        this.StartListener = true;
    }

    public WSAsync(Context context, JSONObject jsonObject,
                   String wsUrl, int wsId, String message, boolean showLoader)
    {
        this.context = context;
        this.jsonObject = jsonObject;
        this.wsUrl = wsUrl;
        this.wsId = wsId;
        this.message = message;
        this.showLoader = showLoader;
        this .StartListener=false;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        if (showLoader) {
            Utility.showProgressDialog(context, message, false,false);
        }
    }

    @Override
    protected Object doInBackground(String... params) {
        try
        {
            return new WSRequest().getPostRequest(wsUrl, jsonObject, WSResponse.class);
        }
        catch (Exception e)
        {
            this.error = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result)
    {
        super.onPostExecute(result);
        if (StartListener)
        responseListener.onWSResponse(wsId, result, error);
    }
}