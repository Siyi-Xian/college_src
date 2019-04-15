package com.siyixian.project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.TreeSet;

public class FavoriteMovie extends AppCompatActivity {

    private String[] movieName;
    private Set<String> favoriteMovies, searchMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);

        Intent myIntent = getIntent();
        SharedPreferences myshprefs = getSharedPreferences("SPREF_APP", MODE_PRIVATE);
        favoriteMovies = myshprefs.getStringSet("FAVORITE_MOVIES", new TreeSet<String>());
        movieName = favoriteMovies.toArray(new String[favoriteMovies.size()]);

        populateListViewer();
        handleButtonOnClickListener();
    }

    private void handleButtonOnClickListener() {

        Button searchButton = findViewById(R.id.search_button);

        searchMovies = new TreeSet<String>();
        searchMovies.addAll(favoriteMovies);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText genreKeywords = findViewById(R.id.search_genre);
                String genre = genreKeywords.getText().toString();
                if ("Fantasy/Science".toLowerCase().contains(genre.toLowerCase())) {
                    searchMovies.remove("Titanic");
                    searchMovies.remove("Interstellar");
                    Toast.makeText(FavoriteMovie.this, "Fantasy/Science", Toast.LENGTH_SHORT).show();
                }
                else if ("Drama".toLowerCase().contains(genre.toLowerCase())) {
                    searchMovies.remove("Iron Man");
                    searchMovies.remove("The Avengers");
                    Toast.makeText(FavoriteMovie.this, "Drama", Toast.LENGTH_SHORT).show();
                }
                else if ("Drama/Disaster".toLowerCase().contains(genre.toLowerCase())) {
                    searchMovies.remove("Interstellar");
                    searchMovies.remove("Iron Man");
                    searchMovies.remove("The Avengers");
                    Toast.makeText(FavoriteMovie.this, "Drama/Disaster", Toast.LENGTH_SHORT).show();
                }
                else if ("Drama/Mystery".toLowerCase().contains(genre.toLowerCase())) {
                    searchMovies.remove("Titanic");
                    searchMovies.remove("Iron Man");
                    searchMovies.remove("The Avengers");
                    Toast.makeText(FavoriteMovie.this, "Drama/Mystery", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FavoriteMovie.this, "Error! Please enter a correct genre!", Toast.LENGTH_SHORT).show();
                }

                movieName = searchMovies.toArray(new String[searchMovies.size()]);
                populateListViewer();
            }
        });

    }

    private void populateListViewer() {
        ArrayAdapter<String> myListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieName);
        ListView myListViewer = findViewById(R.id.favorite_list);
        myListViewer.setAdapter(myListAdapter);
    }
}
