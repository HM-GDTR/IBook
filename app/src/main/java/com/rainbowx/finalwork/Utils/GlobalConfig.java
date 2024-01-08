package com.rainbowx.finalwork.Utils;

import android.content.res.Resources;

public class GlobalConfig {
    public static String connectString;
    public static Resources resources;
    static {
        // 服务器地址(默认是主机端)
        connectString = "http://10.0.2.2:8080";


    }
}
