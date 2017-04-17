package br.com.challenge.activities.injection;

import javax.inject.Singleton;

import br.com.challenge.activities.PostsActivity;
import dagger.Component;

/**
 * Created by thalissonestrela on 4/16/17.
 */

@Singleton
@Component(modules = PostsModule.class)
public interface PostsComponent {

    void inject(PostsActivity activity);

}
