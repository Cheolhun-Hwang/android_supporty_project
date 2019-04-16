package com.hooneys.supporty.MyRoom.Scheduler;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MySchedules {
    @PrimaryKey(autoGenerate = true)
    public int sc_id;

    @ColumnInfo(name = "date")
    public int date;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "body")
    public String body;
    @ColumnInfo(name = "sheet")
    public int sheet;
    @ColumnInfo(name = "important")         //0 : false, 1 : true
    public int important;
}
