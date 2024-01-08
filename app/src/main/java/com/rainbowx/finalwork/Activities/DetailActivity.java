package com.rainbowx.finalwork.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.Adapter.ArticleAdapter;
import com.rainbowx.finalwork.Adapter.RemarkAdapter;
import com.rainbowx.finalwork.Bean.ArticleBean;
import com.rainbowx.finalwork.Bean.RemarkBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Remark;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    AvatarView avatar;
    TextView nickName, sign;
    TextView title, content, time_str;
    RecyclerView remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        Intent intent = getIntent();
        if(intent == null) return;
        int pos = intent.getIntExtra("position", -1);
        if(pos < 0) return;

        List<ArticleBean> articles = ArticleAdapter.instance.getArticleList();

        if(pos >= articles.size()) return;

        ArticleBean cur = articles.get(pos);

        final EditText remark = findViewById(R.id.detailEditEdit);
        final TextView submit = findViewById(R.id.detailTextSubmit);

        if (cur.getUid() != User.curUser.getUid()) {
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(DetailActivity.this, IntroduceActivity.class);
                    intent1.putExtra("uid", cur.getUid());
                    startActivity(intent1);
                }
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = remark.getText().toString();
                Remark.release(cur.getAid(), msg, new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && (code == 0)){
                            recreate();
                            Toast toast = Toast.makeText(DetailActivity.this, "发送成功!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

        User.findByUid(cur.getUid(), new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                if(is_success && code == 0) {
                    User userInfo = User.parse(responseBody);
                    avatar.setImageBitmap(userInfo.getAvatar());
                    title.setText(cur.getTitle());
                    content.setText(cur.getContent());
                    time_str.setText(cur.getTimeStr());
                    nickName.setText(userInfo.getNickName());
                    sign.setText(userInfo.getSign());
                }
            }
        });

        Remark.show(cur.aid, new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                List<RemarkBean> data = new ArrayList<>();
                if(is_success && code == 0) {
                    JSONArray arr = responseBody.getJSONArray("res");
                    int len = arr.size();
                    for(int i = 0;i < len;i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Remark remark = Remark.parse(obj);
                        data.add(new RemarkBean(cur.aid, remark.author, remark.timeStr, remark.content));
                    }
                }

                RemarkAdapter adapter = new RemarkAdapter(DetailActivity.this, data);
                StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                remarks.setLayoutManager(st);
                remarks.setAdapter(adapter);
            }
        });
    }

    protected void initView() {
        content = findViewById(R.id.editTextContent);
        avatar = findViewById(R.id.editImgAvatar);
        nickName = findViewById(R.id.editTextNickName);
        sign = findViewById(R.id.editTextSign);
        title = findViewById(R.id.editTextSubject);
        time_str = findViewById(R.id.editTextTimeStamp);
        remarks = findViewById(R.id.editRecyclerRemark);
    }
}