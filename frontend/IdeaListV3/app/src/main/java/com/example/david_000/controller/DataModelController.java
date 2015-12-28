package com.example.david_000.controller;

import com.example.david_000.model.Cluster;

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
}
