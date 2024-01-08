package com.rainbowx.finalwork.Bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class FriendBean {
    public int uid;
    public String avatar, nickName, sign;

    public FriendBean(int uid, String avatar, String nickName, String sign) {
        this.uid = uid;
        this.avatar = avatar;
        this.nickName = nickName;
        this.sign = sign;
    }

    public Bitmap getAvatarBitMap() {
        if(avatar == null) return null;
        byte[] decoded;
        Bitmap avatarBitmap;
        String base64Image = avatar;
        try {
            decoded = Base64.decode(base64Image, Base64.DEFAULT);
        }catch (Exception e) {
            Log.w("FriendBean", "getAvatar: Fail to decode base64", e);
            return null;
        }
        try {
            avatarBitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        }catch (Exception e) {
            Log.w("FriendBean", "getAvatar: Fail to translate to bitmap", e);
            return null;
        }

        return avatarBitmap;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
