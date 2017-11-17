package com.mnt.bones.addicted.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Usuario on 06/02/2017.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private static final String TAG = TrailerAdapter.class.getSimpleName();
    private Context mContext;
    private List<Trailer> mTrailerList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView trailerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.iv_trailer);

        }
    }

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        this.mContext = context;
        this.mTrailerList = trailers;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Trailer trailer = mTrailerList.get(position);

        Log.v(TAG, trailer.toString());

        Picasso.with(mContext)
                .load(trailer.getImagePreview())
                .placeholder(R.drawable.placeholder)
                .into(holder.trailerImage);


        holder.trailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trailerKey = trailer.getKey();
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(Trailer.VIDEO_BASE_URL+trailerKey));
                Log.d(TAG, "Trailer Url: " + Trailer.VIDEO_BASE_URL+trailerKey);
                mContext.startActivity(webIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }


}
