package com.example.rahul.moviemanager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.moviemanager.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 10-07-2017.
 */

public class MovieGridAdapter extends CursorAdapter {

    List<Movies> list;



    public MovieGridAdapter(MainActivity context, List<Movies> list,Cursor cursor) {
        super(context,cursor,0);

        this.list = list;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.main_grid_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView3g);
        TextView name = (TextView)view.findViewById(R.id.textViewg);
        TextView rating = (TextView)view.findViewById(R.id.textView2g);

        TextView releaseDate = (TextView)view.findViewById(R.id.textView3g);

        name.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME)));

        String url = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_IMAGE_PATH));

        releaseDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_RELEASE)));

        Picasso.with(context).load(url).resize(400,600).into(imageView);

        rating.setText(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_RATING)));


    }
}

