package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.GlobalConfig;
import com.rainbowx.finalwork.Utils.WebCallBack;

public class StartActivity extends AppCompatActivity {
    public static boolean is_ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        GlobalConfig.resources = getResources();

        is_ready = false;
        SharedPreferences sharedPreferences = getSharedPreferences("default", MODE_PRIVATE);

        if(sharedPreferences.contains("lastUUID")){
            String lastUuid = sharedPreferences.getString("lastUUID","");
            User.active(lastUuid, new WebCallBack<JSONObject>() {
                @Override
                public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                    // 如果令牌还没过期
                    if (is_success && (code == 0)) {
                        int uid = sharedPreferences.getInt("lastUid", -1);
                        if(uid == -1) {
                            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            is_ready = true;
                            return;
                        }
                        User.findByUid(uid, new WebCallBack<JSONObject>() {
                            @Override
                            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                                if(is_success && (code == 0)){
                                    User.curUuid = lastUuid;
                                    User.curUser = User.parse(responseBody);

                                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                    is_ready = true;
                                    return;
                                }
                                else {
                                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    is_ready = true;
                                    return;
                                }
                            }
                        });
                        return;
                    }
                    // 如果令牌过期
                    String lastUserName, lastPassWord;
                    lastUserName = sharedPreferences.getString("lastLoginUserName", "");
                    lastPassWord = sharedPreferences.getString("lastLoginPassWord", "");
                    User.login(lastUserName, lastPassWord, new WebCallBack<JSONObject>() {
                        @Override
                        public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                            if(is_success && (code == 0)) {
                                int uid = responseBody.getInteger("uid");
                                String uuid = responseBody.getString("uuid");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("lastUUID", uuid);
                                editor.putInt("lastUid", uid);
                                editor.apply();

                                User.findByUid(uid, new WebCallBack<JSONObject>() {
                                    @Override
                                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                                        if(is_success && (code == 0)){
                                            User.curUuid = uuid;
                                            User.curUser = User.parse(responseBody);

                                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                            is_ready = true;
                                            return;
                                        }
                                        else {
                                            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            is_ready = true;
                                            return;
                                        }
                                    }
                                });
                                return;
                            }
                            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            is_ready = true;
                        }
                    });
                }
            });
        }
        else {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            is_ready = true;
        }

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        return is_ready;
                    }
                });
    }
}