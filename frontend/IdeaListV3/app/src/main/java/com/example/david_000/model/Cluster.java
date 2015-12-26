package com.example.david_000.model;

/**
 * Created by david_000 on 12/24/2015.
 */
public class Cluster extends FeedItem{

    public Cluster() {}

    public Cluster(String user_id, String cluster_id, String name, String description) {
        super(user_id, cluster_id, name, description, null, null);
    }
}
