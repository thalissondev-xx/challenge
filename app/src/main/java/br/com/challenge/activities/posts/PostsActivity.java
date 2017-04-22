package br.com.challenge.activities.posts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.challenge.R;
import br.com.challenge.activities.posts.adapter.PostsAdapter;
import br.com.challenge.activities.posts.injection.DaggerPostsComponent;
import br.com.challenge.activities.posts.injection.PostsModule;
import br.com.challenge.models.RedditChildrenResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements PostsMVP.View {

    // Adapter of posts
    private PostsAdapter adapter = null;
    private boolean loadMoreRequest = true;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisibleItems;

    // Views
    @BindView(R.id.rvPosts) RecyclerView recyclerView;
    @BindView(R.id.llProgress) LinearLayout llProgress;
    @BindView(R.id.llError) LinearLayout llError;
    @BindView(R.id.tvTryAgain) TextView tvTryAgain;
    @BindView(R.id.tvErrorMessage) TextView tvErrorMessage;
    @BindView(R.id.toolbar) Toolbar toolbar;

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

        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        // Verify if is a tablet device
        if (getResources().getBoolean(R.bool.isTablet)) {
            recyclerView.setLayoutManager(staggeredManager);
        } else {
            recyclerView.setLayoutManager(linearManager);
        }

        // Set listener RecyclerView
        rvListener();

        // Click for try again
        tvTryAgain.setOnClickListener(v -> requestList(true));

        // Request the list of posts
        requestList(false);
    }

    public void rvListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();

                    if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                        int[] firstVisibleItems = null;
                        firstVisibleItems = ((StaggeredGridLayoutManager) recyclerView
                                .getLayoutManager()).findFirstCompletelyVisibleItemPositions(
                                firstVisibleItems);

                        if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                            pastVisibleItems = firstVisibleItems[0];
                        }
                    } else {
                        pastVisibleItems = ((LinearLayoutManager) recyclerView
                                .getLayoutManager()).findFirstVisibleItemPosition();
                    }

                    if (loadMoreRequest) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loadMoreRequest = false;
                            requestList(true);
                        }
                    }
                }
            }
        });
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
        if (!loadMore) {
            adapter = null;
        }

        presenter.request(loadMore);
    }

    @Override
    public void setAdapter(List<RedditChildrenResponse> list) {

        // Now can load more
        loadMoreRequest = true;

        // If not null, so add items in list and not create the new adapter
        if (adapter != null) {
            adapter.addAll(list);
        } else {

            // Create the adapter
            adapter = new PostsAdapter(this, list);
            recyclerView.setAdapter(adapter);
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
