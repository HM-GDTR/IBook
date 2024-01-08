package com.rainbowx.finalwork.Services;

import com.rainbowx.finalwork.API.ChatAPI;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Utils.WebConnect;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;

public class Chat {
    public static final String TAG = "Chat";

    public String content;
    public int cid, other, order;
    protected static ChatAPI chatAPI;

    static {
        chatAPI = WebConnect.retrofit.create(ChatAPI.class);
    }

    public Chat(String content, Integer cid, int other, int order) {
        this.content = content;
        this.cid = cid;
        this.other = other;
        this.order = order;
    }

    public static Chat parse(JSONObject obj) {
        String content;
        int cid, other, order;

        cid = obj.getInteger("cid");
        other = obj.getInteger("other");
        order = obj.getInteger("order");
        content = obj.getString("content");

        return new Chat(content,cid,other,order);
    }

    public static void send(int target, String content, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ChatAPI.SendArgs args = new ChatAPI.SendArgs(target, User.curUuid, content);
        Call<JSONObject> call = chatAPI.send(args);
        WebConnect.asyncExec(call,callBack);
    }
    public static void history(int target, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ChatAPI.HistoryArgs args = new ChatAPI.HistoryArgs(User.curUuid, target);
        Call<JSONObject> call = chatAPI.history(args);
        WebConnect.asyncExec(call,callBack);
    }
    public static void historyAll(WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ChatAPI.HistoryAllArgs args = new ChatAPI.HistoryAllArgs(User.curUuid);
        Call<JSONObject> call = chatAPI.historyAll(args);
        WebConnect.asyncExec(call,callBack);
    }
}
