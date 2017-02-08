package com.mnt.bones.addicted;

/**
 * Created by Usuario on 02/02/2017.
 */

public class Review {

    private String author;
    private String contentReview;

    public Review(String author, String contentReview) {
        this.author = author;
        this.contentReview = contentReview;
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
}
