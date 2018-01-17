package com.spartacus.webserviceutility;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /////////////////////////////  API CALL /////////////////////////////////////////////
    private void CallAPIRegisterDevice(String lenvotoken, String gcmtoken) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WSConstants.PARAM_DEVIC_SERIAL, GetUniqueId());
            jsonObject.put(WSConstants.PARAM_LENVO_TOKEN, lenvotoken);
            jsonObject.put(WSConstants.PARAM_GCM_TOKEN, gcmtoken);
            jsonObject.put(WSConstants.PARAM_DEVICE_TYPE, WSConstants.DEVICE_OS);
            if (Utility.isNetworkAvailable(this.getApplicationContext(), false)) {
                new WSAsync(this, this, jsonObject, WSConstants.API_REGISTER_DEVICE, WSConstants.REGISTER_DEVICE,
                        getResources().getString(R.string.CONNECTING_TO_SERVER), false).execute();
            } else {
                ShowErrorMessage(getResources().getString(R.string.Network_error));
            }
        } catch (Exception e) {
            RestartScanning(EROR_COMUNICATE_WITH_SERVER);
            Log.e(TAG, "CallAPIRegisterDevice Exception : " + e.toString());
        }
    }

    @Override
    public void onWSResponse(int wsId, Object data, Exception error) {

        switch (wsId) {
            case WSConstants.REGISTER_DEVICE:
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
                    String user_id = response.getUserId();
                    String last_Lenvo_token = response.getLenvoLoginToken();
                    SavePrefAndGlobize(user_id, last_Lenvo_token, Global.Link);
                } else {
                    RestartScanning(response.getMessage());
                    //  ShowErrorMessage(response.getMessage());
                    Log.e(TAG, " failed, message : " + response.getMessage());
                }
            } else if (error != null) {
                RestartScanning(INVALED_QR_CODE);
                //  ShowErrorMessage(error.toString());
                Log.e(TAG, " api Exception : " + error.toString());
            } else {
                RestartScanning(INVALED_QR_CODE);
                //  ShowErrorMessage(getResources().getString(R.string.Not_Valid_QRCode));
            }
        } catch (Exception e) {
            RestartScanning(INVALED_QR_CODE);
            // ShowErrorMessage(e.toString());
            Log.e(TAG, " Exception : " + e.toString());
            e.printStackTrace();
        }
    }


}
