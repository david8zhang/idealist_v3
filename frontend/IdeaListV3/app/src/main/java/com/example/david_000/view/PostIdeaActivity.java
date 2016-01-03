package com.example.david_000.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.david_000.api.ApiManager;
import com.example.david_000.controller.DataModelController;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david_000 on 12/31/2015.
 */
public class PostIdeaActivity extends BaseActivity {

    /** The content layout. */
    private LinearLayout mContentLayout;

    /** The API Manager. */
    private ApiManager apiManager;

    /** True if user sketched something for the idea. */
    private boolean sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the sketch variable
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                sketch = false;
            } else {
                sketch = extras.getBoolean("Drawn");
            }
        } else {
            sketch = (Boolean)savedInstanceState.getSerializable("Drawn");
        }

        // Inflate the child layout
        mContentLayout = (LinearLayout)findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_post_idea, null);
        mContentLayout.addView(childView);

        //Instantiate api manager
        apiManager = new ApiManager(this);

        //Map the sketch image button to a new activity
        ImageButton imgButton = (ImageButton)findViewById(R.id.sketch_button);

        // Set the image in the sketch button to be the sketch
        if(sketch) {
            DataModelController dmc = DataModelController.getInstance();
            imgButton.setBackgroundColor(Color.WHITE);
            imgButton.setImageBitmap(dmc.getIdea_image());
        }

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drawAct = new Intent(PostIdeaActivity.this, DrawActivity.class);
                finish();
                PostIdeaActivity.this.startActivity(drawAct);
            }
        });

        //Post button makes a POST request to the server
        Button button = (Button)findViewById(R.id.create_idea);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Grab the name
                EditText nameView = (EditText)findViewById(R.id.idea_name);
                EditText descView = (EditText)findViewById(R.id.idea_description);
                EditText catView = (EditText)findViewById(R.id.idea_category);

                String name = nameView.getText().toString();
                String desc = descView.getText().toString();
                String cat = catView.getText().toString();

                if(name.equals("")) {
                    new SweetAlertDialog(PostIdeaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
                            .setContentText("You must enter a name!")
                            .show();
                } else if(desc.equals("")) {
                    new SweetAlertDialog(PostIdeaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("You must enter a description!")
                            .show();
                } else if (cat.equals("")) {
                    new SweetAlertDialog(PostIdeaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("You must enter a category!")
                            .show();
                } else {
                    if(sketch) {
                        apiManager.postIdea(name, cat, desc, true);
                    } else {
                        apiManager.postIdea(name, cat, desc, false);
                    }
                    new SweetAlertDialog(PostIdeaActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success!")
                            .setContentText("The idea was successfully posted!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(PostIdeaActivity.this, IdealistActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                            }).show();
                }
            }
        });

    }
}
