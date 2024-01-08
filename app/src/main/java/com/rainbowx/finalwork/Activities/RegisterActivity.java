package com.rainbowx.finalwork.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONObject;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Utils.Error;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;

public class RegisterActivity extends AppCompatActivity {
    Button back;
    Button buttonRegister, buttonBack;
    EditText textUserName, textPassWord, textCheckPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = textUserName.getText().toString();
                String passWd = textPassWord.getText().toString();
                String checkPassWd = textCheckPassWord.getText().toString();

                if(!passWd.equals(checkPassWd)) {
                    Toast toast;
                    toast = Toast.makeText(RegisterActivity.this, "两次密码不一致",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    User.signup(uName, passWd, new WebCallBack<JSONObject>() {
                        @Override
                        public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                            Toast toast;
                            // 成功
                            if(is_success && code==0){
                                toast = Toast.makeText(RegisterActivity.this,"注册成功，快去登录吧!", Toast.LENGTH_SHORT);
                                toast.show();
                                finish();
                            }
                            else {
                                if(tr != null) {
                                    Log.e("Login", "onHandling: reason"+reason, tr);
                                }
                                toast = Toast.makeText(RegisterActivity.this,"注册失败 " + Error.getLastError(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initView() {
        back = findViewById(R.id.register_back);
        buttonBack = findViewById(R.id.back_button);
        buttonRegister = findViewById(R.id.register_button);

        textUserName = findViewById(R.id.register_username);
        textPassWord = findViewById(R.id.register_password);
        textCheckPassWord = findViewById(R.id.register_checkPassWord);
    }
}