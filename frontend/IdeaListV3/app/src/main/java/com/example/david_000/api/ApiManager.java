package com.example.david_000.api;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.david_000.controller.AppController;
import com.example.david_000.controller.DataModelController;
import com.example.david_000.controller.FeedListAdapter;
import com.example.david_000.model.Cluster;
import com.example.david_000.model.Idea;
import com.example.david_000.utils.Constants;
import com.example.david_000.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david_000 on 12/27/2015.
 */
public class ApiManager {

    /** Utilities. */
    private Utils utils;

    public ApiManager() {
        utils = new Utils();
    }

    /** Fetch a list of ideas given the cluster_id. */
    public void fetchIdeas(final ArrayList<Idea> ideas, final FeedListAdapter listAdapter) {
        DataModelController dmc = DataModelController.getInstance();
        System.out.println(dmc.getItemID());
    }

    /** Fetch a list of clusters given the user_id. */
    public void fetchCluster(final ArrayList<Cluster> clusters, final FeedListAdapter listAdapter) {
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String> ();
        headers.put("Authorization", "Bearer 1450909428");
        String clusterUrl = Constants.CLUSTERS + "?userid=0bfc174eef549715f1448496b854dd810a8a72cb";
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(clusterUrl);
        //TODO: Prevent this goddamn thing from making two requests when the device is rotated
        if(entry != null) {
            //fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                utils.parseClusterJson(new JSONObject(data),listAdapter, clusters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ClusterRequest request = new ClusterRequest(headers, params, clusterUrl, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    System.out.println(jsonObject);
                    utils.parseClusterJson(jsonObject, listAdapter, clusters);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println(volleyError);
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request);
        }
    }
}
