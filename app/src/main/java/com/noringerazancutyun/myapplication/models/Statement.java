package com.noringerazancutyun.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class Statement {
    private String category;
    private String type;
    private String price;
    private String rooms;
    private String floor;
    private String district;
    private String address;
    private String desc;
    private String mImages;
    private double lat, lng;
    private String userId;
    private String statID;
    private ArrayList<String> imageList;

    public Statement() {
    }

    public Statement(String category, String type, String price, String rooms, String floor, String district, String address, String desc, String mImages) {
        this.category = category;
        this.type = type;
        this.price = price;
        this.rooms = rooms;
        this.floor = floor;
        this.district = district;
        this.address = address;
        this.desc = desc;
        this.mImages = mImages;
    }

    public Statement(String category, String type, String price, String rooms, String floor, String district, String address, String desc, double lat, double lng, ArrayList<String> imageList, String userId, String statID) {
        this.category = category;
        this.type = type;
        this.price = price;
        this.rooms = rooms;
        this.floor = floor;
        this.district = district;
        this.address = address;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.imageList = imageList;
        this.userId = userId;
        this.statID = statID;
    }

    public String getStatID() {
        return statID;
    }

    public void setStatID(String statID) {
        this.statID = statID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getmImages() {
        return mImages;
    }

    public void setmImages(String mImages) {
        this.mImages = mImages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
