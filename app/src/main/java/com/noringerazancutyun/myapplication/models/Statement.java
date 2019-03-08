package com.noringerazancutyun.myapplication.models;

public class Statement {
    private String category;
    private String type;
    private String price;
    private String rooms;
    private String floor;
    private String district;
    private String address;
    private String desc;
    private String statementImageurl;

    public Statement() {
    }

    public Statement(String category, String type, String price, String rooms, String floor, String district, String address, String desc) {
        this.category = category;
        this.type = type;
        this.price = price;
        this.rooms = rooms;
        this.floor = floor;
        this.district = district;
        this.address = address;
        this.desc = desc;
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

    public String getStatementImageurl() {
        return statementImageurl;
    }

    public void setStatementImageurl(String image) {
        this.statementImageurl = image;
    }
}
