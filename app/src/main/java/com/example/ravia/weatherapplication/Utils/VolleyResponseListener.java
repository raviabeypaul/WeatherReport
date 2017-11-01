package com.example.ravia.weatherapplication.Utils;

/**
 * Created by ravi on 23/9/16.
 */

public interface VolleyResponseListener {
    void onError(String message);

    void onResponse(Object response);
}
