package com.example.david_000.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.david_000.api.ClusterRequest;
import com.example.david_000.controller.AppController;
import com.example.david_000.model.Cluster;
import com.example.david_000.model.FeedItem;
import com.example.david_000.model.Idea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david_000 on 12/24/2015.
 */
public class DbUtil {

    public DbUtil() {}

    /** Fetch a list of the ideas given the cluster. */
    public List<Idea> fetchIdeas(String cluster_id) {
        return null;
    }

    /** Fetch a list of clusters given the user_id. */
    public List<Cluster> fetchCluster() {
        final List<Cluster> clusters = new ArrayList<Cluster> ();
        Map<String, String> headers = new HashMap<String, String> ();
        Map<String, String> params = new HashMap<String, String> ();
        headers.put("Authorization", "Bearer 1450909428");
        String clusterUrl = Constants.CLUSTERS + "?userid=0bfc174eef549715f1448496b854dd810a8a72cb";
        ClusterRequest request = new ClusterRequest(headers, params, clusterUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject);
                parseJSON(jsonObject, clusters);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        System.out.println(request);
        if(request == null) {
            System.out.println("Shit");
        } else {
            AppController.getInstance().addToRequestQueue(request);
        }
        return clusters;
    }

    /** Parse the json response. */
    public <T extends FeedItem> void parseJSON(JSONObject data, List<T> list) {
        List<T> result = new ArrayList<T>();
        try {
            JSONArray feedArray = data.getJSONArray("Items");
            if(feedArray.length() <= 0) {
                System.out.println("There were no results!");
            } else {
                for(int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject)feedArray.get(i);
                    FeedItem item = new FeedItem();
                    //TODO: Figure out a better way to handle this
                    if(feedObj.getString("user_id") != null) {
                        item.setParent_id(feedObj.getString("user_id"));
                        item.setItemId(feedObj.getString("cluster_id"));
                    } else {
                        item.setParent_id(feedObj.getString("cluster_id"));
                        item.setItemId(feedObj.getString("idea_id"));
                        item.setCategory(feedObj.getString("catgory"));
                    }
                    item.setName(feedObj.getString("name"));
                    item.setDescription(feedObj.getString("description"));
                    result.add((T) item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
