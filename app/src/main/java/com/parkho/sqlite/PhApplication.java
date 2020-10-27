package com.parkho.sqlite;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class PhApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}