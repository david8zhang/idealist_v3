package com.example.david_000.utils;

import com.example.david_000.controller.FeedListAdapter;
import com.example.david_000.model.Cluster;
import com.example.david_000.model.Idea;

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

    /** Add Idea items to a list of ideas. */
    public void parseIdeaJson(JSONObject response, FeedListAdapter listAdapter, ArrayList<Idea> ideas) {
        try {
            JSONArray feedArray = response.getJSONArray("Items");
            if(feedArray.length() <= 0) {
                System.out.println("No results");
            } else {
                for(int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject)feedArray.get(i);
                    Idea idea = new Idea();
                    idea.setName(feedObj.getString("name"));
                    idea.setTimestamp(feedObj.getString("idea_timestamp"));
                    idea.setCategory(feedObj.getString("category"));
                    idea.setDescription(feedObj.getString("description"));
                    idea.setIdea_Id(feedObj.getString("idea_id"));
                    idea.setCluster_id(feedObj.getString("cluster_id"));
                    idea.setImage(feedObj.getString("image_url"));
                    if(!isContainsIdeas(idea, ideas)) {
                        ideas.add(idea);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    cluster.setUser_id(feedObj.getString("user_id"));
                    cluster.setCluster_id(feedObj.getString("cluster_id"));
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
            if(cluster.getCluster_id().equals(c.getCluster_id())) {
                return true;
            }
        }
        return false;
    }

    /** Check if a idea already exists by comparing its id. */
    public boolean isContainsIdeas(Idea idea, ArrayList<Idea> ideas) {
        for(Idea i: ideas) {
            if(idea.getIdea_Id().equals(i.getIdea_Id())) {
                return true;
            }
        }
        return false;
    }
}
