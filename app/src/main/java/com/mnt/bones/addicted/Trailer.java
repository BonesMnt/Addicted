package com.mnt.bones.addicted;

/**
 * Created by Usuario on 06/02/2017.
 */

public class Trailer {

    private static final String IMAGE_PATH = "http://img.youtube.com/vi/";
    private static final String IMAGE_SUFIX = "/0.jpg";

    private String key;
    private String title;
    private String imagePreview;


    public Trailer(String key, String title) {
        this.key = key;
        this.title = title;
        this.imagePreview = IMAGE_PATH + key + IMAGE_SUFIX;
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
        return imagePreview;
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
                '}';
    }
}
