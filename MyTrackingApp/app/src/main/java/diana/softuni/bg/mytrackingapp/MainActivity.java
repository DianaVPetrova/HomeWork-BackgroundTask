package diana.softuni.bg.mytrackingapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import diana.softuni.bg.mytrackingapp.adapter.LineAdapter;
import diana.softuni.bg.mytrackingapp.service.LocationsTrackingService;


public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;

    private LineAdapter lineAdapter;
    private List<String> myLocationsData;
    private Intent myLocationService;

    private TextView txtInfo;


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = findViewById(R.id.txtWait);

        // create a broadcast receiver to get the new data
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constants.RECEIVER_KEY));

        setupRecycler();
        myLocationService = new Intent(this, LocationsTrackingService.class);
    }


    private void setupRecycler() {

        mRecyclerView = findViewById(R.id.recycleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // get data from preferences
        String stringData = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.LOCATION_PREFERENCE_KEY, null);
        // split the data and make it an array of locations
        if(stringData!=null) {
            myLocationsData = Arrays.asList(stringData.split(Constants.SPLIT_REGEX));
        }
        else {
            myLocationsData = new ArrayList<>();
        }
        // create and set the adapter
        lineAdapter = new LineAdapter(myLocationsData);
        mRecyclerView.setAdapter(lineAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkLocationPermission();

        if (myLocationsData.isEmpty()) {
            txtInfo.setVisibility(View.VISIBLE);
        } else {
            txtInfo.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get data
            String stringData = PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                    .getString(Constants.LOCATION_PREFERENCE_KEY, "");

            myLocationsData = Arrays.asList(stringData.split("//"));
            //hide "Please wait"
           txtInfo.setVisibility(View.GONE);
            lineAdapter.changeDataItems(myLocationsData);
            // scroll to last one
            mRecyclerView.scrollToPosition(myLocationsData.size() - 1);
        }
    };

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        startService(myLocationService);

                    }

                } else {

                // TODO: make a dialog
                    Toast.makeText(this, R.string.go_to_settings, Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }


}
