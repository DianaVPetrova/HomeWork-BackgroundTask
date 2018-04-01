package diana.softuni.bg.mytrackingapp.service;


import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import diana.softuni.bg.mytrackingapp.Constants;
import diana.softuni.bg.mytrackingapp.R;

public class LocationsTrackingService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "LocationsTracking";
    Handler handler;
    private String strLocation;
    private final int UPDATE_TIME = 10000;
    private final int MIN_UPDATE_TIME = 9000;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        Log.i(TAG, "onCreate");
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }

    public void scheduleSendLocation() {

        handler.postDelayed(new Runnable() {
            public void run() {
                if(!strLocation.isEmpty()) {
                    sendMessageToActivity(strLocation);
                }
                handler.postDelayed(this, UPDATE_TIME);
            }
        }, UPDATE_TIME);
    }


    private void sendMessageToActivity(String msg) {

        SharedPreferences faves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        {
            String strLocations = faves.getString(Constants.LOCATION_PREFERENCE_KEY, "");
            // add the new location to the string
            StringBuilder strBuilder = new StringBuilder(strLocations);
            strBuilder.append(msg);
            strBuilder.append(Constants.SPLIT_REGEX);

            // save the new string
            SharedPreferences.Editor editor = faves.edit();
            editor.putString(Constants.LOCATION_PREFERENCE_KEY, strBuilder.toString());
            editor.commit();
        }

        // send a broadcast to update the list
        Intent intent = new Intent(Constants.RECEIVER_KEY);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            strLocation = getLocationLanAndLon(location);
        }

        startLocationUpdate();
        scheduleSendLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended " + i);
    }

    @Override
    public void onLocationChanged(Location location) {
        strLocation = getLocationLanAndLon(location);
    }

    private String getLocationLanAndLon(Location location){
        return String.format("lat: %.3f , lon: %.3f", location.getLatitude(), location.getLongitude());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed ");
        strLocation = getResources().getString(R.string.connection_failed);

    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_TIME);
        mLocationRequest.setFastestInterval(MIN_UPDATE_TIME);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

}