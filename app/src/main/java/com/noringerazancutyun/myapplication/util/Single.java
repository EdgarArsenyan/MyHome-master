package com.noringerazancutyun.myapplication.util;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.noringerazancutyun.myapplication.roomDB.DatabaseHelper;



public class Single extends Application {

    public static Single instance;
    public DatabaseHelper db;

//    public static Single getInstance() {
//        return instance;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "data-database")
                .allowMainThreadQueries()
                .build();
    }

//    public DatabaseHelper getDatabaseInstance() {
//        return db;
//    }
}
