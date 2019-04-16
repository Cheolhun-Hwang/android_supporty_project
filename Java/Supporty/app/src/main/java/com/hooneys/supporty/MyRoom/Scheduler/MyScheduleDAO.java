package com.hooneys.supporty.MyRoom.Scheduler;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MyScheduleDAO {
    @Query("SELECT * FROM myschedules")
    List<MySchedules> getAllScheduler();

    @Query("SELECT * FROM myschedules WHERE sc_id=:id")
    MySchedules getScheduleByID(int id);

    @Query("SELECT * FROM myschedules WHERE date = :date ")
    List<MySchedules> getScheduleByDates(String date);

    @Query("SELECT * FROM myschedules WHERE date >= :date AND important = 1")
    List<MySchedules> getScheculeImportant(String date);

    @Insert
    void insert(MySchedules... node);
    @Update
    void update(MySchedules... node);
    @Delete
    void delete(MySchedules... node);
}
