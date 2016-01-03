package com.example.david_000.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.david_000.api.ApiManager;
import com.example.david_000.controller.DataModelController;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david_000 on 1/2/2016.
 */
public class EditIdeaActivity extends BaseActivity {

    /** The Content Layout. */
    private LinearLayout mContentLayout;

    /** The API Manager. */
    private ApiManager apiManager;

    /** The old cluster name. */
    private String oldName;

    /** The old description name. */
    private String oldDesc;

    /** The old category name. */
    private String oldCat;

    /** True if user sketched something for the idea. */
    private boolean sketch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Lock the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                oldName = null;
                oldDesc = null;
                oldCat = null;
                sketch = false;
            } else {
                oldName = extras.getString("name");
                oldDesc = extras.getString("description");
                oldCat = extras.getString("category");
                sketch = extras.getBoolean("Drawn");
            }
        } else {
            oldName = (String)savedInstanceState.getSerializable("name");
            oldDesc = (String)savedInstanceState.getSerializable("description");
            oldCat = (String)savedInstanceState.getSerializable("category");
            sketch = (Boolean)savedInstanceState.getSerializable("Drawn");
        }

        System.out.println(sketch);

        // Inflate the child layout
        mContentLayout = (LinearLayout)findViewById(R.id.content_layout);
        View childView = getLayoutInflater().inflate(R.layout.activity_edit_idea, null);
        mContentLayout.addView(childView);

        //Instantiate api manager
        apiManager = new ApiManager(this);

        final EditText ideaName = (EditText)findViewById(R.id.edit_idea_name);
        final EditText ideaDesc = (EditText)findViewById(R.id.edit_idea_description);
        final EditText ideaCat = (EditText)findViewById(R.id.edit_idea_category);

        ideaName.setHint(oldName);
        ideaDesc.setHint(oldDesc);
        ideaCat.setHint(oldCat);

        //Set th click listener for the image button
        ImageButton imageButton = (ImageButton)findViewById(R.id.edit_sketch);

        //Set the new image
        if(sketch) {
            DataModelController dmc = DataModelController.getInstance();
            imageButton.setBackgroundColor(Color.WHITE);
            imageButton.setImageBitmap(dmc.getIdea_image());
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drawAct = new Intent(EditIdeaActivity.this, DrawActivity.class);
                drawAct.putExtra("Edit", true);
                drawAct.putExtra("name", oldName);
                drawAct.putExtra("description", oldDesc);
                drawAct.putExtra("category", oldCat);
                EditIdeaActivity.this.startActivity(drawAct);
            }
        });

        //Set the click listener for the button
        Button editCluster = (Button)findViewById(R.id.edit_idea);
        editCluster.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText editName = ideaName;
                EditText editDesc = ideaDesc;
                EditText editCat = ideaCat;

                String newName = editName.getText().toString();
                String newDesc = editDesc.getText().toString();
                String newCat = editCat.getText().toString();

                if (newName.equals("")) {
                    newName = oldName;
                }
                if (newDesc.equals("")) {
                    newDesc = oldDesc;
                }
                if (newCat.equals("")) {
                    newCat = oldCat;
                }
                final String finalNewName = newName;
                final String finalNewDesc = newDesc;
                final String finalNewCat = newCat;
                new SweetAlertDialog(EditIdeaActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Warning!")
                        .setContentText("Are you sure you want to edit?")
                        .setCancelText("Exit")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                apiManager.putIdea(finalNewName, finalNewCat, finalNewDesc, sketch);
                                Intent intent = new Intent(EditIdeaActivity.this, IdealistActivity.class);
                                intent.putExtra("updated", true);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        Button deleteCluster = (Button)findViewById(R.id.delete_idea);
        deleteCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(EditIdeaActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Warning!")
                        .setContentText("This cannot be undone! Are you sure?")
                        .setConfirmText("OK")
                        .setCancelText("Cancel")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                apiManager.deleteIdea();
                                final int[] i = {-1};
                                final SweetAlertDialog pDialog = new SweetAlertDialog(EditIdeaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#FF9B00"));
                                pDialog.setTitleText("Deleting");
                                pDialog.setCancelable(false);
                                pDialog.show();
                                new CountDownTimer(800 * 7, 800) {
                                    public void onTick(long millisUntilFinished) {
                                        // you can change the progress bar color by ProgressHelper every 800 millis
                                        i[0]++;
                                        switch (i[0]) {
                                            case 0:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                                break;
                                            case 1:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                                break;
                                            case 2:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                                break;
                                            case 3:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                                break;
                                            case 4:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                                break;
                                            case 5:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                                break;
                                            case 6:
                                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                                break;
                                        }
                                    }
                                    public void onFinish() {
                                        i[0] = -1;
                                        pDialog.setTitleText("Idea Successfully Deleted!")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Intent intent = new Intent(EditIdeaActivity.this, IdealistActivity.class);
                                                        intent.putExtra("updated", true);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                }.start();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        }).show();
            }
        });
    }
}
