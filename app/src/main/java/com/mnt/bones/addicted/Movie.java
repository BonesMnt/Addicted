package com.mnt.bones.addicted;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Movie object to store data
 */

public class Movie implements Parcelable{

    private String movieId;
    private String originalTitle;
    private String poster;
    private String overview;
    private String rating;
    private String releaseDate;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;

    private final String BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w185/";
    private final String MAX_RATING = "/10";
    public static final String MOVIE_KEY = "com.mnt.bones.addicted.MOVIE";


    public Movie(String movieId, String originalTitle, String poster,
                 String overview, String rating, String releaseDate) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.poster = BASE_POSTER_PATH + poster;
        this.overview = overview;
        this.rating = rating + MAX_RATING;
        this.releaseDate = releaseDate;
        this.trailers = new ArrayList<Trailer>();
        this.reviews = new ArrayList<Review>();

    }

    private Movie(Parcel input) {

        movieId = input.readString();
        originalTitle = input.readString();
        poster = input.readString();
        overview = input.readString();
        rating = input.readString();
        releaseDate = input.readString();
        Parcelable[] parcelableTrailer = input.readParcelableArray(Trailer.class.getClassLoader());
        trailers = new ArrayList<Trailer>(parcelableTrailer.length);
        for (int i = 0; i < parcelableTrailer.length; i++){
            trailers.add((Trailer) parcelableTrailer[i]);
        }
        Parcelable[] parcelableReviews = input.readParcelableArray(Review.class.getClassLoader());
        reviews = new ArrayList<Review>(parcelableReviews.length);
        for (int i = 0; i < parcelableReviews.length; i++){
            reviews.add((Review) parcelableReviews[i]);
        }

    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating+MAX_RATING;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", poster='" + poster + '\'' +
                ", overview='" + overview + '\'' +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + '\'' +
                ", trailers='" + trailers.toString() + '\'' +
                ", reviews='" + reviews.toString() + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(originalTitle);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeString(releaseDate);
        dest.writeParcelableArray(trailers.toArray(new Parcelable[0]), flags);
        dest.writeParcelableArray(reviews.toArray(new Parcelable[0]), flags);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
