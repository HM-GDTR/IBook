package com.rainbowx.finalwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.Adapter.ChatAdapter;
import com.rainbowx.finalwork.Bean.ChatBean;
import com.rainbowx.finalwork.Bean.FriendBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Chat;
import com.rainbowx.finalwork.Utils.Error;
import com.rainbowx.finalwork.Utils.WebCallBack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chatView;
    EditText editText;
    TextView submit;
    ChatAdapter adapter;
    int uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();

        adapter = null;

        Intent intent = getIntent();
        if(intent == null) return;
        uid = intent.getIntExtra("uid", -1);
        if(uid < 0) return;

        Chat.history(uid, new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                List<ChatBean> data = new ArrayList<>();
                if(is_success && code == 0){
                    JSONArray arr = responseBody.getJSONArray("res");
                    int len = arr.size();
                    for(int i = 0; i < len; i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Chat chat = Chat.parse(obj);
                        data.add(new ChatBean(chat.order,chat.cid,chat.other,chat.content));
                    }
                }
                adapter = new ChatAdapter(ChatActivity.this, data);
                StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                    chatView.setLayoutManager(st);
                    chatView.setAdapter(adapter);
                }
            }
        );

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                Chat.send(uid, msg, new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && (code == 0)) {
                            refresh();
                        }
                        else {
                            Toast toast;
                            if(reason == null || reason.isEmpty())
                                toast = Toast.makeText(ChatActivity.this, "发送失败: "+ Error.getLastError(), Toast.LENGTH_SHORT);
                            else
                                toast = Toast.makeText(ChatActivity.this, "发送失败: "+ reason, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });
    }

    protected void refresh() {
        Chat.history(uid, new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                List<ChatBean> data = new ArrayList<>();
                if(is_success && code == 0){
                    JSONArray arr = responseBody.getJSONArray("res");
                    int len = arr.size();
                    for(int i = 0; i < len; i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Chat chat = Chat.parse(obj);
                        data.add(new ChatBean(chat.order,chat.cid,chat.other,chat.content));
                    }
                }
                else {
                    data.add(new ChatBean(1, 0, 0, "test content"));
                    data.add(new ChatBean(-1, 0, 0, "test content1"));
                }
                adapter.setChatList(data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected void initView() {
        chatView = findViewById(R.id.chatRecyclerChats);
        editText = findViewById(R.id.chatEditEdit);
        submit = findViewById(R.id.chatTextSubmit);
    }
}