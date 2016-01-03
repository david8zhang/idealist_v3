package com.example.david_000.controller;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.david_000.model.FeedItem;
import com.example.david_000.view.EditClusterActivity;
import com.example.david_000.view.IdeaSketchView;
import com.example.david_000.view.IdealistActivity;
import com.example.david_000.view.MainActivity;
import com.example.david_000.view.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        public String clusterId;

        /** the idea id associated with this view holder. */
        public String ideaId;

        /** The timestamp associated with the cluster. */
        public String clusterTime;

        /** The timestamp associated with the didea.*/
        public String ideaTime;

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
                if(dmc.getIsCluster()) {
                    Intent intent = new Intent(mContext, IdealistActivity.class);
                    mContext.startActivity(intent);
                    dmc.setClusterID(vh.clusterId);
                    dmc.setClusterTime(vh.clusterTime);
                    dmc.setIsCluster(false);
                } else {
                    dmc.setIdeaId(vh.ideaId);
                    dmc.setIdeaTime(vh.ideaTime);
                    System.out.println("Looking at ideas right now");
                }
            }
        });

        // Long click listener for editing and deleting clusters
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(dmc.getIsCluster()) {
                    System.out.println(vh.clusterId);
                    System.out.println(vh.clusterTime);
                    dmc.setClusterID(vh.clusterId);
                    dmc.setClusterTime(vh.clusterTime);
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warning!")
                            .setContentText("Edit or Delete Cluster?")
                            .setCancelText("Delete")
                            .setConfirmText("Edit")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    System.out.println("Delete the cluster!");
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    //Put the existing name and description through to the activity to prepopulate the form
                                    Intent editCluster = new Intent(mContext, EditClusterActivity.class);
                                    editCluster.putExtra("name", vh.nameView.getText().toString());
                                    editCluster.putExtra("description", vh.descView.getText().toString());
                                    mContext.startActivity(editCluster);
                                }
                            }).show();
                } else {
                    //TODO: Under construction
                }
                return true;
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
        String img = item.getImage();

        ImageView imgView = holder.imgView;
        TextView nameView = holder.nameView;
        TextView catView = holder.catView;
        TextView descView = holder.descView;
        if(item.getCluster_id() == null) {
            holder.ideaId = item.getIdea_Id();
            holder.ideaTime = item.getIdea_timestamp();
        } else {
            holder.clusterId = item.getCluster_id();
            holder.clusterTime = item.getCluster_timestamp();
        }
        nameView.setText(name);
        descView.setText(desc);

        if(img != null) {
            new DownloadImageTask(imgView).execute(item.getImage());
        } else {
            imgView.setVisibility(View.GONE);
        }
        if(cat != null) {
            catView.setText(cat);
        } else {
            catView.setVisibility(View.GONE);
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        /** the associated imageView. */
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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
