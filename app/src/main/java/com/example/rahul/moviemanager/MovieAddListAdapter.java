package com.example.rahul.moviemanager;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahul.moviemanager.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 10-07-2017.
 */

public class MovieAddListAdapter extends ArrayAdapter<Movies> {

    public MovieAddListAdapter(Context context, ArrayList<Movies> movie) {
        super(context, 0, movie);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_add_list_item, parent, false);
        }

        Movies currentMovie = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.textView1);
        TextView rating = (TextView) listItemView.findViewById(R.id.textView2);
        TextView genre = (TextView) listItemView.findViewById(R.id.textView4);
        TextView release = (TextView) listItemView.findViewById(R.id.textView5);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);

        String nameMov = currentMovie.getName();

        name.setText("Name: "+nameMov);

        String ratingMov = currentMovie.getRating();

        rating.setText("Rating: "+ratingMov);


        String genreMov = currentMovie.getGenre();

        genre.setText("Genre: "+genreMov);

        String releaseMov = currentMovie.getReleaseDate();

        release.setText("Release Date: "+releaseMov);



        String url = currentMovie.getUrlImg();

        String fUrl="https://image.tmdb.org/t/p/w500"+url;

        Picasso.with(getContext()).load(fUrl).resize(400,600).into(imageView);



        return listItemView;


    }


    public void insertData(int position)
    {
        ContentValues contentValues = new ContentValues();

        Movies currentMovie = getItem(position);

        contentValues.put(MovieEntry.COLUMN_IMAGE_PATH,currentMovie.getUrlImg() );
        contentValues.put(MovieEntry.COLUMN_NAME,currentMovie.getName() );
        contentValues.put(MovieEntry.COLUMN_RATING,currentMovie.getRating() );
        contentValues.put(MovieEntry.COLUMN_GENRE,currentMovie.getGenre() );
        contentValues.put(MovieEntry.COLUMN_RELEASE,currentMovie.getReleaseDate() );
        contentValues.put(MovieEntry.COLUMN_OVERVIEW,currentMovie.getOverview() );
    }
}
