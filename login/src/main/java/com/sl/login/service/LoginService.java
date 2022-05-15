package com.sl.login.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sl.componentbase.ILoginInterface;

public class LoginService extends Service {
    private static final String TAG = "LoginService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginInterface.Stub() {
            @Override
            public boolean isLogined() throws RemoteException {
                Log.d(TAG, "isLogined: ");
                return true;
            }

            @Override
            public String getName() throws RemoteException {
                return "login_name";
            }
        };
    }
}
