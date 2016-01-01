package com.example.david_000.view;

import android.content.Intent;
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
import com.example.david_000.model.Idea;
import com.example.david_000.utils.Utils;

import java.util.ArrayList;

/**
 * Created by david_000 on 12/30/2015.
 */
public class IdealistActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** The Data Model manager. */
    private DataModelController dataModelController;

    /** The utilities. */
    private Utils utils;

    /** The list of clusters. */
    private ArrayList<Idea> ideas;

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
        View childView = getLayoutInflater().inflate(R.layout.activity_ideas, null);
        mContentLayout.addView(childView);


        //Map the action button
        FloatingActionButton newCluster = (FloatingActionButton)findViewById(R.id.new_idea);
        newCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IdealistActivity.this, PostIdeaActivity.class);
                IdealistActivity.this.startActivity(intent);
            }
        });

        // Set up the recycler view.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)childView.findViewById(R.id.idea_list);
        recyclerView.setLayoutManager(llm);

        // Instantiate the view adapter.
        listAdapter = new FeedListAdapter<Idea>(ideas, this);
        recyclerView.setAdapter(listAdapter);
        apiManager.fetchIdeas(ideas, listAdapter);


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
        ideas = new ArrayList<Idea>();
    }

    /** Go back to the clusters view. */
    @Override
    public void onBackPressed() {
        dataModelController.setIsCluster(true);
        finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }
}
