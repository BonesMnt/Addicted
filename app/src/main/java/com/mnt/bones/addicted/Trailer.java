package com.mnt.bones.addicted;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 06/02/2017.
 */

public class Trailer implements Parcelable{

    private static final String IMAGE_PATH = "http://img.youtube.com/vi/";
    private static final String IMAGE_SUFIX = "/0.jpg";
    public static final String VIDEO_BASE_URL = "http://m.youtube.com/watch?v=";

    private String key;
    private String title;
    private String imagePreview;


    public Trailer(String key, String title) {
        this.key = key;
        this.title = title;
        this.imagePreview = key;
    }

    private Trailer(Parcel input){
        this.key = input.readString();
        this.title = input.readString();
        this.imagePreview = input.readString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePreview() {
        return IMAGE_PATH + imagePreview + IMAGE_SUFIX;
    }

    public void setImagePreview(String imagePreview) {
        this.imagePreview = imagePreview;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", imagePreview='" + imagePreview + '\'' +
                ", Trailer URL='" + Trailer.VIDEO_BASE_URL+key + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(title);
        dest.writeString(imagePreview);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
