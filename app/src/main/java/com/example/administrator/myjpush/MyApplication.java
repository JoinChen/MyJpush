package com.example.administrator.myjpush;

import android.app.Application;

/**
 * Created by Administrator on 2018\6\14 0014.
 */

public class MyApplication extends Application {

    MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
