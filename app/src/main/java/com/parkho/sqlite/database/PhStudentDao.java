package com.parkho.sqlite.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.parkho.sqlite.PhDatabaseContract.StudentEntry;

import java.util.List;

@Dao
public interface PhStudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(PhStudentEntity a_studentEntity);

    @Update
    void update(PhStudentEntity a_studentEntity);

    @Query("DELETE FROM " + StudentEntry.TABLE_NAME + " WHERE _id = :a_id")
    void deleteById(int a_id);

    @Query("SELECT * FROM " + StudentEntry.TABLE_NAME)
    LiveData<List<PhStudentEntity>> getAllStudents();

}