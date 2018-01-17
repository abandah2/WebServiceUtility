package com.spartacus.webserviceutility.webservice;

import org.json.JSONObject;

public class WSRequest
{
    /**
     * Post Request
     **/
    public <CLS> CLS getPostRequest(String url, JSONObject jsonData, Class<CLS> cls) throws
            Exception {
        return new WSRequestPost(url, jsonData).execute(cls);
    }
}