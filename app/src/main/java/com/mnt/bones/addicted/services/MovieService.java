package com.mnt.bones.addicted.services;

import android.os.AsyncTask;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.interfaces.AsyncTaskDelegate;
import com.mnt.bones.addicted.utilities.MovieJsonUtils;
import com.mnt.bones.addicted.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * A service to download and pass Movie data
 */

public class MovieService extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final String TAG = MovieService.class.getSimpleName();
    private AsyncTaskDelegate delegatedTask;

    public MovieService(AsyncTaskDelegate responder) {
        this.delegatedTask = responder;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        delegatedTask.onPreStart();
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        String orderPref = params[0];
        URL requestUrl = NetworkUtils.createAndUpdateUrl(orderPref);

        try {
            String jsonResponse =
                    NetworkUtils.getResponseFromHttpUrl(requestUrl);

            ArrayList<Movie> movieList = MovieJsonUtils.getMovieDataFromJson(jsonResponse);
            return movieList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieList) {
        if (movieList != null) {
            delegatedTask.onFinish(movieList);
        }
    }
}