package com.sl.main;

import android.util.Log;

import com.sl.base.BaseApplication;

import java.lang.reflect.Field;

public class MainApp extends BaseApplication {
    private static final String TAG = "MainApp";

    @Override
    public void initModuleApp(BaseApplication application) {
        String className = application.getPackageName() + ".BuildConfig";
        try {
            Class<?> aClass = Class.forName(className);
            Object obj = aClass.newInstance();
            Field field = aClass.getDeclaredField("BASE_URL");
            field.setAccessible(true);
            String url = (String) field.get(obj);
            Log.d(TAG, "initModuleApp: " + url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initModuleData(BaseApplication application) {
        Log.d(TAG, "initModuleData: ");
    }
}
