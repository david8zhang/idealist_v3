package utils;

import com.android.volley.Cache;
import com.example.david_000.controller.AppController;
import com.example.david_000.model.Cluster;
import com.example.david_000.model.Idea;

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

    /** Fetch the clusters of ideas. */
    public List<Cluster> fetchClusters() {
        List<Cluster> clusters = new ArrayList<Cluster>();
        return clusters;
    }
}
