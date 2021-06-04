package com.example.moviessearchapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    Context context;
    List<Movies> moviesList;
    TextView titleTxtView, yearTxtView;
    ImageView posterImage;
    AppCompatImageButton addButton;
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
        addButton = (AppCompatImageButton) view.findViewById(R.id.addButton);

        if((nominatedMoviesList.contains(movie))) {
            addButton.setEnabled(false);
            addButton.setBackgroundColor(Color.LTGRAY);
        }
        else {
            addButton.setEnabled(true);
            addButton.setBackgroundColor(ContextCompat.getColor(context,R.color.purple_500));
        }

        titleTxtView.setText(movie.getTitle());
        yearTxtView.setText(movie.getYear());

        // Convert image URL into integer
        Glide.with(context).load(movie.getPoster()).into(posterImage);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(nominatedMoviesList.contains(movie))) {
                    if (nominatedMoviesList.size() < 5) {
                        nominatedMoviesList.add(movie);

                        // Updated data set get notified to adapter each time button is clicked
                        notifyDataSetChanged();
                        // Filled favorite icon with color upon nominating movie
                        MainActivity.mImageViewFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
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
