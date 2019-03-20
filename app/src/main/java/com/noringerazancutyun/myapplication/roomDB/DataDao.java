package com.noringerazancutyun.myapplication.roomDB;

import android.arch.lifecycle.LiveData;
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
public interface DataDao {
    @Insert
    void insert(StatData dataModel);

    @Delete
    void delete(StatData dataModel);

    @Query("SELECT * FROM StatData")
    List<StatData> getAllData();

    @Update
    void update(StatData dataModel);

    @Query("select * from StatData")
    LiveData<List<StatData>> findAll();

//    @Query("SELECT * FROM DataModel WHERE title LIKE :title")
//    List<DataModel> getAllInfo();
}
