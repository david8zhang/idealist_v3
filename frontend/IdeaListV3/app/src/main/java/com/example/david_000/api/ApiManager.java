package com.example.david_000.api;

import android.app.DownloadManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

    //TODO: Rework this somehow as to avoid having to rewrite code

    /** Fetch a list of ideas given the cluster_id. */
    public void fetchIdeas(final ArrayList<Idea> ideas, final FeedListAdapter listAdapter) {
        DataModelController dmc = DataModelController.getInstance();
        String clusterId = dmc.getClusterID();
        System.out.println(clusterId);
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", "Bearer 1450909428");
        String ideaUrl = Constants.IDEAS + "?clusterid=" + clusterId;
        System.out.println(ideaUrl);
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(ideaUrl);
        if(entry != null) {
            //Fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                System.out.println(data);
                utils.parseIdeaJson(new JSONObject(data), listAdapter, ideas);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            GetRequest request = new GetRequest(headers, params, ideaUrl, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    System.out.println(jsonObject);
                    utils.parseIdeaJson(jsonObject, listAdapter, ideas);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println(volleyError);
                }
            });
            AppController.getInstance().addToRequestQueue(request);
        }
    }

    /** Fetch a list of clusters given the user_id. */
    public void fetchCluster(final ArrayList<Cluster> clusters, final FeedListAdapter listAdapter) {
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String> ();
        headers.put("Authorization", "Bearer 1450909428");
        String clusterUrl = Constants.CLUSTERS + "?userid=0bfc174eef549715f1448496b854dd810a8a72cb";
        GetRequest request = new GetRequest(headers, params, clusterUrl, new Response.Listener<JSONObject>() {
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

    /** Post a new cluster with the given NAME and DESCRIPTION. */
    public void postCluster(final String name, final String description) {
        String clusterUrl = Constants.CLUSTERS;
        final StringRequest request = new StringRequest(Request.Method.POST, clusterUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                params.put("userid", "0bfc174eef549715f1448496b854dd810a8a72cb");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 1450909428");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);
    }
}
