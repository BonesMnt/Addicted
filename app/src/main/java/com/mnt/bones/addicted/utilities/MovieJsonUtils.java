package com.mnt.bones.addicted.utilities;

import android.content.Context;
import android.util.Log;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.Review;
import com.mnt.bones.addicted.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle JSON data.
 */

public class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();

    /**
     * This method parses JSON from a web response and returns an ArrayList of Movie
     *
     * @param moviesJsonStr JSON response from server
     * @return ArrayList of Movie with all related data
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<Movie> getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        ArrayList<Movie> movieList = new ArrayList<>();

        //JSON objects
        final String OWN_RESULTS = "results";
        final String OWN_POSTER = "poster_path";
        final String OWN_OVERVIEW = "overview";
        final String OWN_RELEASE_DATE = "release_date";
        final String OWN_RATING = "vote_average";
        final String OWN_MOVIE_ID = "id";
        final String OWN_ORIGINAL_TITLE = "original_title";

        JSONObject jsonData = new JSONObject(moviesJsonStr);
        JSONArray movieArray = jsonData.getJSONArray(OWN_RESULTS);

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movieJson = movieArray.getJSONObject(i);

            String poster = movieJson.optString(OWN_POSTER);
            String overview = movieJson.optString(OWN_OVERVIEW);
            String movieId = movieJson.optString(OWN_MOVIE_ID);
            String rating = "" + movieJson.getDouble(OWN_RATING);
            String releaseDate = movieJson.optString(OWN_RELEASE_DATE);
            String originalTitle = movieJson.optString(OWN_ORIGINAL_TITLE);

            String[] releaseYear = releaseDate.split("-");

            Movie movieObject = new Movie(movieId, originalTitle, poster, overview, rating, releaseYear[0]);

            Log.v(TAG, movieObject.toString());

            movieList.add(movieObject);

        }

        return movieList;

    }


    public static ArrayList<Review> getReviewDataFromJson(String reviewJsonStr)
            throws JSONException {

        ArrayList<Review> reviewList = new ArrayList<>();

        //JSON objects
        final String OWN_RESULTS = "results";
        final String OWN_AUTHOR = "author";
        final String OWN_CONTENT = "content";


        JSONObject jsonData = new JSONObject(reviewJsonStr);
        JSONArray reviewArray = jsonData.getJSONArray(OWN_RESULTS);

        for (int i = 0; i < reviewArray.length(); i++) {

            JSONObject reviewJson = reviewArray.getJSONObject(i);

            String author = reviewJson.optString(OWN_AUTHOR);
            String content = reviewJson.optString(OWN_CONTENT);

            Review reviewObject = new Review(author, content);

            Log.v(TAG, reviewObject.toString());

            reviewList.add(reviewObject);

        }

        return reviewList;

    }

    public static ArrayList<Trailer> getTrailerDataFromJson(String reviewJsonStr)
            throws JSONException {

        ArrayList<Trailer> trailerList = new ArrayList<>();

        //JSON objects
        final String OWN_RESULTS = "results";
        final String OWN_KEY = "key";
        final String OWN_NAME = "name";
        final String OWN_TYPE = "type";


        JSONObject jsonData = new JSONObject(reviewJsonStr);
        JSONArray trailerArray = jsonData.getJSONArray(OWN_RESULTS);

        for (int i = 0; i < trailerArray.length(); i++) {

            JSONObject trailerJson = trailerArray.getJSONObject(i);

            String key = trailerJson.optString(OWN_KEY);
            String name = trailerJson.optString(OWN_NAME);
            String type = trailerJson.optString(OWN_TYPE);

            if (type.equals("Trailer")){
                Trailer trailerObject = new Trailer(key, name);

                Log.v(TAG, trailerObject.toString());

                trailerList.add(trailerObject);
            }

        }

        return trailerList;

    }

}
