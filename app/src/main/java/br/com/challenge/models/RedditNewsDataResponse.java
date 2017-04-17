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
    @SerializedName("create_utc") private String createdUTC;

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getNumCommments() {
        return numCommments;
    }

    public String getCreatedUTC() {
        return createdUTC;
    }

    public PreviewResolution getPreview() {
        return preview;
    }

}
