package com.parkho.sqlite.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.parkho.sqlite.PhDatabaseContract.StudentEntry;

@Entity(tableName = StudentEntry.TABLE_NAME)
public class PhStudentEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = StudentEntry._ID)
    private int mId;

    @ColumnInfo(name = StudentEntry.GRADE)
    private int grade;

    @ColumnInfo(name = StudentEntry.NUMBER)
    private int number;

    @ColumnInfo(name = StudentEntry.NAME)
    private String name;

    @ColumnInfo(name = StudentEntry.AGE)
    private int age;


    public int getId() {
        return mId;
    }

    public void setId(int a_id) {
        mId = a_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int a_strGrade) {
        grade = a_strGrade;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int a_strNumber) {
        number = a_strNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String a_strName) {
        name = a_strName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int a_strAge) {
        age = a_strAge;
    }

}