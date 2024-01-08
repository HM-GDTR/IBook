package com.rainbowx.finalwork.API;

import com.alibaba.fastjson2.JSONObject;

import java.time.LocalDateTime;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    static final String BASE_PATH = "/api/user/";
    public static class SignUpArgs{
        public String uname, passwd;
        public SignUpArgs(String uname, String passwd) {
            this.uname = uname;
            this.passwd = passwd;
        }
    }
    public static class FindByUidArgs {
        public int uid;
        public FindByUidArgs(int uid) {
            this.uid = uid;
        }
    }
    public static class LogInArgs {
        public String uname,passwd;
        public LogInArgs(String uname, String passwd) {
            this.uname = uname;
            this.passwd = passwd;
        }
    }
    public static class LogOutArgs {
        public String uuid;
        public LogOutArgs(String uuid) {
            this.uuid = uuid;
        }
    }
    public static class ModifyArgs {
        public String birthday;
        public String uuid, avatar, nickname, sign, gender;

        public ModifyArgs(String birthday, String uuid, String avatar, String nickname, String sign, String gender) {
            this.birthday = birthday;
            this.uuid = uuid;
            this.avatar = avatar;
            this.nickname = nickname;
            this.sign = sign;
            this.gender = gender;
        }
    }
    public static class ModifyPassWdArgs {
        public String uuid, passwd;

        public ModifyPassWdArgs(String uuid, String passwd) {
            this.uuid = uuid;
            this.passwd = passwd;
        }
    }

    public static class ActiveArgs {
        public String uuid;

        public ActiveArgs(String uuid) {
            this.uuid = uuid;
        }
    }
    @POST(BASE_PATH + "active")
    Call<JSONObject> active(@Body ActiveArgs requestBody);
    @POST(BASE_PATH + "signup")
    Call<JSONObject> signup(@Body SignUpArgs requestBody);
    @POST(BASE_PATH + "find")
    Call<JSONObject> findByUid(@Body FindByUidArgs requestBody);
    @POST(BASE_PATH + "login")
    Call<JSONObject> login(@Body LogInArgs requestBody);
    @POST(BASE_PATH + "modify")
    Call<JSONObject> modify(@Body ModifyArgs requestBody);
    @POST(BASE_PATH + "modifyPassWd")
    Call<JSONObject> modifyPassWd(@Body ModifyPassWdArgs requestBody);
    @POST(BASE_PATH + "logout")
    Call<JSONObject> logout(@Body LogOutArgs requestBody);
}
