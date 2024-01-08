package com.rainbowx.finalwork.Activities.ui.friends;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.Adapter.ArticleAdapter;
import com.rainbowx.finalwork.Adapter.FriendAdapter;
import com.rainbowx.finalwork.Bean.ArticleBean;
import com.rainbowx.finalwork.Bean.FriendBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Article;
import com.rainbowx.finalwork.Services.Chat;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.databinding.FragmentFriendsBinding;
import com.rainbowx.finalwork.databinding.FragmentPostsBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FriendsFragment extends Fragment {

    private Context context;
    private FragmentFriendsBinding binding;
    private static Integer reqCnt = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel friendsViewModel =
                new ViewModelProvider(this).get(FriendsViewModel.class);

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        final RecyclerView recyclerView = root.findViewById(R.id.friendsRecyclerFriends);

        FriendAdapter adapter = new FriendAdapter(context, new ArrayList<>());
        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(st);
        recyclerView.setAdapter(adapter);

        Chat.historyAll(new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                int len = 0;
                List<FriendBean> data = new ArrayList<>();

                if (is_success && code == 0) {
                    List<Chat> chats = new ArrayList<>();
                    JSONArray arr = responseBody.getJSONArray("res");

                    len = arr.size();

                    for (int i = 0; i < len; i++) {
                        chats.add(Chat.parse(arr.getJSONObject(i)));
                    }

                    adapter.setFriendList(new ArrayList<>());
                    Set<Integer> st = new TreeSet<>();

                    for (Chat chatInfo : chats) st.add(chatInfo.other);

                    for (int uid : st) {
                        User.findByUid(uid, new WebCallBack<JSONObject>() {
                            @Override
                            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                                if (is_success && code == 0) {
                                    User user = User.parse(responseBody);
                                    adapter.addItem(new FriendBean(user.uid, user.avatar, user.nickName, user.sign));
                                }
                            }
                        });
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}