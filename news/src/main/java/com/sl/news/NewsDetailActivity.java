package com.sl.news;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sl.componentbase.ServiceFactory;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "NewsDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        boolean login = ServiceFactory.getInstance().getAccountService().isLogin();
        Log.d(TAG, "onCreate: " + login);
    }
}
