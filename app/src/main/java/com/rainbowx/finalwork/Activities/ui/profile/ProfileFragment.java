package com.rainbowx.finalwork.Activities.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson2.JSONObject;
import com.rainbowx.finalwork.Activities.LoginActivity;
import com.rainbowx.finalwork.Activities.MainActivity;
import com.rainbowx.finalwork.Activities.SettingsCompat;
import com.rainbowx.finalwork.R;
import com.rainbowx.finalwork.Services.User;
import com.rainbowx.finalwork.Utils.WebCallBack;
import com.rainbowx.finalwork.Views.AvatarView;
import com.rainbowx.finalwork.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private Context context;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.settingsLayout, new SettingsCompat())
                .commit();

        final AvatarView avatar;
        final TextView nickName, info, ip;
        final Button logout;

        final User user = User.curUser;

        ip = root.findViewById(R.id.profileTextViewIp);
        avatar = root.findViewById(R.id.profileImgAvatar);
        info = root.findViewById(R.id.profileTextViewInfo);
        nickName = root.findViewById(R.id.profileTextViewNickName);
        logout = root.findViewById(R.id.profileButtonLogout);

        ip.setText("ip: 浙江");

        if(user.getGender() != null) info.setText(user.getGender()+" "+"25");

        if(User.curUser.getNickName() != null) nickName.setText(User.curUser.getNickName());
        if(User.curUser.getAvatar() != null) avatar.setImageBitmap(User.curUser.getAvatar());

        //final TextView textView = binding.textNotifications;
        //profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.logout(new WebCallBack<JSONObject>() {
                    @Override
                    public void onHandling(boolean is_success, int code, JSONObject responseBody, String reason, Throwable tr) {
                        if(is_success && (code == 0)) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("default", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear();
                            edit.apply();

                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            ((MainActivity) context).finish();
                            return;
                        }
                    }
                });
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