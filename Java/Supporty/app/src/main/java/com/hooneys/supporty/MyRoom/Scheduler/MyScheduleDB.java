package com.hooneys.supporty.MyRoom.Scheduler;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MySchedules.class}, version = 1)
public abstract class MyScheduleDB extends RoomDatabase {
    private static final String DB_NAME = "MyScheduleDB.db";
    private static volatile MyScheduleDB instance;

    public static synchronized MyScheduleDB getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static MyScheduleDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                MyScheduleDB.class,
                DB_NAME).build();
    }

    public abstract MyScheduleDAO getMyScheduleDAO();
}
