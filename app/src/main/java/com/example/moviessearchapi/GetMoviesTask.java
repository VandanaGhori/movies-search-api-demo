package com.example.moviessearchapi;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetMoviesTask extends AsyncTask<String, Void, String> {

    public static final String URL = "https://www.omdbapi.com/?apikey=67452df7&type=movie&s=";
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected String doInBackground(String... params) {
        String result;
        String inputLine;
        HttpURLConnection connection = null;
        URL myUrl;

        try{
            myUrl = new URL(params[0]);

            connection = (HttpURLConnection) myUrl.openConnection();

            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            // Create a new InputStreamReader
            InputStream inputStream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            // Create a BufferedReader and String Builder
            BufferedReader reader = new BufferedReader(streamReader); // Has readLine method for iterating through each line
            StringBuilder stringBuilder = new StringBuilder();

            // Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            // Close our InputStream and BufferedReader
            reader.close();
            streamReader.close();

            // Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = null;
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}

