package br.com.challenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class RedditNewsDataResponse {
    private String title;
    private String thumbnail;
    private String author;
    private String url;
    private PreviewResolution preview;

    @SerializedName("num_comments") private String numCommments;
    @SerializedName("created_utc") private long createdUTC;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getNumCommments() {
        return numCommments;
    }

    public long getCreatedUTC() {
        return createdUTC;
    }

    public String getUrl() {
        return url;
    }

    public PreviewResolution getPreview() {
        return preview;
    }
}
