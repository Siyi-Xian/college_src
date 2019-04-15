package com.c323finalproj.siyixian;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TextDialogFragment extends DialogFragment {

    public interface Callback {
        void onClick(String phone_number);
    }

    private DialogInterface.OnClickListener positiveCallback;

    private DialogInterface.OnClickListener negativeCallback;

    private String title;


    private Callback callback;

    public void show(String title, DialogInterface.OnClickListener negativeCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.negativeCallback = negativeCallback;
        show(fragmentManager, "ButtonDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setNegativeButton("Cancel", negativeCallback);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.text_dialog_layout, null);
        builder.setView(view).setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    String phone_number = ((EditText) view.findViewById(R.id.sms_text_message)).getText().toString();
                    callback.onClick(phone_number);
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }
}