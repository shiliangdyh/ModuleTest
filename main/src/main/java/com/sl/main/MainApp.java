package com.sl.main;

import android.util.Log;

import com.sl.base.BaseApplication;

public class MainApp extends BaseApplication {
    private static final String TAG = "MainApp";
    @Override
    public void initModuleApp(BaseApplication application) {
        Log.d(TAG, "initModuleApp: ");
    }

    @Override
    public void initModuleData(BaseApplication application) {
        Log.d(TAG, "initModuleData: ");
    }
}
