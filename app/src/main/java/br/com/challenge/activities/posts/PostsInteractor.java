package br.com.challenge.activities.posts;

import br.com.challenge.App;
import br.com.challenge.models.RedditNewsResponse;
import br.com.challenge.networking.RedditService;
import br.com.challenge.utils.Global;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsInteractor implements PostsMVP.Interactor {
    private Subscription subscription;

    @Override
    public void list(String after, String limit, String rowJson, RedditService service,
                     final PostsMVP.Presenter.OnRequestFinishedListener listener) {

        subscription = service.getListNews(after, limit, rowJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RedditNewsResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        String msg = (!new Global().isOnline(App.getInstance())) ?
                                "Your internet connection may be in trouble" :
                                "Internal problems, please try again";

                        listener.onError(msg);
                    }

                    @Override
                    public void onNext(RedditNewsResponse redditNewsResponse) {
                        listener.onSuccess(redditNewsResponse.getData());
                    }
                });

    }

    @Override
    public void unSubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
