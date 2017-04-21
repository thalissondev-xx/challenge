package br.com.challenge.activities.posts.injection;

import javax.inject.Singleton;

import br.com.challenge.activities.posts.PostsActivity;
import br.com.challenge.activities.posts.PostsInteractor;
import br.com.challenge.activities.posts.PostsMVP;
import br.com.challenge.networking.RedditService;
import br.com.challenge.networking.ServiceFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by thalissonestrela on 4/16/17.
 */

@Module
public class PostsModule {
    PostsMVP.View view;

    public PostsModule(PostsMVP.View view) {
        this.view = view;
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
    PostsMVP.View provideActivity() {
        return view;
    }
}
