package br.com.challenge.activities.posts.injection;

import javax.inject.Singleton;

import br.com.challenge.activities.posts.PostsActivity;
import br.com.challenge.activities.posts.PostsInteractor;
import br.com.challenge.networking.RedditService;
import br.com.challenge.networking.ServiceFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by thalissonestrela on 4/16/17.
 */

@Module
public class PostsModule {
    PostsActivity activity;

    public PostsModule(PostsActivity activity) {
        this.activity = activity;
    }

    @Singleton
    @Provides
    PostsInteractor provideInteractor() {
        return new PostsInteractor();
    }

    @Singleton
    @Provides
    RedditService provideService() {
        return new ServiceFactory().createAPI();
    }

    @Singleton
    @Provides
    PostsActivity provideActivity() {
        return activity;
    }
}
