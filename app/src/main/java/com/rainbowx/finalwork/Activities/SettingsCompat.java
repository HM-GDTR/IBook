package com.rainbowx.finalwork.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.rainbowx.finalwork.Activities.settings.AboutActivity;
import com.rainbowx.finalwork.Activities.settings.DisclaimersActivity;
import com.rainbowx.finalwork.R;

public class SettingsCompat extends PreferenceFragmentCompat {

    public Preference about, disclaimers, check_update, account_setting, general_Settings;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        about = findPreference("about");
        disclaimers = findPreference("disclaimers");
        check_update = findPreference("check_update");
        account_setting = findPreference("account_setting");
        general_Settings = findPreference("general_Settings");

        disclaimers.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getActivity(), DisclaimersActivity.class);
                startActivity(intent);
                return true;
            }
        });

        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                return true;
            }
        });

        check_update.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Toast toast = Toast.makeText(getActivity(), "当前已是最新版本: 1.0.0", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });

        account_setting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                getActivity().startActivity(intent);
                return true;
            }
        });
    }
}