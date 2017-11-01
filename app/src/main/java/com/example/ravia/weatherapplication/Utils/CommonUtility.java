package com.example.ravia.weatherapplication.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ravia.weatherapplication.Controllers.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ravi on 30/1/16.
 */
public class CommonUtility {


    ProgressDialog pdilog;
    String TAG = getClass().getName();
    Boolean show = true;

    public void progressDialog(final Context context, String msg) {
        pdilog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        pdilog.setMessage(msg);
        pdilog.setIndeterminate(false);
        pdilog.setCancelable(false);
        pdilog.show();

    }

    public void stopProgressDialog() {
        pdilog.dismiss();
    }

    public void getValueFromSharedPreference() {

    }

    public void call_intent(Context context, Class nextActivityClass) {
        Intent intent = new Intent(context, nextActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void callIntentClearFlagTop(Context context, Class nextActivityClass) {
        Intent intent = new Intent(context, nextActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void Loge(String contextName, String tag) {
        if (show == true) {
            Log.e(contextName , " : " + tag);
        }

    }

    public void Loge(String contextName, Object tag) {
        if (show == true) {
            Log.e(contextName , " : " + tag);
        }

    }


    public String getErrorType(VolleyError error) {
        String error_str = "";
        if (error instanceof TimeoutError) {
            error_str = "Timeout Error";
        } else if (error instanceof NoConnectionError) {
            error_str = "NoConnectionError";
        } else if (error instanceof AuthFailureError) {
            error_str = "Authentication Failure";
        } else if (error instanceof ServerError) {
            error_str = "Server Error";
        } else if (error instanceof NetworkError) {
            error_str = "Network Error";
        } else if (error instanceof ParseError) {
            error_str = "Parse Error";
        }

        return error_str;
    }

    public void SnackbarLong(String text, int color, ViewGroup viewGroup) {
        Snackbar snackbar = Snackbar.make(viewGroup, text, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }


    public void stringRequest(int method, String url, final VolleyResponseListener listener, final HashMap<String, String> hm) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Loge(TAG, "getParams: " + hm.toString());
                if (hm == null) {
                    return super.getParams();
                } else {
                    return hm;
                }
            }
        };
        AppController.getInstance().getRequestQueue().add(request).setRetryPolicy(new DefaultRetryPolicy(200000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }




    public void jsonRequestStr(int method, String url, final VolleyResponseListener listener, final String str) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, str, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.toString());
            }
        });
        AppController.getInstance().getRequestQueue().add(request).setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
