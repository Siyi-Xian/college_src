package com.c323.midtermproject.siyixian;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowProfileFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.show_profile_layout, container, false);

        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", Context.MODE_PRIVATE);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(myshprefs.getString("USER", ""));
        } catch (JSONException e) {
            jsonObject = new JSONObject();
        }
        TextView height = myView.findViewById(R.id.profile_height);
        TextView weight = myView.findViewById(R.id.profile_weight);
        TextView bloodGroup = myView.findViewById(R.id.profile_blood_group);
        TextView EmergencyContact = myView.findViewById(R.id.profile_emergency_contact);
        try {
            height.setText(jsonObject.getString("HEIGHT"));
            weight.setText(jsonObject.getString("WEIGHT"));
            bloodGroup.setText(jsonObject.getString("BLOOD_GROUP"));
            EmergencyContact.setText(jsonObject.getString("EMERGENCY_CONTACT"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myView;
    }
}
