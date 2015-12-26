package com.example.david_000.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.david_000.model.Cluster;
import com.example.david_000.utils.DbUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** List of clusters. */
    private List<Cluster> clusters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    @Override
    public void setContentView(int layoutResId) {
        DbUtil util = new DbUtil();
        super.setContentView(R.layout.activity_base);

        clusters = new ArrayList<Cluster>();

        //Test the api endpoint
        util.fetchCluster();
    }
}
