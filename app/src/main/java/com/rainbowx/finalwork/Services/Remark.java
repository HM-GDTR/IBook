package com.rainbowx.finalwork.Services;

import com.rainbowx.finalwork.API.RemarkAPI;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Utils.WebConnect;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;

public class Remark {
    public static final String TAG = "Remark";

    public int rid, author;
    public String content, timeStr;
    protected static RemarkAPI remarkAPI;

    static {
        remarkAPI = WebConnect.retrofit.create(RemarkAPI.class);
    }

    public Remark(String content, int rid, int author, String timeStr) {
        this.content = content;
        this.rid = rid;
        this.author = author;
        this.timeStr = timeStr;
    }

    public static Remark parse(JSONObject obj) {
        int rid, author;
        String content, timeStr;

        rid = obj.getInteger("rid");
        author = obj.getInteger("author");
        content = obj.getString("content");
        timeStr = obj.getDate("time").toString();

        return new Remark(content,rid,author, timeStr);
    }

    public static void release(int aid, String content, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        RemarkAPI.ReleaseArgs args = new RemarkAPI.ReleaseArgs(aid, User.curUuid, content);
        Call<JSONObject> call = remarkAPI.release(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void show(int aid, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        RemarkAPI.ShowArgs args = new RemarkAPI.ShowArgs(aid, User.curUuid);
        Call<JSONObject> call = remarkAPI.show(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void modify(int rid, String content, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        RemarkAPI.ModifyArgs args = new RemarkAPI.ModifyArgs(rid, User.curUuid, content);
        Call<JSONObject> call = remarkAPI.modify(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void modify(int rid, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        RemarkAPI.DeleteArgs args = new RemarkAPI.DeleteArgs(rid, User.curUuid);
        Call<JSONObject> call = remarkAPI.delete(args);
        WebConnect.asyncExec(call, callBack);
    }
}
