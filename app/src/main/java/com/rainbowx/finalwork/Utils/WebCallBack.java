package com.rainbowx.finalwork.Utils;


public interface WebCallBack<T> {
    public void onHandling(boolean is_success, int code, T responseBody, String reason, Throwable tr);
}
