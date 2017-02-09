package com.mnt.bones.addicted.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.List;

/**
 * A detail fragment containing more information about Movies.
 */
public class DetailFragment extends Fragment implements AsyncTaskDelegate {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private Movie mMovie;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private List<Trailer> mTrailerList;
    private List<Review> mReviewList;
    private ProgressBar mTrailerProgress;
    private ProgressBar mReviewProgress;
    private RecyclerView mReviewRecycler;
    private RecyclerView mTrailerRecycler;
    private TextView mTrailerError;
    private TextView mReviewError;

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
        mTrailerProgress = (ProgressBar) rootView.findViewById(R.id.pb_loading_trailer);
        mReviewProgress = (ProgressBar) rootView.findViewById(R.id.pb_loading_review);
        mTrailerError = (TextView) rootView.findViewById(R.id.tv_error_trailer);
        mReviewError = (TextView) rootView.findViewById(R.id.tv_error_review);

        detailTitle.setText(mMovie.getOriginalTitle());
        detailReleaseDate.setText(mMovie.getReleaseDate());
        detailRating.setText(mMovie.getRating());
        detailOverview.setText(mMovie.getOverview());
        Picasso.with(getContext())
                .load(mMovie.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(detailPoster);

        mTrailerRecycler = (RecyclerView) rootView.findViewById(R.id.rv_trailers);
        mReviewRecycler = (RecyclerView) rootView.findViewById(R.id.rv_reviews);

        mTrailerRecycler.setVisibility(View.GONE);
        mReviewRecycler.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManagerTrailer = new GridLayoutManager(getContext(), 2);
        mTrailerRecycler.setLayoutManager(layoutManagerTrailer);

        mTrailerList = new ArrayList<Trailer>();

        mTrailerAdapter = new TrailerAdapter(getActivity(), mTrailerList);
        mTrailerRecycler.setAdapter(mTrailerAdapter);
        Log.v(TAG, mTrailerAdapter.toString());

        RecyclerView.LayoutManager layoutManagerReview = new LinearLayoutManager(getContext());
        mReviewRecycler.setLayoutManager(layoutManagerReview);

        mReviewList = new ArrayList<>();

        mReviewAdapter = new ReviewAdapter(getActivity(), mReviewList);
        mReviewRecycler.setAdapter(mReviewAdapter);
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
        mTrailerProgress.setVisibility(View.VISIBLE);
        mReviewProgress.setVisibility(View.VISIBLE);
        mTrailerError.setVisibility(View.GONE);
        mReviewError.setVisibility(View.GONE);
        mTrailerRecycler.setVisibility(View.GONE);
        mReviewRecycler.setVisibility(View.GONE);
    }

    @Override
    public void onFinish(Object output) {

        if (output != null) {
            mTrailerProgress.setVisibility(View.GONE);
            mReviewProgress.setVisibility(View.GONE);
            ArrayList downloadedList = (ArrayList) output;
            if(downloadedList.size() > 0) {
                if (downloadedList.get(0) instanceof Review) {
                    mReviewRecycler.setVisibility(View.VISIBLE);
                    mReviewList.clear();
                    mReviewList.addAll(downloadedList);
                    mReviewAdapter.notifyDataSetChanged();
                } else if (downloadedList.get(0) instanceof Trailer) {
                    mTrailerRecycler.setVisibility(View.VISIBLE);
                    mTrailerList.clear();
                    mTrailerList.addAll(downloadedList);
                    mTrailerAdapter.notifyDataSetChanged();
                }
            } else{
                if (mReviewList.size() == 0){
                    mReviewError.setVisibility(View.VISIBLE);
                } else if (mTrailerList.size() == 0){
                    mTrailerError.setVisibility(View.VISIBLE);
                }

            }
        }

    }
}
