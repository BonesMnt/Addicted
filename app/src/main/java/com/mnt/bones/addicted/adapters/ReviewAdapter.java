package com.mnt.bones.addicted.adapters;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.Review;

import java.util.List;

/**
 * Created by Usuario on 02/02/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private Context mContext;
    private List<Review> mReviewList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView reviewAuthor;
        public TextView reviewContent;
        public CardView reviewCard;
        public ImageView reviewImage;

        public ViewHolder (View itemView){
            super(itemView);
            reviewCard = (CardView) itemView.findViewById(R.id.cv_review_card);
            reviewAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
            reviewContent = (TextView) itemView.findViewById(R.id.tv_review_content);
            reviewImage = (ImageView) itemView.findViewById(R.id.iv_review_image);
        }

    }

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.mContext = context;
        this.mReviewList = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Review review = mReviewList.get(position);

        Log.v(TAG, review.toString());
        holder.reviewAuthor.setText(review.getAuthor());
        holder.reviewContent.setVisibility(View.GONE);
        holder.reviewContent.setText(review.getContentReview());
        holder.reviewImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_black));
        holder.reviewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.reviewContent.getVisibility() == View.GONE) {
                    holder.reviewContent.setVisibility(View.VISIBLE);
                    TransitionManager.beginDelayedTransition(holder.reviewCard);
                    holder.reviewImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_less_black));
                } else{
                    holder.reviewContent.setVisibility(View.GONE);
                    TransitionManager.beginDelayedTransition(holder.reviewCard);
                    holder.reviewImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_more_black));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}
