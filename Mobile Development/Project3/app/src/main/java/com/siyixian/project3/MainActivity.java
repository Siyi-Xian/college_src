package com.siyixian.project3;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MyMovie> myMovies = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("ERROR_BACK", "Return Canceled");
            return;
        }

        switch (requestCode) {
            case 40 :
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateMyMovie();
        populateCustomListView();

        handleButtonOnClickListener();
    }

    private void handleButtonOnClickListener() {
        Button gotoFavorite = findViewById(R.id.goto_favorite);
        gotoFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, FavoriteMovie.class);
                startActivityForResult(myIntent, 40);
            }
        });
    }

    private void populateCustomListView() {
        myMovies.add(new MyMovie("Iron Man", 7.9, "Fantasy/Science", R.mipmap.iron_man));
        myMovies.add(new MyMovie("Titanic", 7.8, "Drama/Disaster", R.mipmap.titanic));
        myMovies.add(new MyMovie("The Avengers", 8.1, "Fantasy/Science", R.mipmap.avengers));
        myMovies.add(new MyMovie("Interstellar", 8.6, "Drama/Mystery", R.mipmap.interstellar));
    }

    private void populateMyMovie() {

        ArrayAdapter<MyMovie> adapter = new MyListAdapter();
        ListView listView = findViewById(R.id.movie_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String clickName = "";
                switch (position) {
                    case 0:
                        clickName = "Iron Man";
                        break;
                    case 1:
                        clickName = "Titanic";
                        break;
                    case 2:
                        clickName = "The Avengers";
                        break;
                    case 3:
                        clickName = "Interstellar";
                        break;
                }
                //Go to second activity
                Intent myIntent = new Intent(MainActivity.this, MovieDetail.class);
                myIntent.putExtra("Movie_Name", clickName);
                startActivityForResult(myIntent, 40);

            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<MyMovie> {

        public MyListAdapter() {super(MainActivity.this, R.layout.movie_layout, myMovies); }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);

            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.movie_layout, parent, false);

            MyMovie currentContact = myMovies.get(position);

            TextView itemName = itemView.findViewById(R.id.movie_name);
            itemName.setText(currentContact.getMovieName());
            TextView itemNumber = itemView.findViewById(R.id.movie_rating);
            itemNumber.setText(itemNumber.getText().toString()+ currentContact.getMovieRate() + "");
            TextView itemGenre = itemView.findViewById(R.id.movie_genre);
            itemGenre.setText(itemGenre.getText().toString() + currentContact.getMovieGenre());
            ImageView itemIcon = itemView.findViewById(R.id.movie_img);
            itemIcon.setImageResource(currentContact.getMovieImg());
            return itemView;
        }

    }
}

class MyMovie {

    private String movieName;
    private double movieRate;
    private String movieGenre;
    private int movieImg;

    public MyMovie(String movieName, double movieRate, String movieCenre, int movieImg) {
        this.movieName = movieName;
        this.movieRate = movieRate;
        this.movieGenre = movieCenre;
        this.movieImg = movieImg;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public double getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(double movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieCenre(String movieCenre) {
        this.movieGenre = movieCenre;
    }

    public int getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(int movieImg) {
        this.movieImg = movieImg;
    }
}