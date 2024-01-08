package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;

public class IntroduceActivity extends AppCompatActivity {
    Button chat;
    AvatarView avatar;
    TextView nickName, info, ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        initView();

        Intent intent = getIntent();
        int uid = intent.getIntExtra("uid", -1);
        if(uid < 0) return;

        User.findByUid(uid, new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                if(is_success && code == 0) {
                    User user = User.parse(responseBody);
                    avatar.setImageBitmap(user.getAvatar());
                    nickName.setText(user.getNickName());
                    info.setText(user.getGender() + " "+ "25");
                    ip.setText("ip: 浙江");
                }
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(IntroduceActivity.this, ChatActivity.class);
                intent1.putExtra("uid", uid);
                startActivity(intent1);
            }
        });
    }

    public void initView() {
        ip = findViewById(R.id.introduceTextViewIp);
        chat = findViewById(R.id.introduceButtonChat);
        avatar = findViewById(R.id.introduceImgAvatar);
        info = findViewById(R.id.introduceTextViewInfo);
        nickName = findViewById(R.id.introduceTextViewNickName);
    }
}