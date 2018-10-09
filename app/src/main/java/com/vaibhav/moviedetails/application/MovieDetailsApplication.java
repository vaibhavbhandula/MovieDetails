package com.vaibhav.moviedetails.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.vaibhav.moviedetails.commons.LogUtils;
import com.vaibhav.moviedetails.commons.ResourceUtils;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class MovieDetailsApplication extends MultiDexApplication {

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    @Override public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize() {
        ResourceUtils.initialize(getApplicationContext());
    }
}
