package com.siyixian.project5;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListViewFragment listViewFragment = new ListViewFragment();
        fragmentTransaction.replace(R.id.list_view_fragment, listViewFragment);
//        fragmentTransaction.commit();

        GestureFragment gestureFragment = new GestureFragment();
        fragmentTransaction.replace(R.id.gesture_fragment, gestureFragment);
        fragmentTransaction.commit();
    }
}
