package com.andrew.alarmclock.data.db.alarmClock;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.andrew.alarmclock.data.entities.Alarm;

import java.util.List;

@Dao
public interface AlarmClockDao {
    @Query("select * from alarm")
    List<Alarm> getAll();

    @Query("select * from alarm where id = :id")
    Alarm getAlarmById(int id);

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);
}
