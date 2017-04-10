/*
 *
 */
package com.indooratlas.android.sdk.examples.getlocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.examples.R;
import com.indooratlas.android.sdk.examples.SdkExample;
import com.firebase.client.Firebase;
import android.widget.Toast;


import java.util.Locale;
public class GetLocationActivity extends AppCompatActivity implements IALocationListener {
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private IALocationManager mLocationManager;
    private IALocationListener mLocationListner;
    private LocationManager locationManager;
    private final int CODE_PERMISSIONS = 1001;
    private TextView mLog;
    private Firebase mRef;
    private long mRequestStartTime;
    private ScrollView mScrollView;
    private static final String TAG = GetLocationActivity.class.getSimpleName();
    @SuppressWarnings("unchecked")
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_only);
        mLog = (TextView) findViewById(R.id.text);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://indooratlasexample-724f5.firebaseio.com/");
        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions( this, neededPermissions, CODE_PERMISSIONS );
        Bundle extras = new Bundle(2);
        extras.putString(IALocationManager.EXTRA_API_KEY,
                getString(R.string.indooratlas_api_key));
        extras.putString(IALocationManager.EXTRA_API_SECRET,
                getString(R.string.indooratlas_api_secret));
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "HEREeeee");
            return;
        }
        //it worked fine to get last location
        /*android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.d(TAG, "HERE");
        Log.d(TAG, "Latitude: " + location);
        Log.d(TAG, "Latitude: " + location.getLatitude());
        Log.d(TAG, "Latitude: " + location.getLongitude());*/
        //mLocationManager = IALocationManager.create(this, extras);
            Log.d(TAG, "HEREEE");

            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {

                    //do something with Lat and Lng
                    Log.d(TAG, "in on location changed");
                    Log.d(TAG, "Latitude: " + location.getLatitude());
                    Log.d(TAG, "Latitude: " + location.getLongitude());
                    log("locationChanged");
                    log("Location: " + location.getLatitude()+ "," + location.getLongitude());
                    Firebase mRefChild = mRef.child("Location");
                    mRefChild.push().setValue(location.getLatitude()+ ",   "+location.getLongitude());

                    //geo fencing

                    Location locationA = new Location("point A");

                    locationA.setLatitude(location.getLatitude());
                    locationA.setLongitude(location.getLongitude());

                    Location locationB = new Location("point B");

                    locationB.setLatitude(51.52180652);
                    locationB.setLongitude(-0.13020650);

                    float distance = locationA.distanceTo(locationB);
                    Log.d(TAG, "Location: ");
                    Log.d(TAG, "Location: " + distance);
                    if(distance <= 3)  //> 10000 //for testing purposes
                    {
                        Log.d(TAG, "outside location ");
                        Toast.makeText(GetLocationActivity.this,"You are within 3 meters of location",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                    //when user enables the GPS setting, this method is triggered.
                }

                @Override
                public void onProviderDisabled(String provider) {
                    //when no provider is available in this case GPS provider, trigger your gpsDialog here.
                }
            };

            //update location every 1sec in 2m radius with both provider GPS and Network.

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, locationListener);
            //51.52180652, -0.13020650 to set the constant location


    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Handle if any of the permissions are denied, in grantResults
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();



        //mLocationManager.requestLocationUpdates(IALocationRequest.create(), mLocationListner);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mLocationManager.removeLocationUpdates(this);
    }

    @Override
    public void onLocationChanged(IALocation location) {
        log(String.format(Locale.US, "%f,%f, accuracy: %.2f", location.getLatitude(),
                location.getLongitude(), location.getAccuracy()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log("onStatusChanged: " + status);
    }


    private void log(String msg) {
            mLog.append("\n" + msg);
        }

}
