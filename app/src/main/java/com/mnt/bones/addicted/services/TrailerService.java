package com.mnt.bones.addicted.services;

import android.os.AsyncTask;

import com.mnt.bones.addicted.Trailer;
import com.mnt.bones.addicted.interfaces.AsyncTaskDelegate;
import com.mnt.bones.addicted.utilities.MovieJsonUtils;
import com.mnt.bones.addicted.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Usuario on 06/02/2017.
 */

public class TrailerService extends AsyncTask<String, Void, ArrayList<Trailer>> {

    private static final String TAG = TrailerService.class.getSimpleName();
    private AsyncTaskDelegate delegatedTask;

    public TrailerService(AsyncTaskDelegate delegatedTask) {
        this.delegatedTask = delegatedTask;
    }

    @Override
    protected ArrayList<Trailer> doInBackground(String... params) {

        String movieId = params[0];

        URL requestTrailer = NetworkUtils.createTrailerUrl(movieId);

        try{
            String trailerResponse = NetworkUtils.getResponseFromHttpUrl(requestTrailer);

            ArrayList<Trailer> trailerList = MovieJsonUtils.getTrailerDataFromJson(trailerResponse);

            return trailerList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> trailers) {
        if (trailers != null){
            delegatedTask.onFinish(trailers);
        }
    }
}
