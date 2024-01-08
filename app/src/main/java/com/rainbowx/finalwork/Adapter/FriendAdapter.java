package com.rainbowx.finalwork.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rainbowx.finalwork.Activities.ChatActivity;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Bean.FriendBean;
import com.rainbowx.finalwork.Views.AvatarView;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    Context mcontext;
    List<FriendBean> friendList;

    public FriendAdapter(Context mcontext, List<FriendBean> friendList) {
        this.mcontext = mcontext;
        this.friendList = friendList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FriendBean friendBean = friendList.get(position);
        final Bitmap avatarBitMap = friendBean.getAvatarBitMap();
        holder.textSign.setText(friendBean.sign);
        holder.textNickName.setText(friendBean.nickName);
        if(avatarBitMap != null) {
            holder.avatarView.setImageBitmap(avatarBitMap);
        }
//        Glide.with(mcontext).load(memoBean.getimgpath()).into(mholder.item_img);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent(mcontext, ChatActivity.class);
                intent.putExtra("uid", friendBean.uid);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void setFriendList(List<FriendBean> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    public void addItem(FriendBean friend) {
        this.friendList.add(friend);
        notifyItemInserted(this.friendList.size()-1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        AvatarView avatarView;
        TextView textNickName, textSign;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.friendsRootLayout);
            textSign = itemView.findViewById(R.id.friendsTextSign);
            avatarView = itemView.findViewById(R.id.friendsAvatarImg);
            textNickName = itemView.findViewById(R.id.friendsTextNickName);
        }
    }
}
