package com.siyixian.project4;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AppointmentFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView =  inflater.inflate(R.layout.appointment_layout, container, false);

        SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE);
        String brief = myshprefs.getString("BRIEF", "");
        String detail = myshprefs.getString("DETAIL", "");
        String[] briefs = brief.split(",");
        final String[] details = detail.split(",");

        String[] myItems = new String[briefs.length - 1];
        for (int i = 1; i < briefs.length; i++)
            myItems[i - 1] = "Appointment " + (i + "") + ": " + briefs[i];

        ArrayAdapter<String> myListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 ,myItems);
        ListView myListViewer = myView.findViewById(R.id.appointments_list);
        myListViewer.setAdapter(myListAdapter);
        myListViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TextView appointmentDetail = myView.findViewById(R.id.appointment_detail);
                appointmentDetail.setText(details[position + 1]);
            }
        });

        return myView;
    }
}
