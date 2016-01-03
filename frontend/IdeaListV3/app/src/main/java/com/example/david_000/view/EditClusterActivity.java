package com.example.david_000.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.david_000.api.ApiManager;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david_000 on 1/2/2016.
 */
public class EditClusterActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** The API Manager. */
    private ApiManager apiManager;

    /** The old cluster name. */
    private String oldName;

    /** The old description name. */
    private String oldDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                oldName = null;
                oldDesc = null;
            } else {
                oldName = extras.getString("name");
                oldDesc = extras.getString("description");
            }
        } else {
            oldName = (String)savedInstanceState.getSerializable("name");
            oldDesc = (String)savedInstanceState.getSerializable("description");
        }

        // Inflate the child layout
        mContentLayout = (LinearLayout)findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_edit_cluster, null);
        mContentLayout.addView(childView);

        //Instantiate api manager
        apiManager = new ApiManager(this);

        final EditText clusterName = (EditText)findViewById(R.id.edit_cluster_name);
        final EditText clusterDesc = (EditText)findViewById(R.id.edit_cluster_description);

        clusterName.setHint(oldName);
        clusterDesc.setHint(oldDesc);

        //Set the click listener for the button
        Button editCluster = (Button)findViewById(R.id.edit_cluster);
        editCluster.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText editName = clusterName;
                EditText editDesc = clusterDesc;

                String newName = editName.getText().toString();
                String newDesc = editDesc.getText().toString();

                if(newName.equals("")) {
                    newName = oldName;
                }
                if(newDesc.equals("")) {
                    newDesc = oldDesc;
                }
                final String finalNewName = newName;
                final String finalNewDesc = newDesc;
                new SweetAlertDialog(EditClusterActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Warning!")
                        .setContentText("Are you sure you want to edit?")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (!finalNewName.equals(oldName) || !finalNewDesc.equals(oldDesc)) {
                                    apiManager.putCluster(finalNewName, finalNewDesc);
                                    Intent intent = new Intent(EditClusterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(EditClusterActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }
                        }).show();
            }
        });


    }
}
