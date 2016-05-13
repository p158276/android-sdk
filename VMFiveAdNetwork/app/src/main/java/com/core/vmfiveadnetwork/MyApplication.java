package com.core.vmfiveadnetwork;

import android.app.Application;

import com.core.adnsdk.ADN;

/**
 * Created by Shawn on 11/18/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ADN.initialize(this, "5630c874cef2370b13942b8f");
    }
}
