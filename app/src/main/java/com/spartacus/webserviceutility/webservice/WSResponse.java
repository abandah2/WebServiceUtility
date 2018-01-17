package com.spartacus.webserviceutility.webservice;


public class WSResponse {

    private String Status;
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

}
