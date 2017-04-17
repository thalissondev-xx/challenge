package br.com.challenge.activities;

import javax.inject.Inject;

import br.com.challenge.models.RedditDataResponse;
import br.com.challenge.networking.RedditService;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsPresenter implements PostsMVP.Presenter.OnRequestFinishedListener {

    private String rowJson = "1";
    private String limit = "20";
    private String after = null;

    PostsInteractor interactor;
    RedditService service;
    PostsActivity view;

    @Inject
    public PostsPresenter(PostsActivity view, PostsInteractor interactor, RedditService service) {

        this.interactor = interactor;
        this.service = service;
        this.view = view;

    }

    public void request() {

        interactor.list(after, limit, rowJson, service, this);

    }

    @Override
    public void onSuccess(RedditDataResponse dataResponse) {

        view.setAdapter(dataResponse.getChildren());
        view.hideLoading();

    }

    @Override
    public void onError() {

    }

}
