package br.com.challenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class RedditNewsDataResponse {

    private String title;
    private String thumbnail;
    private PreviewResolution preview;

    @SerializedName("num_comments") private String numCommments;
    @SerializedName("created") private long createdUTC;

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

    public PreviewResolution getPreview() {
        return preview;
    }

}
