package com.hooneys.supporty.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hooneys.supporty.MainActivity;
import com.hooneys.supporty.MyApp.MyApplication;
import com.hooneys.supporty.R;
import com.hooneys.supporty.Utils.MyGps;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {
    private final String TAG = WeatherFragment.class.getSimpleName();
    public static final int SIG_LOCATION_PERMISSION = 201;
    private float nowLat, nowLon;
    private String nowLocation;
    private boolean isGetLocation;

    private ImageButton getLocation;
    private ImageView weatherIcon;
    private LinearLayout background;
    private TextView temp, minMaxTemp, wind, rain, notifyLocation;

    private MyGps myGps;
    private Thread weatherThread;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return true;
        }
    });

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        init(view);
        setEvents();
        return view;
    }

    private void setEvents() {
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isGetLocation){
                    isGetLocation = true;
                    notifyLocation.setText(getResources().getString(R.string.loading_get_location));
                    getGpsLocation();
                }
            }
        });
    }

    private void getGpsLocation() {
        if(myGps.checkLocationPermissions(getContext())){
            //Ok Permission
            if(myGps.checkProvider()){
                Location location = myGps.getLocation();
                if(location != null){
                    nowLat = (float) location.getLatitude();
                    nowLon = (float) location.getLongitude();
                    notifyLocation.setText(nowLat + " / " + nowLon);
                }else{
                    Toast.makeText(getContext(), getResources().getString(R.string.notice_need_time_to_gps), Toast.LENGTH_SHORT).show();
                    notifyLocation.setText(getResources().getString(R.string.notify_location_null));
                }
                myGps.stopGps();
            }else{
                Toast.makeText(getContext(), getResources().getString(R.string.require_gps_wifi_notice), Toast.LENGTH_SHORT).show();
                notifyLocation.setText(getResources().getString(R.string.notify_location_null));
            }
        }else{
            //Need Permission
            requestPermissions(myGps.permissions, SIG_LOCATION_PERMISSION);
            notifyLocation.setText(getResources().getString(R.string.notify_location_null));
        }
        isGetLocation = false;
    }

    private void init(View view) {
        isGetLocation = false;
        loadLatLonName();
        myGps = new MyGps((LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE));

        getLocation = (ImageButton) view.findViewById(R.id.weather_get_location_btn);
        weatherIcon = (ImageView) view.findViewById(R.id.weather_icon);
        background = (LinearLayout) view.findViewById(R.id.weather_backgournd);
        notifyLocation = (TextView) view.findViewById(R.id.weather_notify_location_text);
        temp = (TextView) view.findViewById(R.id.weather_temp);
        minMaxTemp = (TextView) view.findViewById(R.id.weather_temp_max_min);
        wind = (TextView) view.findViewById(R.id.weather_wind_speed_degree);
        rain = (TextView) view.findViewById(R.id.weather_rain);
    }

    private void saveLatLonName(){
        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("weather_lat", nowLat);
        editor.putFloat("weather_lon", nowLon);
        editor.putString("weather_location", nowLocation);
        editor.commit();
    }

    private void loadLatLonName(){
        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        nowLat = pref.getFloat("weather_lat", -1f);
        nowLon = pref.getFloat("weather_lon", -1f);
        nowLocation = pref.getString("weather_location", getResources().getString(R.string.notify_weather_location_none));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult...");
        if (requestCode == SIG_LOCATION_PERMISSION) {
            boolean isAll = true;
            for(int temp : grantResults){
                if(temp == PackageManager.PERMISSION_DENIED){
                    isAll = false;
                    break;
                }
            }
            if(isAll){
                Toast.makeText(getContext(), "모든 권한을 수락하셨습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "모든 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
