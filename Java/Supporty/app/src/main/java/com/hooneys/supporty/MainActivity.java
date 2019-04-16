package com.hooneys.supporty;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hooneys.supporty.Fragments.SchedulerFragment;
import com.hooneys.supporty.Fragments.WeatherFragment;
import com.hooneys.supporty.MyApp.MyApplication;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private WeatherFragment weatherFragment;
    private SchedulerFragment schedulerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        weatherFragment = new WeatherFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.weather_frame, weatherFragment).commit();
        schedulerFragment = new SchedulerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.scheduler_frame, schedulerFragment).commit();

    }
}
