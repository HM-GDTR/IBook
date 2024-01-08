package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ProfileEditActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private Button submit;
    private AvatarView avatar;
    private EditText nickName, sign, birthday, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        avatar = findViewById(R.id.profileeditImgAvatar);
        nickName = findViewById(R.id.profileeditEditUserName);
        sign = findViewById(R.id.profileeditEditSign);
        birthday = findViewById(R.id.profileeditEditBirth);
        gender = findViewById(R.id.profileeditEditGender);

        submit = findViewById(R.id.profileeditButtonSubmit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        avatar.setImageBitmap(User.curUser.getAvatar());
        nickName.setText(User.curUser.getNickName());
        sign.setText(User.curUser.getSign());
        birthday.setText(dateFormat.format(User.curUser.getBirthday()));
        gender.setText(User.curUser.getGender());

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String nickNameStr = nickName.getText().toString();
                String signStr = sign.getText().toString();
                String birthdayStr = birthday.getText().toString();
                String genderStr = gender.getText().toString();

                if(nickNameStr.isEmpty()) nickNameStr = null;
                if(signStr.isEmpty()) signStr = null;
                if(birthdayStr.isEmpty()) birthdayStr = null;
                if(genderStr.isEmpty()) genderStr = null;

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                avatar.bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                // 使用Base64进行编码
                String avatarEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                LocalDateTime localDateTime;
                if(birthdayStr == null) localDateTime = null;
                else localDateTime = LocalDateTime.parse(birthdayStr, formatter);

                User.modify(localDateTime.toString(), avatarEncoded, nickNameStr, signStr, genderStr, new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && (code == 0)) {
                            int curUid = User.curUser.getUid();
                            User.findByUid(curUid, new WebCallBack<JSONObject>() {
                                @Override
                                public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                                    if(is_success && code == 0){
                                        User.curUser = User.parse(responseBody);
                                        return;
                                    }
                                    Toast toast = Toast.makeText(ProfileEditActivity.this, "刷新失败", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                            Toast toast = Toast.makeText(ProfileEditActivity.this, "保存成功", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                        Toast toast = Toast.makeText(ProfileEditActivity.this, "保存失败", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap selectedBitmap = getBitmapFromUri(data.getData());

            // 将图像缩放为48x48dp
            Bitmap scaledBitmap = scaleBitmap(selectedBitmap, 48, 48);
            avatar.setImageURI(data.getData());
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 通过URI获取图像的输入流，并将其解码为Bitmap
            InputStream inputStream = getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap scaleBitmap(Bitmap originalBitmap, int targetWidthDp, int targetHeightDp) {
        // 获取屏幕密度
        float density = getResources().getDisplayMetrics().density;

        // 计算目标宽度和高度（像素）
        int targetWidth = (int) (targetWidthDp * density);
        int targetHeight = (int) (targetHeightDp * density);

        // 缩放原始Bitmap
        return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);
    }
}