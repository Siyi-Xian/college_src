package com.siyixian.project3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent myIntent = getIntent();
        String movieName = myIntent.getStringExtra("Movie_Name");
        ImageView movieImage = findViewById(R.id.movie_detail_image);
        TextView movieSynopsis = findViewById(R.id.movie_synopsis);

        switch (movieName) {
            case "Iron Man" :
                movieImage.setImageResource(R.mipmap.iron_man_detail);
                movieSynopsis.setText(R.string.iron_man_synopsis);
                break;
            case "Titanic" :
                movieImage.setImageResource(R.mipmap.titanic_detail);
                movieSynopsis.setText(R.string.titanic_synopsis);
                break;
            case "The Avengers" :
                movieImage.setImageResource(R.mipmap.avengers_detail);
                movieSynopsis.setText(R.string.avengers_synopsis);
                break;
            case "Interstellar" :
                movieImage.setImageResource(R.mipmap.interstellar_detail);
                movieSynopsis.setText(R.string.interstellar_synopsis);
                break;
        }

        handleButtonOnClickListener(movieName);
    }

    private void handleButtonOnClickListener(final String movieName) {

        Button add_to_favorite = findViewById(R.id.add_to_favorite);
        add_to_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
                Set<String> favoriteMovies = myshprefs.getStringSet("FAVORITE_MOVIES", new TreeSet<String>());
                SharedPreferences.Editor editor = myshprefs.edit();
                editor.clear();
                favoriteMovies.add(movieName);
                editor.putStringSet("FAVORITE_MOVIES", favoriteMovies);
                editor.commit();

                Intent myIntent = new Intent();
                setResult(Activity.RESULT_OK, myIntent);
                finish();

            }
        });

    }
}
