package com.example.Class4Demo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getMyContext() {
        return MyApplication.context;
    }
}
