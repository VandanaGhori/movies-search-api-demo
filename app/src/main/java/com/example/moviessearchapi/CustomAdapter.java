package com.example.moviessearchapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Movies> moviesList;
    TextView titleTxtView, yearTxtView;
    ImageView posterImage;

    public CustomAdapter(List<Movies> moviesList, Context context) {
        super();
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Movies movie = moviesList.get(position);

        /*URL url = null;
        try {
            url = new URL(movie.getPoster());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_listview,null);
        titleTxtView = (TextView) view.findViewById(R.id.titleTextView);
        yearTxtView = (TextView) view.findViewById(R.id.yearTextView);
        posterImage = (ImageView) view.findViewById(R.id.moviesImage);
        titleTxtView.setText(movie.getTitle());
        yearTxtView.setText(movie.getYear());
        Glide.with(context).load(movie.getPoster()).into(posterImage);
        //posterImage.setImageResource(movie.getPoster());
       // posterImage.setImageBitmap(bmp);
        return view;
    }
}
