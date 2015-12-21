package com.example.david_000.controller;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_000.model.FeedItem;
import com.example.david_000.view.R;

import java.util.List;

/**
 * Created by david_000 on 12/20/2015.
 */
public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    /** The List of FeedItems, or ideas.*/
    private List<FeedItem> ideaList;

    /**
     * The View holder that provides a reference to the views for
     * each data item. Complex data items may need more than one view
     * per item, and you provide access to all the views fora  data item
     * in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** The CardView which corresponds to a feed item. */
        public CardView mCardView;
        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public FeedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView)LayoutInflater.from(parent.getContext())
                                   .inflate(R.layout.feeditem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Get the element from the dataset at this position,
     * and replace the contents of the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem item = ideaList.get(position);
        String name = item.getIdeaTitle();
        String cat = item.getIdeaCategory();
        String desc = item.getIdeaText();
        Bitmap img = item.getImage();

        ImageView imgView = (ImageView)holder.mCardView.findViewById(R.id.idea_sketch);
        TextView nameView = (TextView)holder.mCardView.findViewById(R.id.name);
        TextView catView = (TextView)holder.mCardView.findViewById(R.id.category);
        TextView descView = (TextView)holder.mCardView.findViewById(R.id.description);

        nameView.setText(name);
        catView.setText(cat);
        descView.setText(desc);

        if(img != null) {
            imgView.setImageBitmap(img);
        } else {
            imgView.setVisibility(imgView.INVISIBLE);
        }
    }

    /**
     * Get the number of items in the dataset.
     * @return idealist.size()
     */
    @Override
    public int getItemCount() {
        return ideaList.size();
    }
}
