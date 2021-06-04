package com.example.moviessearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mMovieEditText;
    Button mSearchButton;
    String searchKey;
    List<Movies> moviesList;
    static ListView mListViewMovies;
    CustomAdapter customAdapter;
    static ImageView mImageViewFavourite;
    LinearLayout linlaHeaderProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        moviesList = new ArrayList<>();
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesList.clear();
                if (validateInput()) {
                    getMoviesTask(searchKey);
                }
                // TODO : API call and collect the data
                bindDataToAdapter();

                //For hiding the keyboard onClick of button
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
        });

        mImageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomAdapter.nominatedMoviesList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No nominations are there.", Toast.LENGTH_LONG).show();
                } else {
                    MyDialog myDialog = new MyDialog();
                    myDialog.show(getSupportFragmentManager(),"test");
                }
            }
        });
    }

    private void getMoviesTask(String searchKey) {
        String result;
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        GetMoviesTask getMoviesTask = new GetMoviesTask();
        try {
            result = getMoviesTask.execute(GetMoviesTask.URL + searchKey).get();
            //Log.i("Format", result);
            if (result.length() != 0) {
                linlaHeaderProgress.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(result);
                String search = jsonObject.getString("Search");
                JSONArray jsonArray = new JSONArray(search);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String title = object.getString("Title");
                    String year = object.getString("Year");
                    String imdbID = object.getString("imdbID");
                    String type = object.getString("Type");
                    String poster = object.getString("Poster");
                    Movies movie = new Movies(title, year, imdbID, type, poster);
                    moviesList.add(movie);
                }

            } else {
                Toast.makeText(this, "Movie name does not match.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Data not found.",Toast.LENGTH_LONG).show();
        }
    }

    private void initialize() {
        mMovieEditText = (EditText) findViewById(R.id.edit_text);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mImageViewFavourite = (ImageView) findViewById(R.id.imageViewFavourite);
    }

    private boolean validateInput() {
        searchKey = mMovieEditText.getText().toString().trim();
        if (searchKey.length() < 1) {
            Toast.makeText(this, "Please enter movie name.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void bindDataToAdapter() {
        mListViewMovies = (ListView) findViewById(R.id.listMovie);
        customAdapter = new CustomAdapter(moviesList,this);
        mListViewMovies.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
}