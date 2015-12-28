package com.example.david_000.model;

import android.graphics.Bitmap;

/**
 * Created by david_000 on 12/26/2015.
 */
public class FeedItem implements Comparable {

    /** Attributes of a feeditem. */
    private String parent_id;
    private String item_id;
    private String timestamp;
    private String name;
    private String description;
    private String category;
    private Bitmap image;

    public FeedItem() {}

    public FeedItem(String parent_id, String timestamp, String item_id, String name, String description, String category, Bitmap image) {
        this.item_id = item_id;
        this.timestamp = timestamp;
        this.parent_id = parent_id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
    }


    /** GETTERS and SETTERS for attributes. */
    public String getItemId() {
        return item_id;
    }

    public void setItemId(String item_id) {
        this.item_id = item_id;
    }

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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return  "parent_id: " + parent_id + "\n"
                + "item_id: " + item_id + "\n"
                + "name: " + name + "\n"
                + "description: " + description + "\n"
                + "category: " + category + "\n";
    }

    @Override
    public int compareTo(Object o) {
        FeedItem feedItem = (FeedItem)o;
        return item_id.compareTo(feedItem.getItemId());
    }


}
