package com.example.david_000.view;

import android.content.Context;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize data models.
        initializeDataModel();

        // Inflate the child layout
        mContentLayout  = (LinearLayout) findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_main, null);
        mContentLayout.addView(childView);

        // Set up the recycler view.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)childView.findViewById(R.id.cluster_list);
        recyclerView.setLayoutManager(llm);

        // Instantiate the view adapter.
        ArrayList<Cluster> clusters = dataModelController.getClusters();
        listAdapter = new FeedListAdapter<Cluster>(clusters, this);
        recyclerView.setAdapter(listAdapter);
        apiManager.fetchCluster(clusters, listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /** Initialize the data models to be used. */
    public void initializeDataModel() {
        utils = new Utils();
        dataModelController = DataModelController.getInstance();
        apiManager = new ApiManager();
        clusters = dataModelController.getClusters();
    }

    /** Close the app if the user gets to this screen and presses back.*/
    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
