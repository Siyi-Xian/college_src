package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.profile_layout, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowProfileFragment showProfileFragment = new ShowProfileFragment();
        fragmentTransaction.replace(R.id.profile_detail, showProfileFragment);
        fragmentTransaction.commit();

        ImageButton editProfile = myView.findViewById(R.id.profile_edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                fragmentTransaction.replace(R.id.profile_detail, editProfileFragment);
                fragmentTransaction.commit();
            }
        });

        return myView;
    }
}
