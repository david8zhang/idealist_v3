package com.example.david_000.model;

import android.graphics.Bitmap;

/**
 * Created by david_000 on 12/20/2015.
 */
public class Idea extends FeedItem{

    public Idea() {}

    public Idea(String cluster_id, String idea_id, String name, String category, String description, Bitmap image) {
        super(cluster_id, idea_id, name, category, description, image);
    }

}
