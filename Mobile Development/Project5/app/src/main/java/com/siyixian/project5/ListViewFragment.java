package com.siyixian.project5;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView =  inflater.inflate(R.layout.list_view_layout, container, false);

        populateListView();

        return myView;
    }

    public void populateListView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SPREF_APP", getActivity().MODE_PRIVATE);
        String[] myLog = sharedPreferences.getString("HISTORY_LOG", "").split(",");
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myLog);
        ListView myListViewer = myView.findViewById(R.id.log_data);
        myListViewer.setAdapter(myListAdapter);
        Log.i("POPULATE", "Success");
    }
}
