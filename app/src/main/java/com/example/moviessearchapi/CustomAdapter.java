package com.example.moviessearchapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Movies> moviesList;
    TextView titleTxtView, yearTxtView;
    ImageView posterImage;
    FloatingActionButton fab;
    static List<Movies> nominatedMoviesList = new ArrayList<>();

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

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_custom_listview,null);
        titleTxtView = (TextView) view.findViewById(R.id.titleTextView);
        yearTxtView = (TextView) view.findViewById(R.id.yearTextView);
        posterImage = (ImageView) view.findViewById(R.id.moviesImage);
        fab = (FloatingActionButton) view.findViewById(R.id.fabButton);
        titleTxtView.setText(movie.getTitle());
        yearTxtView.setText(movie.getYear());

        // Convert image URL into integer
        Glide.with(context).load(movie.getPoster()).into(posterImage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(nominatedMoviesList.contains(movie))) {
                    if (nominatedMoviesList.size() < 5) {
                        nominatedMoviesList.add(movie);
                        MainActivity.mImageViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
                        fab.setEnabled(false);
                        Toast.makeText(context, movie.getTitle() + " is nominated.", Toast.LENGTH_LONG).show();
                    } else {
                        new AlertDialog.Builder(context).setTitle("5 movies are already nominated.").
                                setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }).show();
                    }
                } else {
                    new AlertDialog.Builder(context).setTitle("Movie is already nominated. \nTry to nominate other one.").
                            setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                }
            }
        });

        return view;
    }
}
