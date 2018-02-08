package com.example.rahul.moviemanager;

import android.content.Intent;
import android.database.Cursor;
import android.content.ContentUris;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.CursorLoader;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.rahul.moviemanager.data.MovieContract;
import com.example.rahul.moviemanager.data.MovieContract.MovieEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton F1;
    GridView Grid;

    MovieGridAdapter mAdapter;
    private static final int LOADER_ID = 1000;

    List<Movies> mMovies = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        F1=(FloatingActionButton)findViewById(R.id.add_fab);
        Grid=(GridView)findViewById(R.id.grid);


        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        F1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                startActivity(new Intent(com.example.rahul.moviemanager.MainActivity.this, MovieSearch.class));


            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MovieEntry.COLUMN_ID,
                MovieEntry.COLUMN_IMAGE_PATH,
                MovieEntry.COLUMN_NAME,
                MovieEntry.COLUMN_RATING,
                MovieEntry.COLUMN_GENRE,
                MovieEntry.COLUMN_RELEASE,
                MovieEntry.COLUMN_OVERVIEW
        };
        return new CursorLoader(com.example.rahul.moviemanager.MainActivity.this, MovieContract.CONTENT_URI, projection, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Movies> list = new ArrayList<>();

        if(cursor != null) {

            while (cursor.moveToNext())
            {
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_IMAGE_PATH));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_RELEASE));
                String numOfComments = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_RATING));
                list.add(new Movies(url, title, numOfComments,null,null,date));
            }
            mAdapter = new MovieGridAdapter(this, list,cursor);
            Grid.setAdapter(mAdapter);
        }
        else
            Toast.makeText(this,"No offline articles found", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
