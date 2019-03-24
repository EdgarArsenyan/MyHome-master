package com.noringerazancutyun.myapplication.roomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by gleb on 11/16/17.
 */

@Dao
public interface MyDataDao {
    @Insert
    void insert(MyStatData dataModel);

    @Delete
    void delete(MyStatData dataModel);

    @Query("SELECT * FROM MyStatData")
    List<MyStatData> getAllData();

    @Update
    void update(MyStatData dataModel);

    @Query("SELECT * FROM MyStatData")
    List<MyStatData> findAll();

//    @Query("SELECT * FROM DataModel WHERE title LIKE :title")
//    List<DataModel> getAllInfo();
}