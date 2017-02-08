package com.mnt.bones.addicted.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.mnt.bones.addicted.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to handle communicate with servers.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";

    private static final String API_KEY_PARAM = "api_key";

    private static final String REVIEW_PATH = "reviews";

    private static final String TRAILER_PATH = "videos";

    /**
     * Builds the URL used to talk to the movieDB server using a sort criteria.
     *
     * @param params The sort preference that will be queried for.
     * @return The URL to use to query the movie server.
     */
    public static URL createAndUpdateUrl(String params) {

        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(params)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .build();
        Log.d(TAG, "Built URI = "+ builtUri);
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Built URL = " + url);
        return url;
    }

    public static URL createReviewUrl(String params) {
        // https://api.themoviedb.org/3/movie/297761/reviews?api_key=
        //String
        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(params)
                .appendPath(REVIEW_PATH)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .build();
        Log.d(TAG, "Built Review URI = "+ builtUri);
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Built Review URL = " + url);
        return url;
    }

    public static URL createTrailerUrl(String params) {
        // https://api.themoviedb.org/3/movie/297761/videos?api_key=
        //String
        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(params)
                .appendPath(TRAILER_PATH)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIEDB_API_KEY)
                .build();
        Log.d(TAG, "Built Review URI = "+ builtUri);
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Built Review URL = " + url);
        return url;
    }



    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null){
                return null;
            }

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * This method checks if there is Internet Connection and return it.
     *
     * @param context use to get connectivity service status
     * @return the status .
     */
    public static boolean hasInternetConnection(Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


}
