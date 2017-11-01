package com.example.ravia.weatherapplication.Model;

import java.util.ArrayList;

/**
 * Created by ravi on 23/9/16.
 */

public class GenericModelArr<T> {
    private ArrayList<T> responseObject;
    int errorCode;
    String errorMessage;

    public ArrayList<T> getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ArrayList<T> responseObject) {
        this.responseObject = responseObject;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
