package br.com.challenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.challenge.R;
import br.com.challenge.activities.adapter.PostsAdapter;
import br.com.challenge.activities.injection.DaggerPostsComponent;
import br.com.challenge.activities.injection.PostsModule;
import br.com.challenge.models.RedditChildrenResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements PostsMVP.View {

    // Views
    @BindView(R.id.rvPosts) RecyclerView recyclerView;
    @BindView(R.id.llProgress) LinearLayout llProgress;

    // Presenter
    @Inject PostsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        // Inject the views
        ButterKnife.bind(this);

        // Inject the objects
        DaggerPostsComponent
                .builder()
                .postsModule(new PostsModule(this))
                .build()
                .inject(this);

        // Config
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Request the list of posts
        requestList();
    }

    @Override
    public void showLoading() {
        llProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        llProgress.setVisibility(View.GONE);
    }

    @Override
    public void requestList() {
        presenter.request();
    }

    @Override
    public void setAdapter(List<RedditChildrenResponse> list) {

        // Create the adapter
        PostsAdapter adapter = new PostsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

}
