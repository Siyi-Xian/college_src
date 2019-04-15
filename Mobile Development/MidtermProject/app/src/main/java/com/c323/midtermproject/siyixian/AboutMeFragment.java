package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutMeFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.about_me_layout, container, false);

        TextView dir = myView.findViewById(R.id.dir_path);
        dir.setText("JSONFile written in External Storage:\n" + getActivity().getExternalCacheDir().getAbsolutePath());

        return myView;
    }
}
