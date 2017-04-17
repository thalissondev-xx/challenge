package br.com.challenge.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class ServiceFactory {

    public RedditService createAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RedditService.SERVICE_ENDPOINT)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(RedditService.class);
    }

}
