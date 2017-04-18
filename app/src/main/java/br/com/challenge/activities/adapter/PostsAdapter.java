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

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    private Context context;
    private List<RedditChildrenResponse> list;

    public PostsAdapter(Context context, List<RedditChildrenResponse> list) {
        this.context = context;
        this.list = list;
    }

    public void addAll(List<RedditChildrenResponse> addList) {
        list.addAll(addList);
        notifyDataSetChanged();
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        PostsViewHolder viewHolder = new PostsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        RedditNewsDataResponse data = list.get(position).getData();

        // Set the data in views
        holder.title.setText(data.getTitle());
        holder.numComments.setText(data.getNumCommments());
        holder.createdUTC.setText(Global.timeDiff(data.getCreatedUTC()));

        if (!data.getThumbnail().equals("self") && data.getPreview() != null) {

            // Get the image with the best resolution
            Resolution resolution = BestResolution.search(data.getPreview().getImages());
            Picasso.with(context).load(resolution.getUrl()).into(holder.thumbnail);
            holder.thumbnail.setVisibility(View.VISIBLE);
        } else {

            // Remove the image view when not have a image
            holder.thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
