package br.com.challenge.activities.posts.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.challenge.App;
import br.com.challenge.R;
import br.com.challenge.activities.posts.PostsMVP;
import br.com.challenge.models.RedditChildrenResponse;
import br.com.challenge.models.RedditNewsDataResponse;
import br.com.challenge.models.Resolution;
import br.com.challenge.utils.BestResolution;
import br.com.challenge.utils.Global;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<RedditChildrenResponse> list;
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private int lastPosition = -1;

    // Endless
    private boolean loadMoreRequest = true;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisibleItems;

    public PostsAdapter(Activity activity, List<RedditChildrenResponse> list) {
        this.activity = activity;
        this.list = list;
    }

    public void addAll(List<RedditChildrenResponse> addList) {
        list.remove(list.size() - 1);
        notifyItemRemoved(list.size());

        list.addAll(addList);
        notifyItemInserted(list.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
            return new PostsViewHolder(view);
        } else if (viewType == VIEW_PROG) {
            View view = LayoutInflater.from(activity).inflate(R.layout.footer_progress, parent, false);
            return new PostsProgressViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_ITEM:
                setHolderPosts(holder, position);
                break;
            case VIEW_PROG:
                setHolderProgress(holder);
                break;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionItem(position)) {
            return VIEW_PROG;
        }

        return VIEW_ITEM;
    }

    private boolean isPositionItem(int position) {
        return !(position != getItemCount()-1);
    }

    private void setHolderProgress(RecyclerView.ViewHolder holder) {
        PostsProgressViewHolder holderFooter = (PostsProgressViewHolder) holder;
        holderFooter.getProgressBar().setIndeterminate(true);
    }

    private void setHolderPosts(RecyclerView.ViewHolder holder, int position) {
        PostsViewHolder holderPosts = (PostsViewHolder) holder;
        RedditNewsDataResponse data = list.get(position).getData();
        String url = list.get(position).getData().getUrl();

        // Set the data in views
        holderPosts.title.setText(data.getTitle());
        holderPosts.author.setText("by " + data.getAuthor());
        holderPosts.numComments.setText(data.getNumCommments());
        holderPosts.createdUTC.setText(new Global().timeDiff(data.getCreatedUTC()));

        // Click
        if (url != null && !url.equals("") &&
                (url.contains("https://") || url.contains("http://"))) {
            setClickListener(holder.itemView, url);
        }

        if (!data.getThumbnail().equals("self") && data.getPreview() != null) {

            // Get the image with the best resolution
            Resolution resolution = BestResolution.search(App.getInstance(),
                    data.getPreview().getImages());

            // Set the height
            holderPosts.thumbnail.setMinimumHeight(resolution.getHeight());

            Picasso.with(activity).load(resolution.getUrl()).into(holderPosts.thumbnail);
            holderPosts.thumbnail.setVisibility(View.VISIBLE);
        } else {

            // Remove the image view when not have a image
            holderPosts.thumbnail.setVisibility(View.GONE);
        }

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        Animation animation = AnimationUtils.loadAnimation(activity, (position > lastPosition) ?
                R.anim.up_from_bottom : R.anim.down_from_top);
        viewToAnimate.startAnimation(animation);

        lastPosition = position;
    }

    private void setClickListener(View view, String url) {
        view.setOnClickListener(v -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
            builder.setExitAnimations(activity, R.anim.slide_in_left, R.anim.slide_out_right);
            builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(activity, Uri.parse(url));
        });
    }

    public void scrollListener(RecyclerView recyclerView, PostsMVP.View view) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();

                    // Verify if the device is tablet, because if is tablet the list is gridview
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

                    // If load data is ended, so load more items
                    if (loadMoreRequest) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loadMoreRequest = false;
                            view.requestList(true);
                        }
                    }
                }
            }
        });
    }

    public void listLoaded() {
        loadMoreRequest = true;
    }
}
