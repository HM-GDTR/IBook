package com.rainbowx.finalwork.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson2.JSONException;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebConnect {
    public static String TAG = "WebConnect";
    public static Retrofit retrofit;
    public JSONObject responseBody;
    public boolean isReady;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalConfig.connectString)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void asyncExec(Call<JSONObject> call, @NonNull WebCallBack<JSONObject> callback){
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                try {
                    Log.d(TAG, "onResponse: received from server:" + response);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Call server successful.");
                        JSONObject responseBody = response.body();
                        if (responseBody == null) {
                            Log.d(TAG, "Server return null.");
                            Error.setLastError("Internal error " + Error.ErrorType.TYPE_SERVER_NO_RESPONSE);
                            callback.onHandling(true, -7, null, "Server return null.", null);
                            return;
                        }
                        int code = responseBody.getInteger("code");
                        String reason;
                        if (code != 0) {
                            reason = responseBody.getString("reason");
                            Log.e(TAG, "Error code:" + code);
                            Log.e(TAG, "Reason: " + reason);
                            Error.setLastError(reason);
                            callback.onHandling(false, code, responseBody, reason, null);
                            return;
                        }
                        callback.onHandling(true, code, responseBody, "ok", null);
                    } else {
                        Log.e(TAG, "Call server fail.");
                        Error.setLastError("Internal error " + Error.ErrorType.TYPE_CONNECTION_NOT_SUCCESS);
                        callback.onHandling(false, -9, null, "Internal error " + Error.ErrorType.TYPE_CONNECTION_NOT_SUCCESS, null);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Exception occurs.", e);
                    Error.setLastError("Internal error "+ Error.ErrorType.TYPE_INTERNAL_ERROR);
                    callback.onHandling(false, -13, null, "Internal error "+ Error.ErrorType.TYPE_INTERNAL_ERROR, e);
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e(TAG, "onFailure: fail to received from server.", t);
                callback.onHandling(false, -11, null, "Fail to connect to server", t);
            }
        });
    }

}
