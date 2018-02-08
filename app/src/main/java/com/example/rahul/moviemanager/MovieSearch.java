package com.example.rahul.moviemanager;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rahul.moviemanager.data.MovieContract;
import com.example.rahul.moviemanager.data.MovieContract.MovieEntry;
import com.example.rahul.moviemanager.data.MovieDbHelper;
import com.example.rahul.moviemanager.MovieAddListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 10-07-2017.
 */

public class MovieSearch extends AppCompatActivity {

    EditText E1;
    Button go;
    MovieAddListAdapter mAdapter;
    ListView MovieList;

    private static String movie_url1 = "https://api.themoviedb.org/3/search/movie?api_key=9d9f52613729baa2b0817925688583c0&query=";

    private static String movie_url;

    ArrayAdapter<Movies> moviesArrayList;

    List<Movies> moviesList=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movies);

        E1=(EditText)findViewById(R.id.movieName);
        go = (Button)findViewById(R.id.go);

        mAdapter = new MovieAddListAdapter(this, (ArrayList<Movies>) moviesList);

        MovieList=(ListView)findViewById(R.id.list);

        MovieList.setAdapter(mAdapter);

        MovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current movie that was clicked on

                ContentValues contentValues = new ContentValues();


                Movies currentMovie = moviesList.get(position);
                Log.v("::",String.valueOf(moviesList.size()));

                contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE_PATH,currentMovie.getUrlImg());
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME,currentMovie.getName() );
                contentValues.put(MovieContract.MovieEntry.COLUMN_RATING,currentMovie.getRating() );
                contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE,currentMovie.getGenre() );
                contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE,currentMovie.getReleaseDate() );
                contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,currentMovie.getOverview());

                getContentResolver().insert(MovieContract.CONTENT_URI,contentValues);

                Intent intent = new Intent(MovieSearch.this,MainActivity.class);
                startActivity(intent);
            }
        });



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String addMovie = E1.getText().toString().trim().toLowerCase();

                String queryMovie="";

                if(addMovie.length()>0)
                {
                    for(int i=0;i<addMovie.length();i++)
                    {
                        if(addMovie.charAt(i)!=' ')
                            queryMovie=queryMovie+addMovie.charAt(i);
                        else
                            queryMovie=queryMovie+"+";
                    }

                    movie_url=movie_url1+queryMovie;
                    MoviesAsyncTask task = new MoviesAsyncTask();
                    task.execute(movie_url);
                }


            }
        });





    }




    private class MoviesAsyncTask extends AsyncTask<String, Void, List<Movies>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Movies}s as the result.
         */
        @Override
        protected List<Movies> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Movies> result = QueryUtils.fetchMoviesData(urls[0]);


            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of pokemon data from a previous
         * query to USGS. Then we update the adapter with the new list of pokemons,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<Movies> data) {
            // Clear the adapter of previous pokemon data
            mAdapter.clear();

            // If there is a valid list of {@link Movies}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                moviesList.addAll(data);
            }
        }
    }
}
