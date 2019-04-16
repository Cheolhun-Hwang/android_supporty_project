package com.hooneys.supporty.MyApp;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyApplication extends Application {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
