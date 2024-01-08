package com.rainbowx.finalwork.API;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ArticleAPI {
    static final String BASE_PATH = "/api/article/";
    public static class ReleaseArgs {
        public String uuid, content, title;
        public ReleaseArgs(String uuid, String content, String title) {
            this.uuid = uuid;
            this.content = content;
            this.title = title;
        }
    }
    public static class ShowArgs {
        public String uuid;
        public ShowArgs(String uuid) {
            this.uuid = uuid;
        }
    }
    public static class ModifyArgs {
        public int aid;
        public String uuid, content;
        public ModifyArgs(int aid, String uuid, String content) {
            this.aid = aid;
            this.uuid = uuid;
            this.content = content;
        }
    }
    public static class DeleteArgs {
        public int aid;
        public String uuid;
        public DeleteArgs(int aid, String uuid) {
            this.aid = aid;
            this.uuid = uuid;
        }
    }
    @POST(BASE_PATH + "release")
    Call<JSONObject> release(@Body ReleaseArgs requestBody);
    @POST(BASE_PATH + "show")
    Call<JSONObject> show(@Body ShowArgs requestBody);
    @POST(BASE_PATH + "modify")
    Call<JSONObject> modify(@Body ModifyArgs requestBody);
    @POST(BASE_PATH + "delete")
    Call<JSONObject> delete(@Body DeleteArgs requestBody);
}
