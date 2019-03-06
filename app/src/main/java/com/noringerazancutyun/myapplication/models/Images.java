package com.noringerazancutyun.myapplication.models;

import java.util.List;

public class Images {

    private String mUserPhoto;
    private List<String> mHomeeImage;

    public Images(String mUserPhoto, List<String> mHomeeImage) {
        this.mUserPhoto = mUserPhoto;
        this.mHomeeImage = mHomeeImage;
    }

    public String getmUserPhoto() {
        return mUserPhoto;
    }

    public void setmUserPhoto(String mUserPhoto) {
        this.mUserPhoto = mUserPhoto;
    }

    public List<String> getmHomeeImage() {
        return mHomeeImage;
    }

    public void setmHomeeImage(List<String> mHomeeImage) {
        this.mHomeeImage = mHomeeImage;
    }
}
