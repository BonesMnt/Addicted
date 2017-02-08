package com.mnt.bones.addicted.services;

import android.os.AsyncTask;

import com.mnt.bones.addicted.Review;
import com.mnt.bones.addicted.interfaces.AsyncTaskDelegate;
import com.mnt.bones.addicted.utilities.MovieJsonUtils;
import com.mnt.bones.addicted.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Usuario on 02/02/2017.
 */

public class ReviewService extends AsyncTask<String, Void, ArrayList<Review>> {

    private static final String TAG = ReviewService.class.getSimpleName();
    private AsyncTaskDelegate delegatedTask;

    public ReviewService(AsyncTaskDelegate delegatedTask) {
        this.delegatedTask = delegatedTask;
    }

    @Override
    protected ArrayList<Review> doInBackground(String... params) {

        String movieId = params[0];
        URL requestReview = NetworkUtils.createReviewUrl(movieId);

        try{
            String reviewResponse =
                    NetworkUtils.getResponseFromHttpUrl(requestReview);

            ArrayList<Review> reviewList = MovieJsonUtils.getReviewDataFromJson(reviewResponse);

            return reviewList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<Review> movieList) {
        if (movieList != null) {
            delegatedTask.onFinish(movieList);
        }
    }
}
