package com.example.david_000.controller;

import android.graphics.Bitmap;

import com.example.david_000.model.Cluster;
import com.example.david_000.model.Idea;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by david_000 on 12/27/2015.
 */
public class DataModelController {

    /** Instantiate DataModelController instance. */
    private static DataModelController mInstance;
    private static final Object obj = new Object();

    /** Instantiate an arraylist of clusters to provide project-wide accessibility.*/
    private ArrayList<Cluster> clusters = new ArrayList<Cluster>();

    /** A boolean dictating if we're currently looking at the clusters or the ideas. */
    private boolean isCluster = true;

    /** Cluster ID that was clicked (null if none were clicked at all) */
    private String clusterId = null;

    /** Idea ID that was clicked (null if none were clicked at all). */
    private String ideaId = null;

    /** The bitmap image associated with the idea. */
    private Bitmap idea_image = null;

    /** The cluster timestamp. */
    private String clusterTime = null;

    /** The idea timestamp. */
    private String ideaTime = null;

    public DataModelController() {}

    /** Get an instance of the Data Model Controller. */
    public static DataModelController getInstance() {
        synchronized(obj) {
            if(mInstance == null)
                mInstance = new DataModelController();
        }
        return mInstance;
    }

    /** GETTER and SETTER for cluster list. */
    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }

    /** GETTER and SETTER for Item ID, to determine if user clicked on an item. */
    public String getClusterID() {
        return clusterId;
    }

    public void setClusterID(String itemID) {
        this.clusterId = itemID;
    }

    public String getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(String ideaID) { this.ideaId = ideaID;}

    public boolean getIsCluster() {
        return isCluster;
    }

    public void setIsCluster(boolean isCluster) {
        this.isCluster = isCluster;
    }

    public Bitmap getIdea_image() {
        return idea_image;
    }

    public void setIdea_image(Bitmap idea_image) {
        this.idea_image = idea_image;
    }

    public String getIdeaTime() {
        return ideaTime;
    }

    public void setIdeaTime(String ideaTime) {
        this.ideaTime = ideaTime;
    }

    public String getClusterTime() {
        return clusterTime;
    }

    public void setClusterTime(String clusterTime) {
        this.clusterTime = clusterTime;
    }
}
