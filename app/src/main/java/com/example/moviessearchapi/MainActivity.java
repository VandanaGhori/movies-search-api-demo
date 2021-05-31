package com.example.moviessearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mMovieEditText;
    Button mSearchButton;
    String searchKey;
    List<Movies> moviesList;
    ListView mListViewMovies;
    CustomAdapter customAdapter;

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
                    //customAdapter.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(),"Search happened.", Toast.LENGTH_LONG).show();
                    getMoviesTask(searchKey);
                }
                // TODO : API call and collect the data
                // Toast.makeText(getApplicationContext(),"Search happened.", Toast.LENGTH_LONG).show();
                bindDataToAdapter();

                //For hiding the keyboard onClick of button
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
        });

    }

    private void getMoviesTask(String searchKey) {
        String result;
        GetMoviesTask getMoviesTask = new GetMoviesTask();

        try {
            result = getMoviesTask.execute(GetMoviesTask.URL + searchKey).get();
            // Toast.makeText(this, "List of movies." + result,Toast.LENGTH_LONG).show();
            // Log.i("OUTPUT", result);

            if (result.length() != 0) {
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
            //Toast.makeText(this, "Search." + search,Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        mMovieEditText = findViewById(R.id.edit_text);
        mSearchButton = findViewById(R.id.search_button);
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
        //Toast.makeText(this,"Hello Bind Adapter.", Toast.LENGTH_LONG).show();
        mListViewMovies = (ListView) findViewById(R.id.listMovie);
        customAdapter = new CustomAdapter(moviesList, this);
        mListViewMovies.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
}