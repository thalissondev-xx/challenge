package br.com.challenge.activities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.challenge.R;
import br.com.challenge.models.RedditChildrenResponse;
import br.com.challenge.models.RedditNewsDataResponse;
import br.com.challenge.models.Resolution;
import br.com.challenge.utils.BestResolution;
import br.com.challenge.utils.Global;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<RedditChildrenResponse> list;
    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;

    public PostsAdapter(Context context, List<RedditChildrenResponse> list) {
        this.context = context;
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
            View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            return new PostsViewHolder(view);
        } else if (viewType == VIEW_PROG) {
            View view = LayoutInflater.from(context).inflate(R.layout.footer_progress, parent, false);
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

        // Set the data in views
        holderPosts.title.setText(data.getTitle());
        holderPosts.numComments.setText(data.getNumCommments());
        holderPosts.createdUTC.setText(Global.timeDiff(data.getCreatedUTC()));

        if (!data.getThumbnail().equals("self") && data.getPreview() != null) {

            // Get the image with the best resolution
            Resolution resolution = BestResolution.search(data.getPreview().getImages());

            // Set the height
            holderPosts.thumbnail.setMinimumHeight(resolution.getHeight());

            Picasso.with(context).load(resolution.getUrl()).into(holderPosts.thumbnail);
            holderPosts.thumbnail.setVisibility(View.VISIBLE);
        } else {

            // Remove the image view when not have a image
            holderPosts.thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
