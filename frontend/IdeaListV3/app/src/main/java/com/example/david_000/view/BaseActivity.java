package com.example.david_000.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by david_000 on 12/21/2015.
 */
public class BaseActivity extends AppCompatActivity{

    /** Reference this activity. */
    private BaseActivity mActivity;

    /** Toolbar for this activity. */
    protected Toolbar toolbar;

    /** Content Layout for this instance. */
    private LinearLayout mContentLayout;

    /** Constructor. */
    public BaseActivity() { mActivity = this; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    /** Override this in subclasses in order to define new layouts. */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);

        //Add inflated UI to container
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);

        //Inflate the UI provided by this activity
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View oView = inflater.inflate(layoutResID, null);
        mContentLayout.addView(oView);

        //Make sure we meet the minimum sdk level
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //Add a toolbar
            Toolbar toolbar = new Toolbar(this);
            toolbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            toolbar.setBackgroundColor(Color.parseColor("#4CBB17"));
            toolbar.setTitle("IdeaList");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setCollapsible(true);

            //Add toolbar to the contentLayout
            mContentLayout.addView(toolbar);
            setSupportActionBar(toolbar);
        }

    }
}
