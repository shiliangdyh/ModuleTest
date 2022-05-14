package com.sl.base;

import android.app.Application;

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    public abstract void initModuleApp(BaseApplication application);

    public abstract void initModuleData(BaseApplication application);
}
