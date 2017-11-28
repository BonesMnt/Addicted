package com.mnt.bones.addicted.services;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.data.AddictedContract;
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
    private Context mContext;
    private AsyncTaskDelegate delegatedTask;

    public MovieService(Context context, AsyncTaskDelegate responder) {
        this.mContext = context;
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
        Log.d(TAG, "orderPref= "+ orderPref);

        if (!orderPref.equals(mContext.getString(R.string.pref_sort_favorite))) {
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
        } else {

            try {
                Cursor cursor = mContext.getContentResolver()
                        .query(AddictedContract.MoviesEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);

                ArrayList<Movie> movieList = MovieJsonUtils.getMoiveFromDB(cursor);
                cursor.close();
                return movieList;
            } catch (Exception e){
                Log.e(TAG, "Failed to load data");
                e.printStackTrace();
                return null;
            }

        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movieList) {
        if (movieList != null) {
            delegatedTask.onFinish(movieList);
        }
    }
}