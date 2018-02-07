package com.spartacus.webserviceutility;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.spartacus.webserviceutility.constant.WSConstants;
import com.spartacus.webserviceutility.utility.Utility;
import com.spartacus.webserviceutility.webservice.WSAsync;
import com.spartacus.webserviceutility.webservice.WSResponse;
import com.spartacus.webserviceutility.webservice.WSResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /////////////////////////////  API CALL /////////////////////////////////////////////
    private void CallAPIRegisterDevice() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WSConstants.PARAM_USERNAME, "user");
            jsonObject.put(WSConstants.PARAM_PASSWORD, "pass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //send array
           /* JSONArray jsonArray = new JSONArray();
            for (String string :iist) {
                jsonArray.put(string);
            }
            jsonObject.put(WSConstants.array, jsonArray);*/

        //send pic
        // jsonObject.put(WSConstants.PARAM_Pic, Base64.encodeToString(image, 0));

        new WSAsync(this, jsonObject, WSConstants.API_URL, new WSResponseListener() {

            @Override
            public void onResponse(WSResponse response) {

            }

            @Override
            public void onError(Exception error) {

            }
        }).execute();
    }
}







