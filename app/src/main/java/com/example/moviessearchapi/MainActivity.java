package com.example.moviessearchapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                }
                // TODO : API call and collect the data
               // Toast.makeText(getApplicationContext(),"Search happened.", Toast.LENGTH_LONG).show();
            }
        });
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