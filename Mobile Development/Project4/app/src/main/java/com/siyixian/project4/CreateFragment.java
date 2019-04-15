package com.siyixian.project4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CreateFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        myView = inflater.inflate(R.layout.create_layout, container, false);

        Button creatAppointmentButton = myView.findViewById(R.id.add_appointment);
        creatAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView firstName = myView.findViewById(R.id.first_name);
                TextView lastName = myView.findViewById(R.id.last_name);
                TextView email = myView.findViewById(R.id.email);
                TextView agenda = myView.findViewById(R.id.agenda);
                TextView date = myView.findViewById(R.id.date);
                TextView time = myView.findViewById(R.id.time);
                //Save in Shared Preferences
                SharedPreferences myshprefs = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = myshprefs.edit();
                String brief = myshprefs.getString("BRIEF", "");
                String detail = myshprefs.getString("DETAIL", "");

                brief += "," + firstName.getText().toString();
                detail += ",First Name: " + firstName.getText().toString() +
                        "\nLast Name: " + lastName.getText().toString() +
                        "\nEmail: " + email.getText().toString() +
                        "\nAgenda: " + agenda.getText().toString() +
                        "\nDate: " + date.getText().toString() +
                        "\nTime: " + time.getText().toString();

                editor.clear();
                editor.putString("BRIEF", brief);
                editor.putString("DETAIL", detail);
                editor.commit();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AppointmentFragment appointmentFragment = new AppointmentFragment();
                fragmentTransaction.replace(R.id.linearLayout_Main, appointmentFragment);
                fragmentTransaction.commit();
            }
        });

        return myView;
    }
}
