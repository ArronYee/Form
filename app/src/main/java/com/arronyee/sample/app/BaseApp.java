package com.arronyee.sample.app;

import android.app.Application;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MvpManager());

    }
}
