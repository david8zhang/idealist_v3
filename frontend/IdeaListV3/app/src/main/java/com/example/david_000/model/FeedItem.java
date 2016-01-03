package com.example.david_000.model;

import android.graphics.Bitmap;

/**
 * Created by david_000 on 12/26/2015.
 */
public class FeedItem implements Comparable {

    /** Attributes of a feeditem. */
    private String user_id;
    private String cluster_id;
    private String idea_id;
    private String idea_timestamp;
    private String cluster_timestamp;
    private String name;
    private String description;
    private String category;
    private String image;

    public FeedItem() {}

    public FeedItem(String user_id, String cluster_id, String cluster_timestamp, String idea_timestamp, String idea_id, String name, String description, String category, String image) {
        this.user_id = user_id;
        this.cluster_id = cluster_id;
        this.cluster_timestamp = cluster_timestamp;
        this.idea_timestamp = idea_timestamp;
        this.idea_id = idea_id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
    }

    public String getUser_id() { return user_id; }

    public void setUser_id(String user_id) {this.user_id = user_id;}

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) { this.cluster_id = cluster_id; }

    public String getIdea_Id() { return idea_id; }

    public void setIdea_Id(String idea_id) {this.idea_id = idea_id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return  "user_id: " + user_id + "\n"
                + "cluster_id: " + cluster_id + "\n"
                + "idea_id: " + idea_id + "\n"
                + "name: " + name + "\n"
                + "description: " + description + "\n"
                + "category: " + category + "\n";
    }

    @Override
    public int compareTo(Object o) {
        FeedItem feedItem = (FeedItem)o;
        if(idea_id == null) {
            return cluster_id.compareTo(feedItem.getCluster_id());
        } else {
            return idea_id.compareTo(feedItem.getIdea_Id());
        }
    }


    public String getIdea_timestamp() {
        return idea_timestamp;
    }

    public void setIdea_timestamp(String idea_timestamp) {
        this.idea_timestamp = idea_timestamp;
    }

    public String getCluster_timestamp() {
        return cluster_timestamp;
    }

    public void setCluster_timestamp(String cluster_timestamp) {
        this.cluster_timestamp = cluster_timestamp;
    }
}
