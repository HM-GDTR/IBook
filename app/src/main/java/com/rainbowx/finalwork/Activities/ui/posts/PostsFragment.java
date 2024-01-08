package com.rainbowx.finalwork.Activities.ui.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rainbowx.finalwork.Activities.AddActivity;
import com.rainbowx.finalwork.Adapter.ArticleAdapter;
import com.rainbowx.finalwork.Bean.ArticleBean;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.Article;
import com.rainbowx.finalwork.databinding.FragmentPostsBinding;
import com.rainbowx.finalwork.Utils.WebCallBack;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    private Context context;
    private FragmentPostsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PostsViewModel postsViewModel =
                new ViewModelProvider(this).get(PostsViewModel.class);

        binding = FragmentPostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //postsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        context = getContext();

        final RecyclerView recyclerView = root.findViewById(R.id.postsRecyclerPosts);
        final FloatingActionButton floatingActionButton = root.findViewById(R.id.postsButtonAddPosts);

        ArticleAdapter adapter = new ArticleAdapter(context, new ArrayList<>());
        StaggeredGridLayoutManager st = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(st);
        recyclerView.setAdapter(adapter);
        Article.show(new WebCallBack<JSONObject>() {
            @Override
            public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                List<ArticleBean> data = new ArrayList<>();
                if(is_success && code == 0){
                    int len;
                    List<Article> arr = new ArrayList<>();
                    JSONArray res = responseBody.getJSONArray("res");

                    len = res.size();

                    for(int i = 0; i<len; i++) {
                        arr.add(Article.parse(res.getJSONObject(i)));
                    }

                    for (Article articleInfo : arr) {
                        data.add(new ArticleBean(articleInfo.author, articleInfo.aid, articleInfo.content, articleInfo.timeStr,articleInfo.title));
                    }
                }
//                if(data.size() == 0){
//                    // 测试数据
//                    data.add(new ArticleBean(1,0,"test","2003-12-23","test title"));
//                    data.add(new ArticleBean(1,0,"test","2003-12-23","test title"));
//                }
                adapter.setArticleList(data);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                context.startActivity(intent);
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