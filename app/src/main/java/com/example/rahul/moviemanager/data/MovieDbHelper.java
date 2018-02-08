package com.example.rahul.moviemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rahul.moviemanager.data.MovieContract.MovieEntry;

/**
 * Created by Rahul on 10-07-2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "caption";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MovieEntry.COLUMN_IMAGE_PATH + " TEXT," +
                    MovieEntry.COLUMN_NAME + " TEXT,"+
                    MovieEntry.COLUMN_RATING+ " TEXT,"+
                    MovieEntry.COLUMN_GENRE+ " TEXT,"+
                    MovieEntry.COLUMN_RELEASE+ " TEXT,"+
                    MovieEntry.COLUMN_OVERVIEW+ " TEXT );";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
