/**
 * @author c61
 * <p>
 * Single Listener for All web services call..
 * This is Interface, used for All the web services Response.
 * We can impliment this to get response of web services with any activity or fragment.
 */

package com.spartacus.webserviceutility.webservice;

public interface WSResponseListener {
    void onWSResponse(int wsId, Object data, Exception error);
}