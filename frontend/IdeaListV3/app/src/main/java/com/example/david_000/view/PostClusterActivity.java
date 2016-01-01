package com.example.david_000.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.david_000.api.ApiManager;
import com.example.david_000.controller.DataModelController;
import com.example.david_000.model.Cluster;
import com.example.david_000.utils.Utils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david_000 on 12/31/2015.
 */
public class PostClusterActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** the API manager. */
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the child layout
        mContentLayout  = (LinearLayout) findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_post_cluster, null);
        mContentLayout.addView(childView);

        //Instantiate api manager
        apiManager = new ApiManager();

        //Grab the text and send it as a post request
        Button button = (Button)findViewById(R.id.create_cluster);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Grab the name
                EditText nameView = (EditText)findViewById(R.id.cluster_name);
                String name = nameView.getText().toString();

                //Grab the description
                EditText descView = (EditText)findViewById(R.id.cluster_description);
                String description = descView.getText().toString();

                if(nameView.getText().toString().equals("")) {
                    new SweetAlertDialog(PostClusterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("You must enter a cluster name")
                            .show();
                } else if(descView.getText().toString().equals("")) {
                    new SweetAlertDialog(PostClusterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("You must enter a cluster description")
                            .show();
                } else {
                    //Post the cluster
                    apiManager.postCluster(name, description);
                    new SweetAlertDialog(PostClusterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success!")
                            .setContentText("The cluster was successfully created!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(PostClusterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).show();
                }
            }
        });

    }

}
