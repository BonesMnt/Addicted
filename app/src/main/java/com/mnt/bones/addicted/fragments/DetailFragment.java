package com.mnt.bones.addicted.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnt.bones.addicted.Movie;
import com.mnt.bones.addicted.R;
import com.squareup.picasso.Picasso;

/**
 * A detail fragment containing more information about Movies.
 */
public class DetailFragment extends Fragment {

    private static final String LOG = DetailFragment.class.getSimpleName();
    private Movie mMovie;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Movie.MOVIE_KEY)){
            mMovie = intent.getParcelableExtra(Movie.MOVIE_KEY);
        }

        Log.v(LOG, mMovie.toString());

        TextView detailTitle = (TextView) rootView.findViewById(R.id.detail_title);
        TextView detailReleaseDate = (TextView) rootView.findViewById(R.id.detail_releaseDate);
        TextView detailRating = (TextView) rootView.findViewById(R.id.detail_rating);
        TextView detailOverview = (TextView) rootView.findViewById(R.id.detail_overview);
        ImageView detailPoster = (ImageView) rootView.findViewById(R.id.detail_poster);

        detailTitle.setText(mMovie.getOriginalTitle());
        detailReleaseDate.setText(mMovie.getReleaseDate());
        detailRating.setText(mMovie.getRating());
        detailOverview.setText(mMovie.getOverview());
        Picasso.with(getContext())
                .load(mMovie.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(detailPoster);



        return rootView;
    }
}
