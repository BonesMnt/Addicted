package com.mnt.bones.addicted.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.Review;

import java.util.List;

/**
 * Created by Usuario on 02/02/2017.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();


    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.review_list_item, parent, false);
        }

        Review reviewsList = getItem(position);

        TextView authorReview = (TextView) convertView.findViewById(R.id.tv_review_author);
        TextView contentReview = (TextView) convertView.findViewById(R.id.tv_review_content);

        authorReview.setText(reviewsList.getAuthor());
        contentReview.setText(reviewsList.getContentReview());

        Log.d(TAG, "ReviewAdapter Author: "+reviewsList.getAuthor());

        return convertView;
    }

}
