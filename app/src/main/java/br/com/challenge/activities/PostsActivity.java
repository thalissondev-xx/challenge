package br.com.challenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

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

    // Adapter of posts
    PostsAdapter adapter;
    List<RedditChildrenResponse> list;

    // Views
    @BindView(R.id.rvPosts) RecyclerView recyclerView;
    @BindView(R.id.llProgress) LinearLayout llProgress;
    @BindView(R.id.llError) LinearLayout llError;
    @BindView(R.id.tvTryAgain) TextView tvTryAgain;
    @BindView(R.id.tvErrorMessage) TextView tvErrorMessage;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.pbLoadMore) ProgressBar pbLoadMore;

    // Presenter
    @Inject PostsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        // Inject the views
        ButterKnife.bind(this);

        // Set toolbar
        setSupportActionBar(toolbar);

        // Inject the objects
        DaggerPostsComponent
                .builder()
                .postsModule(new PostsModule(this))
                .build()
                .inject(this);

        // Config
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // RecyclerView listener
        recyclerView.addOnScrollListener(new InfiniteScrollListener(
                Integer.parseInt(presenter.limit), linearLayoutManager) {

            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
                requestList(true);
            }

        });

        // Click for try again
        tvTryAgain.setOnClickListener(v -> requestList(false));

        // Request the list of posts
        requestList(false);
    }

    @Override
    public void showLoading() {
        hideError();
        llProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        llProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        hideLoading();
        tvErrorMessage.setText(message);
        llError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        llError.setVisibility(View.GONE);
    }

    @Override
    public void requestList(boolean loadMore) {
        presenter.request(loadMore);
    }

    @Override
    public void setAdapter(List<RedditChildrenResponse> listItems) {
        if (list == null || list.size() == 0) {

            // Create instance
            list = new ArrayList<>();

            // Add new items

            // Create the adapter
            adapter = new PostsAdapter(this, list);
            recyclerView.setAdapter(adapter);
        } else {
            list.clear();
            list.addAll(listItems);
            adapter.notifyDataSetChanged();
        }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload:
                requestList(false);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
