package com.mnt.bones.addicted;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 02/02/2017.
 */

public class Review implements Parcelable{

    private String author;
    private String contentReview;

    public Review(String author, String contentReview) {
        this.author = author;
        this.contentReview = contentReview;
    }

    private Review(Parcel input){
        author = input.readString();
        contentReview = input.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContentReview() {
        return contentReview;
    }

    public void setContentReview(String contentReview) {
        this.contentReview = contentReview;
    }

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author + '\'' +
                ", contentReview='" + contentReview + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(contentReview);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
