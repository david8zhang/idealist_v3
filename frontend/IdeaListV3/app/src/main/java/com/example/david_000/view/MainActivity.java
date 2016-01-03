package com.example.david_000.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.david_000.api.ApiManager;
import com.example.david_000.controller.DataModelController;
import com.example.david_000.controller.FeedListAdapter;
import com.example.david_000.model.Cluster;
import com.example.david_000.utils.Utils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** The Data Model manager. */
    private DataModelController dataModelController;

    /** The utilities. */
    private Utils utils;

    /** The list of clusters. */
    private ArrayList<Cluster> clusters;

    /** the API manager. */
    private ApiManager apiManager;

    /** RecylcerView. */
    private RecyclerView recyclerView;

    /** The FeedListAdapter. */
    private FeedListAdapter listAdapter;

    /** The boolean indicating there is an updated cluster. */
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize data models.
        initializeDataModel();

        //If user updated
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                update = false;
            } else {
                update = extras.getBoolean("updated");
            }
        } else {
            update = (Boolean)savedInstanceState.getSerializable("updated");
        }

        // Inflate the child layout
        mContentLayout  = (LinearLayout) findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_main, null);
        mContentLayout.addView(childView);

        // Set up the recycler view.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)childView.findViewById(R.id.cluster_list);
        recyclerView.setLayoutManager(llm);

        // Set up the post new cluster button
        FloatingActionButton newCluster = (FloatingActionButton)findViewById(R.id.new_cluster);
        newCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostClusterActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Instantiate the view adapter.
        ArrayList<Cluster> clusters = dataModelController.getClusters();
        listAdapter = new FeedListAdapter<Cluster>(clusters, this);
        listAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listAdapter);
        apiManager.fetchCluster(clusters, listAdapter);

        if(update) {
            refresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Menu options selection. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /** Initialize the data models to be used. */
    public void initializeDataModel() {
        utils = new Utils();
        dataModelController = DataModelController.getInstance();
        apiManager = new ApiManager(this);
        clusters = dataModelController.getClusters();
    }

    /** Close the app if the user gets to this screen and presses back.*/
    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    /** Refresh the activity. */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void refresh() {
        clusters.clear();
        apiManager.fetchCluster(clusters, listAdapter);
        dataModelController.setClusters(clusters);
        listAdapter.notifyDataSetChanged();
    }
}
