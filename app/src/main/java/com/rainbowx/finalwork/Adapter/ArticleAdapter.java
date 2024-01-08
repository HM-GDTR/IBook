package com.rainbowx.finalwork.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.Activities.DetailActivity;
import com.rainbowx.finalwork.Bean.ArticleBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Article;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    Context mcontext;
    List<ArticleBean> articleList;

    public static ArticleAdapter instance;

    static {
        instance = null;
    }

    public ArticleAdapter(Context mcontext, List<ArticleBean> articleList){
        this.mcontext = mcontext;
        this.articleList = articleList;

        instance = this;
    }

    public List<ArticleBean> getArticleList() {
        return articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ArticleBean articleBean = articleList.get(position);
//        final User author = User.getUserByUid(articleBean.getUid());
//        holder.nickName.setText(author.getNickName());
        User.findByUid(articleBean.getUid(), new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                if(is_success && code == 0){
                    User user = User.parse(responseBody);
                    holder.nickName.setText(user.getNickName());
                    holder.sign.setText(user.getSign());
                    holder.avatar.setImageBitmap(user.getAvatar());
                }
            }
        });

        holder.subject.setText(articleBean.getTitle());
        holder.content.setText(articleBean.getContent());
        holder.timeStamp.setText(articleBean.getTimeStr());
//        Glide.with(mcontext).load(memoBean.getimgpath()).into(mholder.item_img);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(mcontext, DetailActivity.class);
                intent.putExtra("position", pos);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void setArticleList(List<ArticleBean> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView rootView;
        public ImageView avatar;
        public TextView nickName, sign, subject, content, timeStamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.avatar = itemView.findViewById(R.id.imgAvatar);

            this.nickName =  itemView.findViewById(R.id.textNickName);
            this.sign =      itemView.findViewById(R.id.textSign);
            this.subject =   itemView.findViewById(R.id.textSubject);
            this.content =   itemView.findViewById(R.id.textContent);
            this.timeStamp = itemView.findViewById(R.id.textTimeStamp);
            this.rootView = itemView.findViewById(R.id.postsRootLayout);
        }
    }
}
