package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Utils.Error;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.GlobalConfig;
import com.rainbowx.finalwork.Utils.WebCallBack;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    CheckBox checkRememberPassword;
    Button buttonLogin, buttonSignUp;
    EditText textUserName, textPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("default", MODE_PRIVATE);

        if(sharedPreferences.contains("lastLoginUserName") && sharedPreferences.contains("lastLoginPassWord")){
            textUserName.setText(sharedPreferences.getString("lastLoginUserName", ""));
            textPassWord.setText(sharedPreferences.getString("lastLoginPassWord", ""));
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName, passWd;
                userName = textUserName.getText().toString();
                passWd = textPassWord.getText().toString();

                User.login(userName, passWd, new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && code == 0){
                            int uid = responseBody.getInteger("uid");
                            String uuid = responseBody.getString("uuid");
                            User.curUuid = uuid;
                            User.findByUid(uid, new WebCallBack<JSONObject>() {
                                @Override
                                public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                                    if (is_success && (code == 0)){
                                        User.curUser = User.parse(responseBody);

                                        SharedPreferences.Editor edit = sharedPreferences.edit();
                                        edit.putString("lastUUID", uuid);
                                        edit.putInt("lastUid", User.curUser.getUid());
                                        if (checkRememberPassword.isChecked()) {
                                            edit.putString("lastLoginUserName", userName);
                                            edit.putString("lastLoginPassWord", passWd);
                                        }
                                        edit.apply();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast info = Toast.makeText(LoginActivity.this,"登录失败 "+ Error.getLastError(), Toast.LENGTH_SHORT);
                                        info.show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast info = Toast.makeText(LoginActivity.this,"登录失败 "+ Error.getLastError(), Toast.LENGTH_SHORT);
                            info.show();
                        }
                    }
                });
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                //finish();
                return;
            }
        });
    }

    protected void initView() {
        checkRememberPassword = findViewById(R.id.loginCheckRememberPassword);
        buttonLogin = findViewById(R.id.loginButtonLogin);
        buttonSignUp = findViewById(R.id.loginButtonRegister);
        textUserName = findViewById(R.id.loginInputUsername);
        textPassWord = findViewById(R.id.loginInputPasswd);
    }
}