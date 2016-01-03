package com.example.david_000.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.transform.XmlResponsesSaxParser;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by david_000 on 12/27/2015.
 */
public class ApiManager {

    /** Utilities. */
    private Utils utils;

    /** The current application context (Used for AWS).*/
    private Context context;

    public ApiManager(Context context) {
        this.context = context;
        utils = new Utils();
    }

    /** Fetch a list of ideas given the cluster_id. */
    public void fetchIdeas(final ArrayList<Idea> ideas, final FeedListAdapter listAdapter) {
        DataModelController dmc = DataModelController.getInstance();
        String clusterId = dmc.getClusterID();
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Authorization", "Bearer 1450909428");
        String ideaUrl = Constants.IDEAS + "?clusterid=" + clusterId;
        GetRequest request = new GetRequest(headers, params, ideaUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
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

    /** Update a cluster to have the given name and description. */
    public void putCluster(final String name, final String description) {
        final DataModelController dmc = DataModelController.getInstance();
        final String cluster_id = dmc.getClusterID();
        final String cluster_timestamp = dmc.getClusterTime();
        final StringRequest request = new StringRequest(Request.Method.PUT, Constants.CLUSTERS,
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
                params.put("cluster_id", cluster_id);
                params.put("cluster_timestamp", cluster_timestamp);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 1450909428");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);
    }

    /** Delete the cluster. */
    public void deleteCluster() {
        DataModelController dmc = DataModelController.getInstance();
        String url = Constants.CLUSTERS + "?cluster_id=" + dmc.getClusterID() +
                "&cluster_timestamp=" + dmc.getClusterTime();
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 1450909428");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);
    }


    /**
     * Post an idea to the AWS DynamoDB, with params for the idea params and a boolean to
     * determine if there is an image to be sent through
     * @param name
     * @param category
     * @param description
     * @param hasImage
     */
    public void postIdea(final String name, final String category, final String description, final boolean hasImage) {
        final DataModelController dmc = DataModelController.getInstance();
        String ideaUrl = Constants.IDEAS;

        // Initialize the Amazon Cognito credentials provider
        final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:5fba2532-65fc-4d04-8272-a508dfb32f24", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );

        final String cluster_id = dmc.getClusterID();
        final StringRequest request = new StringRequest(Request.Method.POST, ideaUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        final String imgParams = s;
                        //Add the sketched image if it exists
                        if(hasImage) {
                            AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void> () {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    File photoFile = convertImage(dmc.getIdea_image(), imgParams);
                                    AmazonS3Client amazonS3Client = new AmazonS3Client(credentialsProvider.getCredentials());
                                    PutObjectRequest por = new PutObjectRequest("idealist-sketches", imgParams, photoFile);
                                    amazonS3Client.putObject(por);
                                    return null;
                                }
                            }.execute();
                        }
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
                params.put("category", category);
                params.put("description", description);
                params.put("cluster_id", cluster_id);

                //If there is no image, set the image_url to string url (so the backend knows
                //to change the image_url to null instead of to the default s3 link.
                if(!hasImage) {
                    params.put("image_url", "null");
                }
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

    /** PUT an idea to DynamoDB. */
    public void putIdea(final String name, final String category, final String description, final boolean sketch) {
        // Initialize the Amazon Cognito credentials provider
        final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:5fba2532-65fc-4d04-8272-a508dfb32f24", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        final DataModelController dmc = DataModelController.getInstance();
        final String idea_id = dmc.getIdeaId();
        final String idea_timestamp = dmc.getIdeaTime();
        final StringRequest request = new StringRequest(Request.Method.PUT, Constants.IDEAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println(s);
                        /** if there is an updated sketch. */
                        if(sketch) {
                            AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void> () {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    File photoFile = convertImage(dmc.getIdea_image(), idea_id);
                                    AmazonS3Client amazonS3Client = new AmazonS3Client(credentialsProvider.getCredentials());
                                    PutObjectRequest por = new PutObjectRequest("idealist-sketches", idea_id, photoFile);
                                    amazonS3Client.putObject(por);
                                    System.out.println("Added image");
                                    return null;
                                }
                            }.execute();
                        }
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
                params.put("category", category);
                params.put("description", description);
                params.put("idea_id", idea_id);
                params.put("idea_timestamp", idea_timestamp);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 1450909428");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);
    }

    /** Delete the given idea. */
    public void deleteIdea() {
        DataModelController dmc = DataModelController.getInstance();
        String url = Constants.IDEAS + "?ideaid=" + dmc.getIdeaId() +
                "&ideatimestamp=" + dmc.getIdeaTime();
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer 1450909428");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);
    }

    /** Convert an image to a JPEG. */
    File convertImage(Bitmap bmp, String name) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = name + ".jpg";
        File file = new File(myDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
