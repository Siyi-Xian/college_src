package com.siyixian.project5;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GestureFragment extends Fragment {

    View myView;
    int lastX, lastY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.gesture_layout, container, false);

        Button sensor = myView.findViewById(R.id.sensor_button);
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SensorActivity.class);
                startActivityForResult(intent, 40);
            }
        });

        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE);
                        String log = sharedPreferences.getString("HISTORY_LOG", "");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.putString("HISTORY_LOG", log + dx + "" + " " + dy + "" + ",");
                        editor.commit();
                        break;
                    default:
                        break;
                }
                ListViewFragment fragment = (ListViewFragment) getActivity().getFragmentManager().findFragmentById(R.id.list_view_fragment);
                fragment.populateListView();
                return true;
            }
        });

        return myView;
    }
}
