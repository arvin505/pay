package com.miqtech.master.client.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.miqtech.master.client.App;
import com.miqtech.master.client.DeviceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyi on 2016/8/15.
 */
public class NormalPostRequest extends com.android.volley.Request<JSONObject> {
    public Map<String, String> mParams;
    public Response.Listener<JSONObject> mListener;
    private static final String TAG = "NormalPostRequest";
    private ResponseListener mCallback;
    private String mRequestTag;

    public NormalPostRequest(String url, Response.Listener<JSONObject> listener, ResponseListener callback, Map<String, String> params, String requestTag) {
        super(Method.POST, url, null);
        this.mParams = params;
        this.mCallback = callback;
        this.mListener = listener;
        this.mRequestTag = requestTag;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(
            NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        mListener.onResponse(jsonObject);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        // FIXME: 2016/8/15
        headers.put("User-Agent", DeviceUtils.getUserAgent(App.getApp()));
        //           headers.put("User-Agent", DateUtil.getNow().toString());
        return headers;
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        mCallback.onError(error.getMessage(), mRequestTag);
        Log.e(TAG, "---message" + error);
        error.printStackTrace();

    }
}
