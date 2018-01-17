package com.spartacus.webserviceutility.utility;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.spartacus.webserviceutility.R;

public class Utility {

    private static final String TAG = Utility.class.getSimpleName();
    private static final int THEME_ALERT_DIALOG_USER = 0;

    public static ProgressDialog progressDialog;
    public static AlertDialog.Builder builder;


    public static void showProgressDialog(Context context, String msg, boolean isCancelable, boolean CanceledOnTouchOutside) {
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(isCancelable);
            progressDialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
            progressDialog.show();
        }
    }

    public static void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, "hideProgressDialog Exception : " + e.toString());
        }
    }

    public static AlertDialog alert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, THEME_ALERT_DIALOG_USER);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        AlertDialog dialog = builder.setPositiveButton("ok", null).create();
        dialog.show();
        return dialog;
    }


    public static boolean isNetworkAvailable(Context context, boolean Showalert) {
        boolean IsOn = false;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            IsOn = netInfo != null && netInfo.isConnectedOrConnecting();
        }
        if (Showalert && !IsOn) {
            alert(context, "caution", "no_internet_connection");
        }
        return IsOn;
    }


}