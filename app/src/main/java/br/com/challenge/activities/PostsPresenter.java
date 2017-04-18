package br.com.challenge.activities;

import javax.inject.Inject;

import br.com.challenge.models.RedditDataResponse;
import br.com.challenge.networking.RedditService;
import br.com.challenge.utils.Global;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsPresenter implements PostsMVP.Presenter.OnRequestFinishedListener {

    private final String rowJson = "1";
    public final String limit = "20";
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

    public void request(boolean loadMore) {

        // If reload is true, so reload the list
        if (!loadMore) {
            after = null;
            view.showLoading();
        }

        interactor.list(after, limit, rowJson, service, this);

    }

    @Override
    public void onSuccess(RedditDataResponse dataResponse) {

        // Update the variable "after" for load more
        after = dataResponse.getAfter();

        // Set the adapter
        view.setAdapter(dataResponse.getChildren());
        view.hideLoading();

    }

    @Override
    public void onError() {

        String msg = (!Global.isOnline()) ?
                "Your internet connection may be in trouble" :
                "Internal problems, please try again";

        view.showError(msg);

    }

}
