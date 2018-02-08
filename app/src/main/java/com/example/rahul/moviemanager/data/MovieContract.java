package com.example.rahul.moviemanager.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rahul on 10-07-2017.
 */

public class MovieContract {

    static final String CONTENT_AUTHORITY = "com.example.rahul.moviemanager";
    private static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_MOVIE = "movie";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH_MOVIE);

    public class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_IMAGE_PATH = "image_path_url";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_GENRE = "genre";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE = "release";

    }
}
