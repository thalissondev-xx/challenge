package br.com.challenge;

import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import br.com.challenge.activities.posts.PostsInteractor;
import br.com.challenge.activities.posts.PostsMVP;
import br.com.challenge.activities.posts.PostsPresenter;
import br.com.challenge.models.RedditDataResponse;
import br.com.challenge.models.RedditNewsResponse;
import br.com.challenge.networking.RedditService;
import br.com.challenge.networking.ServiceFactory;

import rx.schedulers.Schedulers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by thalissonestrela on 4/18/17.
 */

public class PostsTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    // FAKE json for verify the objects
    String FAKE_JSON = "{\"kind\": \"Listing\", \"data\": {\"modhash\": \"\", \"children\": [{\"kind\": \"t3\", \"data\": {\"contest_mode\": false, \"banned_by\": null, \"media_embed\": {}, \"subreddit\": \"Android\", \"selftext_html\": null, \"selftext\": \"\", \"likes\": null, \"suggested_sort\": null, \"user_reports\": [], \"secure_media\": null, \"link_flair_text\": null, \"id\": \"667g4d\", \"gilded\": 0, \"secure_media_embed\": {}, \"clicked\": false, \"score\": 1, \"report_reasons\": null, \"author\": \"SirVeza\", \"saved\": false, \"mod_reports\": [], \"name\": \"t3_667g4d\", \"subreddit_name_prefixed\": \"r/Android\", \"approved_by\": null, \"over_18\": false, \"domain\": \"techcrunch.com\", \"hidden\": false, \"preview\": {\"images\": [{\"source\": {\"url\": \"https://i.redditmedia.com/YZtSkd6iJx7ApP5I_MdOXmXHmuzKtET7G1D8DK7HANI.jpg?s=c308aee63805e360a22a3edbebdc54a8\", \"width\": 738, \"height\": 400}, \"resolutions\": [{\"url\": \"https://i.redditmedia.com/YZtSkd6iJx7ApP5I_MdOXmXHmuzKtET7G1D8DK7HANI.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=108&amp;s=4cfa7e64b7b9aa876e31028cd942e993\", \"width\": 108, \"height\": 58}, {\"url\": \"https://i.redditmedia.com/YZtSkd6iJx7ApP5I_MdOXmXHmuzKtET7G1D8DK7HANI.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=216&amp;s=23bc60984665deb8900136cc16475fcf\", \"width\": 216, \"height\": 117}, {\"url\": \"https://i.redditmedia.com/YZtSkd6iJx7ApP5I_MdOXmXHmuzKtET7G1D8DK7HANI.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=320&amp;s=d8508a897ac74c740c91ba765a34974b\", \"width\": 320, \"height\": 173}, {\"url\": \"https://i.redditmedia.com/YZtSkd6iJx7ApP5I_MdOXmXHmuzKtET7G1D8DK7HANI.jpg?fit=crop&amp;crop=faces%2Centropy&amp;arh=2&amp;w=640&amp;s=6aea4033ddb5b2305d1311eed47f6011\", \"width\": 640, \"height\": 346}], \"variants\": {}, \"id\": \"4ZW5O7zZym8Hda_ozXaractD4kH2X7Qfp7BSu8_8pJ8\"}], \"enabled\": false}, \"thumbnail\": \"https://b.thumbs.redditmedia.com/NJihwvdNTEAq5uwep0Ad_ciadP1rdf8_dZCaIyFcntc.jpg\", \"subreddit_id\": \"t5_2qlqh\", \"edited\": false, \"link_flair_css_class\": null, \"author_flair_css_class\": \"userBlack\", \"downs\": 0, \"brand_safe\": true, \"archived\": false, \"removal_reason\": null, \"post_hint\": \"link\", \"is_self\": false, \"hide_score\": true, \"spoiler\": false, \"permalink\": \"/r/Android/comments/667g4d/instagram_on_android_gets_offline_mode/\", \"num_reports\": null, \"locked\": false, \"stickied\": false, \"created\": 1492597228.0, \"url\": \"https://techcrunch.com/2017/04/18/instagram-offline/\", \"author_flair_text\": \"iPhone 7 Plus\", \"quarantine\": false, \"title\": \"Instagram on Android gets offline mode\", \"created_utc\": 1492568428.0, \"distinguished\": null, \"media\": null, \"num_comments\": 0, \"visited\": false, \"subreddit_type\": \"public\", \"ups\": 1}}], \"after\": \"t3_667g4d\", \"before\": null}}";

    // Mocks of presenter
    @Mock PostsMVP.View view;
    @Mock PostsInteractor interactor;
    @Mock RedditService service;
    @Mock PostsMVP.Presenter.OnRequestFinishedListener listener;
    @Spy RedditDataResponse dataResponse;

    // Injected in presenter the all mocks
    @InjectMocks PostsPresenter presenter;

    @Test
    public void successTest() {

        // When the request runs, then handle the adapter's calls and remove load
        presenter.onSuccess(dataResponse);

        verify(dataResponse).getAfter();
        verify(view).setAdapter(dataResponse.getChildren());
        verify(view).hideLoading();
    }

    @Test
    public void errorTest() {

        // When generates an error in the request
        presenter.onError("Error Test");

        verify(view).showError("Error Test");
    }

    @Test
    public void requestTest() {

        // When making request check the load and request the list
        presenter.request(false);

        verify(view).showLoading();
        verify(interactor).list(null, presenter.limit, presenter.rowJson, service, presenter);
    }

    @Test
    public void constsTest() {

        // Verify the constants
        assertThat(presenter.limit, is("20"));
        assertThat(presenter.rowJson, is("1"));
    }

    @Test
    public void requestApiTest() {

        // Verify that the reddit api is working and returning the correct data
        String rowJson = "1";
        String limit = "20";
        String after = null;

        RedditService service = new ServiceFactory().createAPI();
        service.getListNews(after, limit, rowJson)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe (obj -> {
                    assertNotNull(obj.getData());
                    assertNotNull(obj.getData().getChildren());
                });
    }

    @Test
    public void jsonToObjectTest() {

        // Check s whether the class is able to receive the Gson class object
        Gson gson = new Gson();
        RedditNewsResponse posts = gson.fromJson(FAKE_JSON, RedditNewsResponse.class);

        assertNotNull(posts.getData());
        assertNotNull(posts.getData().getChildren());

        assertThat(posts.getData().getChildren().get(0).getData().getAuthor(), is("SirVeza"));
        assertThat(posts.getData().getChildren().get(0).getData().getPreview().
                getImages().get(0).getSource().getHeight(), is(400));
    }
}
