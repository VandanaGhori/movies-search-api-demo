package com.example.moviessearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText mMovieEditText;
    Button mSearchButton;
    String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    Toast.makeText(getApplicationContext(),"Search happened.", Toast.LENGTH_LONG).show();
                    getMoviesTask(searchKey);
                }
                // TODO : API call and collect the data
               // Toast.makeText(getApplicationContext(),"Search happened.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMoviesTask(String searchKey) {
        String result;
        GetMoviesTask getMoviesTask = new GetMoviesTask();

        try {
            result = getMoviesTask.execute(GetMoviesTask.URL+searchKey).get();
           // Toast.makeText(this, "List of movies." + result,Toast.LENGTH_LONG).show();
           // Log.i("OUTPUT", result);

            JSONObject jsonObject = new JSONObject(result);
            String search = jsonObject.getString("Search");
            JSONArray jsonArray = new JSONArray(search);

            for(int i =0; i< jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String year = jsonObject.getString("Year");
               // Toast.makeText(getApplicationContext(), "Title " + title + "\nYear " + year,Toast.LENGTH_LONG).show();
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
        if(searchKey.length() < 1) {
            Toast.makeText(this,"Please enter movie name.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}