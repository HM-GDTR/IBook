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
import com.rainbowx.finalwork.Activities.ChatActivity;
import com.rainbowx.finalwork.Activities.IntroduceActivity;
import com.rainbowx.finalwork.Bean.ArticleBean;
import com.rainbowx.finalwork.Bean.RemarkBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;

import java.util.List;

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    Context mcontext;
    List<RemarkBean> remarkList;

    public RemarkAdapter(Context mcontext, List<RemarkBean> remarkList) {
        this.mcontext = mcontext;
        this.remarkList = remarkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_remark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RemarkBean remarkBean = remarkList.get(position);

        if(remarkBean.getAuthor() != User.curUser.getUid()) {
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, IntroduceActivity.class);
                    intent.putExtra("uid", remarkBean.getAuthor());
                    mcontext.startActivity(intent);
                }
            });
        }
        User.findByUid(remarkBean.getAuthor(), new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                if(is_success && code == 0) {
                    User userInfo = User.parse(responseBody);
                    holder.avatar.setImageBitmap(userInfo.getAvatar());
                    holder.nickName.setText(userInfo.getNickName());
                }
            }
        });

        holder.timestamp.setText(remarkBean.getTimeStr());
        holder.content.setText(remarkBean.getContent());
    }

    public void setRemarkList(List<RemarkBean> remarkList) {
        this.remarkList = remarkList;
        notifyDataSetChanged();
    }

    public void addRemark(RemarkBean remark) {
        this.remarkList.add(remark);
        notifyItemInserted(this.remarkList.size()-1);
    }

    @Override
    public int getItemCount() {
        return remarkList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AvatarView avatar;
        TextView nickName, content, timestamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.remarkTextNickName);
            avatar = itemView.findViewById(R.id.remarkImgAvatar);
            content = itemView.findViewById(R.id.remarkTextContent);
            timestamp = itemView.findViewById(R.id.remarkTextTimeStamp);
        }
    }
}
