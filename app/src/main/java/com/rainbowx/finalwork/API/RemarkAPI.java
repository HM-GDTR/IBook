package com.rainbowx.finalwork.API;

import com.alibaba.fastjson2.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemarkAPI {
    static final String BASE_PATH = "/api/remark/";
    public static class ReleaseArgs {
        public int aid;
        public String uuid, content;
        public ReleaseArgs(int aid, String uuid, String content) {
            this.aid = aid;
            this.uuid = uuid;
            this.content = content;
        }
    }
    public static class ShowArgs {
        public int aid;
        public String uuid;
        public ShowArgs(int aid, String uuid) {
            this.aid = aid;
            this.uuid = uuid;
        }
    }
    public static class ModifyArgs {
        public int rid;
        public String uuid, content;
        public ModifyArgs(int rid, String uuid, String content) {
            this.rid = rid;
            this.uuid = uuid;
            this.content = content;
        }
    }
    public static class DeleteArgs {
        public int rid;
        public String uuid;
        public DeleteArgs(int rid, String uuid) {
            this.rid = rid;
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
