package br.com.challenge.models;

import java.util.List;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class RedditDataResponse {
    private List<RedditChildrenResponse> children;
    private String after;
    private String before;

    public List<RedditChildrenResponse> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }
}
