package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Article;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.Error;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;

import java.util.Date;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final TextView sign = findViewById(R.id.addTextSign);
        final AvatarView avatar = findViewById(R.id.addImgAvatar);
        final TextView nickName = findViewById(R.id.addTextNickName);
        final EditText title = findViewById(R.id.addEditTextSubject);
        final EditText content = findViewById(R.id.addEditTextContent);

        final Button submit = findViewById(R.id.addButtonSubmit);

        if(User.curUser == null) {
            User.curUser = new User(0, new Date(), new Date(), "test", "test", "未知", null);
        }
        sign.setText(User.curUser.getSign());
        nickName.setText(User.curUser.getNickName());
        if(User.curUser.getAvatar() != null)
            avatar.setImageBitmap(User.curUser.getAvatar());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article.release(title.getText().toString(), content.getText().toString(), new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && code == 0) {
                            Toast toast = Toast.makeText(AddActivity.this, "发布成功", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                            return;
                        }
                        Toast toast = Toast.makeText(AddActivity.this, "发布失败: " + Error.getLastError(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });
    }
}