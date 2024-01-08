package com.rainbowx.finalwork.Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import retrofit2.Call;

import com.rainbowx.finalwork.API.UserAPI;
import com.rainbowx.finalwork.Activities.LoginActivity;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Utils.GlobalConfig;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Utils.WebConnect;

import com.alibaba.fastjson2.JSONObject;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Date;

public class User {
    public static final String TAG = "User";

    // 当前(登录了的)账号
    public static User curUser;
    // 当前登录令牌
    public static String curUuid;

    // 基本信息
    public int uid;
    public Date birthday, reg_date;
    public String nickName, sign, gender, avatar;

    // 向服务端发起请求的接口
    protected static UserAPI userAPI;

    static {
        curUuid = null;
        userAPI = WebConnect.retrofit.create(UserAPI.class);
    }

    public Bitmap getAvatar() {
        if(avatar == null) return null;
        byte[] decoded;
        Bitmap avatarBitmap;
        String base64Image = avatar;
        try {
            decoded = Base64.decode(base64Image, Base64.DEFAULT);
        }catch (Exception e) {
            Log.w(TAG, "getAvatar: Fail to decode base64", e);
            return null;
        }
        try {
            avatarBitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        }catch (Exception e) {
            Log.w(TAG, "getAvatar: Fail to translate to bitmap", e);
            return null;
        }

        return avatarBitmap;
    }

    public int getUid() {
        return uid;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public String getNickName() {
        return nickName;
    }

    public String getSign() {
        return sign;
    }

    public String getGender() {
        return gender;
    }

    public static UserAPI getUserAPI() {
        return userAPI;
    }

    public User(int uid, Date birthday, Date reg_date, String nickName, String sign, String gender, String avatar) {
        this.uid = uid;
        this.birthday = birthday;
        this.reg_date = reg_date;
        this.nickName = nickName;
        this.sign = sign;
        this.gender = gender;
        this.avatar = avatar;
    }
    public static User parse(JSONObject obj) {
        int uid;
        Date birthday, reg_date;
        String nickName, sign, gender, avatar;

        uid = obj.getInteger("uid");
        sign = obj.getString("sign");
        avatar = obj.getString("avatar");
        gender = obj.getString("gender");
        birthday = obj.getDate("birthday");
        reg_date = obj.getDate("reg_date");
        nickName = obj.getString("nickname");

        if(sign == null) {
            sign = "这个人很神秘，什么都没有留下。";
        }
        if(nickName == null) {
            nickName = "JohnDoe";
        }
        if(birthday == null) {
            birthday = new Date(2023, 12, 23);
        }
        if(gender == null) {
            gender = "男";
        }
        if(avatar == null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap avatarBitMap = BitmapFactory.decodeResource(GlobalConfig.resources, R.drawable.ic_default_avatar, options);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            avatarBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        return new User(uid, birthday, reg_date, nickName, sign, gender, avatar);
    }
    public static void login(String username, String password, WebCallBack<JSONObject> callBack) {
        UserAPI.LogInArgs args = new UserAPI.LogInArgs(username,password);
        Call<JSONObject> call = userAPI.login(args);
        WebConnect.asyncExec(call, callBack);
    }

    public static void signup(String username, String password, WebCallBack<JSONObject> callBack) {
        UserAPI.SignUpArgs args = new UserAPI.SignUpArgs(username, password);
        Call<JSONObject> call = userAPI.signup(args);
        WebConnect.asyncExec(call, callBack);
    }

    public static void findByUid(int uid, WebCallBack<JSONObject> callBack) {
        UserAPI.FindByUidArgs args = new UserAPI.FindByUidArgs(uid);
        Call<JSONObject> call = userAPI.findByUid(args);
        WebConnect.asyncExec(call, callBack);
    }

    public static void logout(WebCallBack<JSONObject> callBack) {
        if(curUuid == null) {
            callBack.onHandling(false, -1, null, "你还未登录", null);
            return;
        }
        UserAPI.LogOutArgs args = new UserAPI.LogOutArgs(curUuid);
        Call<JSONObject> call = userAPI.logout(args);
        WebConnect.asyncExec(call, callBack);
    }

    public static void modify(String birthday, String avatar, String nickName, String sign, String gender, WebCallBack<JSONObject> callBack) {
        if(curUuid == null) {
            callBack.onHandling(false, -1, null, "你还未登录", null);
            return;
        }
        UserAPI.ModifyArgs args = new UserAPI.ModifyArgs(birthday, curUuid, avatar, nickName, sign, gender);
        Call<JSONObject> call = userAPI.modify(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void modifyPassWd(String passwd, WebCallBack<JSONObject> callBack) {
        if(curUuid == null) {
            callBack.onHandling(false, -1, null, "你还未登录", null);
            return;
        }
        UserAPI.ModifyPassWdArgs args = new UserAPI.ModifyPassWdArgs(curUuid, passwd);
        Call<JSONObject> call = userAPI.modifyPassWd(args);
        WebConnect.asyncExec(call, callBack);
    }

    public static void active(String uuid, WebCallBack<JSONObject> callBack) {
       UserAPI.ActiveArgs args = new UserAPI.ActiveArgs(uuid);
       Call<JSONObject> call = userAPI.active(args);
       WebConnect.asyncExec(call, callBack);
    }
}
