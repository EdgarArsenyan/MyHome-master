package com.noringerazancutyun.myapplication.models;

import java.util.List;

public class Images {

    private String mUserPhoto;
    private List<String> mHomeImage;

    public Images(String mUserPhoto, List<String> mHomeImage) {
        this.mUserPhoto = mUserPhoto;
        this.mHomeImage = mHomeImage;
    }

    public String getmUserPhoto() {
        return mUserPhoto;
    }

    public void setmUserPhoto(String mUserPhoto) {
        this.mUserPhoto = mUserPhoto;
    }

    public List<String> getmHomeImage() {
        return mHomeImage;
    }

    public void setmHomeImage(List<String> mHomeImage) {
        this.mHomeImage = mHomeImage;
    }
}
