package com.rainbowx.finalwork.API;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatAPI {
    static final String BASE_PATH = "/api/chat/";
    public static class SendArgs{
        public int target;
        public String uuid, content;
        public SendArgs(int target, String uuid, String content) {
            this.target = target;
            this.uuid = uuid;
            this.content = content;
        }
    }

    public static class HistoryArgs {
        public String uuid;
        public int target;
        public HistoryArgs(String uuid, int target) {
            this.uuid = uuid;
            this.target = target;
        }
    }

    public static class HistoryAllArgs {
        public String uuid;
        public HistoryAllArgs(String uuid) {
            this.uuid = uuid;
        }
    }

    @POST(BASE_PATH + "send")
    Call<JSONObject> send(@Body SendArgs requestBody);
    @POST(BASE_PATH + "history")
    Call<JSONObject> history(@Body HistoryArgs requestBody);
    @POST(BASE_PATH + "history")
    Call<JSONObject> historyAll(@Body HistoryAllArgs requestBody);
}
