package com.example.moviessearchapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        StringBuffer stringBuffer = new StringBuffer();

        for(Movies movies:CustomAdapter.nominatedMoviesList) {
            stringBuffer.append("\n" + movies.getTitle() + " " + movies.getYear());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("Nominated Movies List : \n" + stringBuffer)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
        return builder.create();
    }
}
