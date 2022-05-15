package com.sl.news;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sl.componentbase.ILoginInterface;
import com.sl.componentbase.ServiceFactory;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "NewsDetailActivity";
    private ILoginInterface loginInterface;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            loginInterface = ILoginInterface.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: " + loginInterface);
            try {
                String logined = loginInterface.getName();
                Log.d(TAG, "onServiceConnected: " + logined);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        boolean login = ServiceFactory.getInstance().getAccountService().isLogin();
        Log.d(TAG, "onCreate: " + login);

        // 绑定进程A中的服务
        Intent intent = new Intent();
        intent.setAction("ACTION_LOGIN_SERVICE");
        intent.setPackage("com.sl.moduletest");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
}
