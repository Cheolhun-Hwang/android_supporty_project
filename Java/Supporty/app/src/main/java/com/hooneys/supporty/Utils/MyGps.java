package com.hooneys.supporty.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class MyGps implements LocationListener {
    private final String TAG = MyGps.class.getSimpleName();
    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; //10미터 당
    private final long MIN_TIME_UPDATES = 1000 * 10 * 1; // 30초마다
    public final String[] permissions = {//import android.Manifest;
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private LocationManager manager;

    public MyGps(LocationManager manager) {
        this.manager = manager;
    }

    //GPS, WIFI 기능 중 하나라도 가동되어 있는지 확인하기
    public boolean checkProvider(){
        //if provider enabled just one, it is ok to use.
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e(TAG, "Location Net, GPS Func is off...");
            return false;
        } else {
            return true;
        }
    }

    //Permission Check...
    //coast, fine 둘다 check.
    public boolean checkLocationPermissions(Context context){
        boolean isAll = true;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(context, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                isAll = false;
                break;
            }
        }
        return isAll;
    }

//    public void requirePermission(final Context context){
//        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
//        alert.setTitle("권한 설정");
//        alert.setMessage("동작하기 위해서는 모든 권한이 필요합니다.");
//        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                commitPermissions(context);
//            }
//        });
//        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        alert.show();
//    }

    public void stopGps(){
        manager.removeUpdates(this);
    }

    //commit Permission
    public void commitPermissions(Context context){
//        ActivityCompat.requestPermissions(activity, permissions, this.SIG_LOCATION_PERMISSION);
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        Location gpsLocation = null, netLocation = null;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i(TAG, "Request Location Update GPS Provider");

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this
            );
        }

        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.i(TAG, "Request Location Update Net Provider");
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }

        if(manager != null) {
            Log.d(TAG, "Manager is Not Null");
            gpsLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            netLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        int count = 0;
        while (gpsLocation == null && netLocation == null){
            try {
                Thread.sleep(1000);
                Log.d(TAG, "Waiting : " + count++ + " sec");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(gpsLocation != null){
            Log.d(TAG, "gpsLocation is Not Null");
            return gpsLocation;
        }else if(netLocation != null){
            Log.d(TAG, "netLocation is Not Null");
            return netLocation;
        }else{
            Log.d(TAG, "All is Null");
            return null;
        }
    }

    //JustUsing Now!!
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
