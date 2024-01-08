package com.rainbowx.finalwork.Services;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.API.ArticleAPI;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Utils.WebConnect;

import retrofit2.Call;

public class Article {
    public static final String TAG = "Article";

    public int aid, author;
    public String content, title, timeStr;
    protected static ArticleAPI articleAPI;

    static {
        articleAPI = WebConnect.retrofit.create(ArticleAPI.class);
    }

    public Article(String content, int aid, int author, String title, String timeStr) {
        this.content = content;
        this.aid = aid;
        this.author = author;
        this.title = title;
        this.timeStr = timeStr;
    }

    public static Article parse(JSONObject obj) {
        int aid, author;
        String content, title, timeStr;

        aid = obj.getInteger("aid");
        author = obj.getInteger("author");
        content = obj.getString("content");
        title = obj.getString("title");
        timeStr = obj.getDate("time").toString();

        return new Article(content,aid,author,title,timeStr);
    }

    public static void release(String title, String content, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ArticleAPI.ReleaseArgs args = new ArticleAPI.ReleaseArgs(User.curUuid,content, title);
        Call<JSONObject> call = articleAPI.release(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void show(WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ArticleAPI.ShowArgs args = new ArticleAPI.ShowArgs(User.curUuid);
        Call<JSONObject> call = articleAPI.show(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void modify(int aid, String content, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ArticleAPI.ModifyArgs args = new ArticleAPI.ModifyArgs(aid, User.curUuid, content);
        Call<JSONObject> call = articleAPI.modify(args);
        WebConnect.asyncExec(call, callBack);
    }
    public static void modify(int aid, WebCallBack<JSONObject> callBack) {
        if(User.curUuid == null) {
            callBack.onHandling(false, -3, null, "你还没有登录", null);
            return;
        }
        ArticleAPI.DeleteArgs args = new ArticleAPI.DeleteArgs(aid, User.curUuid);
        Call<JSONObject> call = articleAPI.delete(args);
        WebConnect.asyncExec(call, callBack);
    }
}
