package br.com.challenge.activities.posts;

import java.util.List;
import br.com.challenge.base.BaseInteractor;
import br.com.challenge.base.BaseView;
import br.com.challenge.models.RedditChildrenResponse;
import br.com.challenge.models.RedditDataResponse;
import br.com.challenge.networking.RedditService;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public interface PostsMVP {
    interface View extends BaseView {
        void requestList(boolean loadMore);
        void setAdapter(List<RedditChildrenResponse> list);
    }

    interface Presenter {
        interface OnRequestFinishedListener {
            void onSuccess(RedditDataResponse dataResponse);
            void onError(String msg);
        }
    }

    interface Interactor extends BaseInteractor {
        void list(String after, String limit, String rowJson, RedditService service,
                  final PostsMVP.Presenter.OnRequestFinishedListener listener);
    }
}