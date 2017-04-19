package br.com.challenge.activities.posts.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import br.com.challenge.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thalissonestrela on 4/18/17.
 */

public class PostsProgressViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.pbFooterProgress) ProgressBar progressBar;

    public PostsProgressViewHolder(View itemView) {
        super(itemView);

        // Inject
        ButterKnife.bind(this, itemView);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}