package br.com.challenge.networking;

import br.com.challenge.models.RedditNewsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public interface RedditService {
    String SERVICE_ENDPOINT = "https://www.reddit.com/r/Android/";

    @GET("new.json")
    Observable<RedditNewsResponse> getListNews(@Query("after") String after,
                                               @Query("limit") String limit,
                                               @Query("raw_json") String row);
}