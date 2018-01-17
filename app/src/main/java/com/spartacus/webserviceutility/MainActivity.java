package com.spartacus.webserviceutility;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.spartacus.webserviceutility.constant.WSConstants;
import com.spartacus.webserviceutility.utility.Utility;
import com.spartacus.webserviceutility.webservice.WSAsync;
import com.spartacus.webserviceutility.webservice.WSResponse;
import com.spartacus.webserviceutility.webservice.WSResponseListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements WSResponseListener {

    private static final String TAG ="MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /////////////////////////////  API CALL /////////////////////////////////////////////
    private void CallAPIRegisterDevice() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WSConstants.PARAM_USERNAME, "user");
            jsonObject.put(WSConstants.PARAM_PASSWORD, "pass");
            if (Utility.isNetworkAvailable(this.getApplicationContext(), false)) {
                new WSAsync(this, this, jsonObject, WSConstants.API_URL, WSConstants.CALLID, "CONNECTING_TO_SERVER", false).execute();
            } else {
               // ShowErrorMessage(getResources().getString(R.string.Network_error));
            }
        } catch (Exception e) {
            Log.e(TAG, "CallAPIRegisterDevice Exception : " + e.toString());
        }
    }

    @Override
    public void onWSResponse(int wsId, Object data, Exception error) {

        switch (wsId) {
            case WSConstants.CALLID:
                OnResponseRegisterDevice(data, error);
                break;
        }
    }

    private void OnResponseRegisterDevice(Object data, Exception error) {
        try {
            Utility.hideProgressDialog();
            if (data != null) {
                WSResponse response = (WSResponse) data;
                if (response.getStatus().equalsIgnoreCase("1")) {
                } else {
                    //RestartScanning(response.getMessage());
                    //  ShowErrorMessage(response.getMessage());
                    Log.e(TAG, " failed, message : " + response.getMessage());
                }
            } else if (error != null) {
                //  ShowErrorMessage(error.toString());
                Log.e(TAG, " api Exception : " + error.toString());
            } else {
                //  ShowErrorMessage(getResources().getString(R.string.Not_Valid_QRCode));
            }
        } catch (Exception e) {
            // ShowErrorMessage(e.toString());
            Log.e(TAG, " Exception : " + e.toString());
            e.printStackTrace();
        }
    }



}
