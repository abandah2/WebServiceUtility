package com.spartacus.webserviceutility.webservice;

/**
 * Created by Abandah on 2/7/2018.
 */

public interface WSResponseListener {
    void onResponse(WSResponse response);

    void onError(Exception error);
}
