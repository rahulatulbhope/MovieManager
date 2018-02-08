package com.example.rahul.moviemanager;

/**
 * Created by Rahul on 10-07-2017.
 */

public class Movies {

    private String name;
    private String rating;
    private String urlImg;
    private String genre;
    private String overview;
    private String releaseDate;

    public Movies(String name, String rating, String urlImg, String genre, String overview, String releaseDate) {
        this.name = name;
        this.rating = rating;
        this.urlImg = urlImg;
        this.genre = genre;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



}
