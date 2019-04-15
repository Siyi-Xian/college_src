package com.siyixian.project5;

import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SensorActivity extends AppCompatActivity {

    private int lastX, lastY;
    private  Matrix mMatrix=new Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        final ImageView imageView = findViewById(R.id.ball);

        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        String[] move = sharedPreferences.getString("HISTORY_LOG", "").split(",");
        String[] last_move = move[move.length - 1].split(" ");
        try {
            int move_x = Integer.parseInt(last_move[0]);
            int move_y = Integer.parseInt(last_move[1]);
            mMatrix.postTranslate(move_x, move_y);
            imageView.setImageMatrix(mMatrix);
        } catch (Exception e) {
            Log.i("LOG", "No Previous");
        }

        imageView.setOnTouchListener(new View.OnTouchListener() {
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
                        SharedPreferences sharedPreferences = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                        String log = sharedPreferences.getString("HISTORY_LOG", "");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.putString("HISTORY_LOG", log + dx + "" + " " + dy + "" + ",");
                        editor.commit();

                        mMatrix.postTranslate(dx, dy);
                        imageView.setImageMatrix(mMatrix);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
