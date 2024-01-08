package com.rainbowx.finalwork.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rainbowx.finalwork.Bean.ChatBean;
import com.rainbowx.finalwork.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context mcontext;
    List<ChatBean> chatList;
    public ChatAdapter(Context context, List<ChatBean> chatList){
        this.mcontext = context;
        this.chatList = chatList;
    }

    public List<ChatBean> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatBean> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ChatBean chatBean = chatList.get(position);
        if(chatBean.order == 1){
            holder.imgRight.setImageResource(R.drawable.ic_launcher_background);
            holder.imgLeft.setVisibility(View.INVISIBLE);
            holder.textContent.setGravity(Gravity.END);
            //Glide.with(mcontext).load(memoBean.getimgpath()).into(holder.imgRight);
        }
        else{
            holder.imgLeft.setImageResource(R.drawable.ic_launcher_background);
            holder.imgRight.setVisibility(View.INVISIBLE);
            holder.textContent.setGravity(Gravity.START);
        }
        holder.textContent.setText(chatBean.content);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textContent;
        public ImageView imgLeft,imgRight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imgLeft);
            imgRight = itemView.findViewById(R.id.imgRight);
            textContent = itemView.findViewById(R.id.textContent);
        }
    }
}
