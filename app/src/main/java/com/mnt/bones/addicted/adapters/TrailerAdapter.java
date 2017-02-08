package com.mnt.bones.addicted.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mnt.bones.addicted.R;
import com.mnt.bones.addicted.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Usuario on 06/02/2017.
 */

public class TrailerAdapter extends ArrayAdapter<Trailer>{

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        super(context, 0, trailers);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.trailer_list_item, parent, false);
        }

        Trailer trailerList = getItem(position);

        Log.v(TAG, trailerList.toString());

        ImageView trailerPreview = (ImageView) convertView.findViewById(R.id.iv_trailer_preview);

        Log.v(TAG, "Image Preview: "+ trailerList.getImagePreview());

        Picasso.with(convertView.getContext())
                .load(trailerList.getImagePreview())
                .into(trailerPreview);

        return convertView;
    }
}
