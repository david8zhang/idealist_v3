package com.example.david_000.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david_000.model.FeedItem;
import com.example.david_000.view.IdealistActivity;
import com.example.david_000.view.MainActivity;
import com.example.david_000.view.R;

import java.util.ArrayList;

/**
 * Created by david_000 on 12/20/2015.
 */
public class FeedListAdapter <T extends FeedItem> extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    /** The List of FeedItems, or ideas.*/
    private ArrayList<T> items;

    /** The current context. */
    private Context mContext;

    /** The data model controller. */
    private DataModelController dmc = DataModelController.getInstance();

    public FeedListAdapter(ArrayList<T> items, Context context) {
        this.items = items;
        mContext = context;
    }

    /**
     * The View holder that provides a reference to the views for
     * each data item. Complex data items may need more than one view
     * per item, and you provide access to all the views fora  data item
     * in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** The CardView which corresponds to a feed item. */
        public CardView mCardView;

        /** The Image associated with the ideas. */
        public ImageView imgView;

        /** The textview associated with names. */
        public TextView nameView;

        /** The TextView associated with.*/
        public TextView catView;

        /** The textview associated with descriptions. */
        public TextView descView;

        /** The clusterid associated with this viewholder. */
        public String itemId;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.card_view);
            imgView = (ImageView)itemView.findViewById(R.id.idea_sketch);
            nameView = (TextView)itemView.findViewById(R.id.name);
            catView = (TextView)itemView.findViewById(R.id.category);
            descView = (TextView)itemView.findViewById(R.id.description);
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
        View v = LayoutInflater.from(parent.getContext())
                                   .inflate(R.layout.feeditem, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dmc.getIsCluster() == true) {
                    Intent intent = new Intent(mContext, IdealistActivity.class);
                    mContext.startActivity(intent);
                    dmc.setItemID(vh.itemId);
                    dmc.setIsCluster(false);
                } else {
                    System.out.println("Looking at ideas right now");
                }
            }
        });
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
        FeedItem item = items.get(position);
        String name = item.getName();
        String cat = item.getCategory();
        String desc = item.getDescription();
        Bitmap img = item.getImage();

        ImageView imgView = holder.imgView;
        TextView nameView = holder.nameView;
        TextView catView = holder.catView;
        TextView descView = holder.descView;
        holder.itemId = item.getItemId();

        nameView.setText(name);
        descView.setText(desc);

        if(img != null) {
            imgView.setImageBitmap(img);
        } else {
            imgView.setVisibility(View.GONE);
        }
        if(cat != null) {
            catView.setText(cat);
        } else {
            catView.setVisibility(View.GONE);
        }
    }

    /**
     * Get the number of items in the dataset.
     * @return idealist.size()
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
}
