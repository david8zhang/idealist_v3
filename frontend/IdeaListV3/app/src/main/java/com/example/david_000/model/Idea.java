package com.example.david_000.model;

import android.graphics.Bitmap;

/**
 * Created by david_000 on 12/20/2015.
 */
public class Idea {

    /** The idea title, category and text. */
    private String ideaTitle, ideaCategory, ideaText;

    /** The sketch image. */
    private Bitmap image;

    public Idea(){

    }

    public Idea(String ideaTitle, String ideaCategory, String ideaText, Bitmap image){
        super();
        this.ideaTitle = ideaTitle;
        this.ideaCategory = ideaCategory;
        this.ideaText = ideaText;
        this.image = image;
    }

    /** Getter and Setter Methods. */
    public String getIdeaTitle() {
        return ideaTitle;
    }

    public void setIdeaTitle(String ideaTitle) {
        this.ideaTitle = ideaTitle;
    }

    public String getIdeaCategory() {
        return ideaCategory;
    }

    public void setIdeaCategory(String ideaCategory) {
        this.ideaCategory = ideaCategory;
    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
