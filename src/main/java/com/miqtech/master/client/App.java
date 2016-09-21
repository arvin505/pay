package com.miqtech.master.client;

import android.app.Application;


/**
 * Created by xiaoyi on 2016/9/20.
 */
public class App extends Application {
    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

    }

    public static App getApp() {
        return mApp;
    }
}
