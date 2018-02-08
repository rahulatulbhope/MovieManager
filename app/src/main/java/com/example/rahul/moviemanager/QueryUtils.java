package com.example.rahul.moviemanager;

import android.graphics.Movie;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 10-07-2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Movie} objects.
     */
    public static List<Movies> fetchMoviesData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Movies> movies = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Movie}s
        return movies;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movies} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Movies> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        List<Movies> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONArray baseJsonArray = baseJsonResponse.getJSONArray("results");

            for(int i=0;i<baseJsonArray.length();i++) {

                JSONObject movieSearch = baseJsonArray.getJSONObject(i);

                String name = movieSearch.getString("original_title");
                String rating=movieSearch.getString("vote_average");
                String overview = movieSearch.getString("overview");
                String releaseDate = movieSearch.getString("release_date");
                String url = movieSearch.getString("poster_path");

                JSONArray genreList = movieSearch.getJSONArray("genre_ids");

                String genre="";

                for(int j=0;j<genreList.length();j++)
                {
                    if(genreList.getInt(j)==28)
                        genre+="Action ";

                    if(genreList.getInt(j)==12)
                        genre+="Adventure ";

                    if(genreList.getInt(j)==16)
                        genre+="Animation ";

                    if(genreList.getInt(j)==35)
                        genre+="Comedy ";

                    if(genreList.getInt(j)==80)
                        genre+="Crime ";

                    if(genreList.getInt(j)==99)
                        genre+="Documentary ";

                    if(genreList.getInt(j)==18)
                        genre+="Drama ";

                    if(genreList.getInt(j)==10751)
                        genre+="Family ";

                    if(genreList.getInt(j)==14)
                        genre+="Fantasy ";

                    if(genreList.getInt(j)==36)
                        genre+="History ";

                    if(genreList.getInt(j)==27)
                        genre+="Horror ";

                    if(genreList.getInt(j)==10402)
                        genre+="Music ";

                    if(genreList.getInt(j)==9648)
                        genre+="Mystery ";

                    if(genreList.getInt(j)==10749)
                        genre+="Romance ";

                    if(genreList.getInt(j)==878)
                        genre+="Science Fiction ";

                    if(genreList.getInt(j)==10770)
                        genre+="TV Movie ";

                    if(genreList.getInt(j)==53)
                        genre+="Thriller ";

                    if(genreList.getInt(j)==10752)
                        genre+="War ";

                    if(genreList.getInt(j)==37)
                        genre+="Western ";
                }


                // Extract the JSONArray associated with the key called "features",
                // which represents a list of features (or movies).



                Movies movie = new Movies(name, rating, url, genre, overview, releaseDate);

                // Add the new {@link Movie} to the list of movies.
                movies.add(movie);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }

        // Return the list of movies
        return movies;
    }
}
