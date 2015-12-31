package com.example.david_000.utils;

import com.example.david_000.controller.FeedListAdapter;
import com.example.david_000.model.Cluster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by david_000 on 12/24/2015.
 */
public class Utils {

    public Utils() {
    }

    /** Add cluster items to a list of clusters. */
    public void parseClusterJson(JSONObject response, FeedListAdapter listAdapter, ArrayList<Cluster> clusters) {
        try {
            JSONArray feedArray = response.getJSONArray("Items");
            if(feedArray.length() <= 0) {
                System.out.println("No results!");
            } else {
                for(int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);
                    Cluster cluster = new Cluster();
                    cluster.setName(feedObj.getString("name"));
                    cluster.setTimestamp(feedObj.getString("cluster_timestamp"));
                    cluster.setDescription(feedObj.getString("description"));
                    cluster.setParent_id(feedObj.getString("user_id"));
                    cluster.setItemId(feedObj.getString("cluster_id"));
                    if(!isContainsCluster(cluster, clusters)) {
                        clusters.add(cluster);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** Check if a cluster already exists by comparing its id. */
    public boolean isContainsCluster(Cluster cluster, ArrayList<Cluster> clusters) {
        for(Cluster c: clusters) {
            if(cluster.getItemId().equals(c.getItemId())) {
                return true;
            }
        }
        return false;
    }
}
