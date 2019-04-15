package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.setting_layout, container, false);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TextView title = myView.findViewById(R.id.title_name);
        switch (getArguments().getString("MESSAGE")){
            case "PROFILE" :
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.replace(R.id.setting_manage, profileFragment);
                title.setText("My Profile");
                break;
            case "SETTING" :
                SettingManageFragment settingManageFragment = new SettingManageFragment();
                fragmentTransaction.replace(R.id.setting_manage, settingManageFragment);
                title.setText("My Setting");
                break;
            case "ABOUT_ME" :
                AboutMeFragment aboutMeFragment = new AboutMeFragment();
                fragmentTransaction.replace(R.id.setting_manage, aboutMeFragment);
                title.setText("About Me");
        }
        fragmentTransaction.commit();

        ImageButton logout = myView.findViewById(R.id.setting_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(myshprefs.getString("USER", ""));
        } catch (JSONException e) {
            jsonObject = new JSONObject();
        }
        TextView name = myView.findViewById(R.id.setting_name);
        try {
            name.setText(jsonObject.getString("FIRST_NAME") + " " + jsonObject.getString("LAST_NAME"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myView;
    }
}
