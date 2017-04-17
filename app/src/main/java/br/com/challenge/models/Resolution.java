package br.com.challenge.models;

/**
 * Created by thalissonestrela on 4/17/17.
 */

public class Resolution {

    String url;
    int width;
    int height;

    public Resolution(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
