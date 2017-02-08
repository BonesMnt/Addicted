package com.mnt.bones.addicted.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.Review;
import com.mnt.bones.addicted.Trailer;
import com.mnt.bones.addicted.adapters.ReviewAdapter;
import com.mnt.bones.addicted.adapters.TrailerAdapter;
import com.mnt.bones.addicted.interfaces.AsyncTaskDelegate;
import com.mnt.bones.addicted.services.ReviewService;
import com.mnt.bones.addicted.services.TrailerService;
import com.mnt.bones.addicted.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A detail fragment containing more information about Movies.
 */
public class DetailFragment extends Fragment implements AsyncTaskDelegate {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private Movie mMovie;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Movie.MOVIE_KEY)) {
            mMovie = intent.getParcelableExtra(Movie.MOVIE_KEY);
        }

        Log.v(TAG, mMovie.toString());

        TextView detailTitle = (TextView) rootView.findViewById(R.id.detail_title);
        TextView detailReleaseDate = (TextView) rootView.findViewById(R.id.detail_releaseDate);
        TextView detailRating = (TextView) rootView.findViewById(R.id.detail_rating);
        TextView detailOverview = (TextView) rootView.findViewById(R.id.detail_overview);
        ImageView detailPoster = (ImageView) rootView.findViewById(R.id.detail_poster);
        ListView listTrailer = (ListView) rootView.findViewById(R.id.lv_trailers);
        ListView listReview = (ListView) rootView.findViewById(R.id.lv_reviews);

        detailTitle.setText(mMovie.getOriginalTitle());
        detailReleaseDate.setText(mMovie.getReleaseDate());
        detailRating.setText(mMovie.getRating());
        detailOverview.setText(mMovie.getOverview());
        Picasso.with(getContext())
                .load(mMovie.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(detailPoster);

        mTrailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<Trailer>());
        listTrailer.setAdapter(mTrailerAdapter);
        Log.v(TAG, mTrailerAdapter.toString());

        mReviewAdapter = new ReviewAdapter(getActivity(), new ArrayList<Review>());
        listReview.setAdapter(mReviewAdapter);
        Log.v(TAG, mReviewAdapter.toString());


        return rootView;
    }

    private void updateList() {

        if (NetworkUtils.hasInternetConnection(getContext())) {
            String movieId = mMovie.getMovieId();
            TrailerService downloadTrailer = new TrailerService(this);
            downloadTrailer.execute(movieId);

            ReviewService downloadReview = new ReviewService(this);
            downloadReview.execute(movieId);
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

    @Override
    public void onStart() {
        super.onStart();
        updateList();

    }

    @Override
    public void onPreStart() {

    }

    @Override
    public void onFinish(Object output) {

        if (output != null) {
            ArrayList list = (ArrayList) output;
            if (list.get(0) instanceof Review) {
                mReviewAdapter.clear();
                mReviewAdapter.addAll(list);
            } else if (list.get(0) instanceof Trailer) {
                mTrailerAdapter.clear();
                mTrailerAdapter.addAll(list);
            }
        }

    }
}
