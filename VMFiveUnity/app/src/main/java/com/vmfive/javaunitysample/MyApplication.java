package com.vmfive.javaunitysample;

import android.app.Application;

import com.core.adnsdk.ADN;

/**
 * Created by ChanYiChih on 2016/6/23.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ADN.initialize(this, "5630c874cef2370b13942b8f");
    }
}