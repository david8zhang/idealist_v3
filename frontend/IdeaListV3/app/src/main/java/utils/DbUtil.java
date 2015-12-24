package utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.david_000.controller.FeedListAdapter;
import com.example.david_000.model.Cluster;
import com.example.david_000.model.Idea;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david_000 on 12/24/2015.
 */
public class DbUtil {

    public DbUtil() {}

    /** Fetch a list of the ideas given the cluster. */
    public List<Idea> fetchIdeas(String cluster_id) {
        return null;
    }

    //TODO: Figure out how to use Google Volley
    /** Fetch a list of clusters given the user_id. */
    public List<Cluster> fetchCluster() {
        List<Cluster> clusters = new ArrayList<Cluster> ();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.CLUSTERS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        return clusters;
    }
}
