package com.mnt.bones.addicted.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.activities.DetailActivity;
import com.mnt.bones.addicted.adapters.MovieAdapter;
import com.mnt.bones.addicted.interfaces.AsyncTaskDelegate;
import com.mnt.bones.addicted.services.MovieService;
import com.mnt.bones.addicted.utilities.NetworkUtils;

import java.util.ArrayList;

/**
 * Main Fragment contains a grid view.
 */
public class MainActivityFragment extends Fragment implements AsyncTaskDelegate {

    public MovieAdapter mMovieAdapter;

    private GridView mGridView;
    private ProgressBar mLoadingBar;
    private ArrayList<Movie> mMovieList;

    private static final String ON_SAVED_INSTANCE = "MainActivityCallback";

    private final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) view.findViewById(R.id.gv_posters);
        mLoadingBar = (ProgressBar) view.findViewById(R.id.pb_loading);


        if (savedInstanceState != null){
            Log.d(TAG, "Restoring Saved Instance");
            mMovieList = savedInstanceState.getParcelableArrayList(ON_SAVED_INSTANCE);
        } else{
            Log.d(TAG, "No Saved Instance");
            mMovieList = new ArrayList<Movie>();
        }

        mMovieAdapter = new MovieAdapter(getActivity(), mMovieList);
        mGridView.setAdapter(mMovieAdapter);

        Log.v(TAG, mMovieAdapter.toString());

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Movie.MOVIE_KEY, mMovieAdapter.getItem(position));
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateList() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = preferences.getString(getString(R.string.pref_key),
                getString(R.string.pref_sort_popular));
        MovieService downloadMovieData = new MovieService(getContext(), this);

        if (sortBy.equals(getString(R.string.pref_sort_favorite))) {
            //Toast.makeText(getContext(), "tryed to load pref= "+sortBy, Toast.LENGTH_LONG).show();
            downloadMovieData.execute(sortBy);
        } else{
            if (NetworkUtils.hasInternetConnection(getContext())) {
                downloadMovieData.execute(sortBy);
            } else {
                View view = getActivity().findViewById(R.id.container);
                Snackbar alertSnackbar = Snackbar.make(view, getString(R.string.no_internet_alert), Snackbar.LENGTH_LONG);
                alertSnackbar.setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateList();
                    }
                });
                alertSnackbar.show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPreStart() {
        mLoadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish(Object output) {
        mLoadingBar.setVisibility(View.INVISIBLE);
        if (output != null){
            mMovieAdapter.clear();
            ArrayList<Movie> movieList = (ArrayList<Movie>) output;
            mMovieAdapter.addAll(movieList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovieList != null){
            Log.d(TAG, "Saving");
            outState.putParcelableArrayList(ON_SAVED_INSTANCE, mMovieList);
        }
    }
}
