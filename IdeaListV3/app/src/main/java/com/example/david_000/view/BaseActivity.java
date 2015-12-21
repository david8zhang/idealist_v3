package com.example.david_000.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by david_000 on 12/21/2015.
 */
public class BaseActivity extends AppCompatActivity{

    /** Reference this activity. */
    private BaseActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseactivity);
    }

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    /** Override this in subclasses in order to define new layouts. */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.baseactivity);

        // TODO: Add inflated UI
        // TODO: Inflate UI provided by child activity
        // TODO: Define boilerplate UI here, like toolbars, etc.
    }
}
