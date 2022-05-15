package com.sl.componentbase.empty_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.sl.componentbase.ILoginInterface;

public class EmptyLoginService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginInterface.Stub() {
            @Override
            public boolean isLogined() throws RemoteException {
                return true;
            }

            @Override
            public String getName() throws RemoteException {
                return "nihao";
            }
        };
    }

}
