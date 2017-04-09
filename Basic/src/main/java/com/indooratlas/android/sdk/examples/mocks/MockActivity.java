/*
 *
 */
package com.indooratlas.android.sdk.examples.mocks;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.os.SystemClock;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.examples.R;
import com.indooratlas.android.sdk.examples.SdkExample;

import java.lang.reflect.Method;
import java.util.Locale;


/**
 * There are two ways of setting credentials:
 * <ul>
 * <li>a) specifying as meta-data in AndroidManifest.xml</li>
 * <li>b) passing in as extra parameters via{@link IALocationManager#create(Context, Bundle)}</li>
 * </ul>
 * This example demonstrates option b).
 */
@SdkExample(description = R.string.example_credentials_description)
public class MockActivity extends AppCompatActivity implements IALocationListener {
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private IALocationManager mLocationManager;
    private IALocationListener mLocationListner;
    private LocationManager locationManager;
    private final int CODE_PERMISSIONS = 1001;
    private TextView mLog;
    private Firebase mRef;
    MockLocationProvider mock;
    private static final String TAG = MockActivity.class.getSimpleName();
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
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };
        ActivityCompat.requestPermissions( this, neededPermissions, CODE_PERMISSIONS );
        mock = new MockLocationProvider(LocationManager.NETWORK_PROVIDER, this);
        mock.pushLocation(-12.34, 23.45);

        final String providerName = "MyFancyGPSProvider";
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.getProvider(providerName) != null) {
            locationManager.removeTestProvider(providerName);
        }
        locationManager.addTestProvider(providerName, true, false, false, false, true, true, true,
                Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        Location loc = new Location(providerName);
        try {
            Method makeComplete = Location.class.getMethod("makeComplete");
            if (makeComplete != null) {
                makeComplete.invoke(loc);
            }
        } catch (Exception e) {
            //Method only available in Jelly Bean
        }
        loc.setLongitude(13);
        loc.setTime(System.currentTimeMillis());
        loc.setLatitude(10);
        loc.setAccuracy(1000);
        locationManager.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, loc);
        locationManager.setTestProviderStatus(LocationManager.NETWORK_PROVIDER,
                LocationProvider.AVAILABLE,
                null,System.currentTimeMillis());

        locationManager.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, loc);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
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
