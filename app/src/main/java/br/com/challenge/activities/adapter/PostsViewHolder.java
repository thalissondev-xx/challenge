package br.com.challenge.activities.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.challenge.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thalissonestrela on 4/16/17.
 */

public class PostsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvTitle) TextView title;
    @BindView(R.id.tvNumberOfComments) TextView numComments;
    @BindView(R.id.tvUTC) TextView createdUTC;
    @BindView(R.id.ivPost) ImageView thumbnail;
    @BindView(R.id.tvAuthor) TextView author;

    public PostsViewHolder(View itemView) {
        super(itemView);

        // Inject
        ButterKnife.bind(this, itemView);
    }
}
