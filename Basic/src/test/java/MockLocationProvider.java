
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
import android.widget.TextView;
import android.widget.ListView;
import android.util.Log;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.examples.R;
import com.indooratlas.android.sdk.examples.SdkExample;
import android.provider.Settings.SettingNotFoundException;
import com.firebase.client.Firebase;
import android.widget.Toast;
import java.util.List;
import android.os.Handler;
import android.content.res.AssetManager;


import java.util.Locale;
public class MockLocationProvider {
        String providerName;
        Context ctx;

        public MockLocationProvider(String name, Context ctx) {
                this.providerName = name;
                this.ctx = ctx;

                LocationManager lm = (LocationManager) ctx.getSystemService(
                        Context.LOCATION_SERVICE);
                lm.addTestProvider(providerName, false, false, false, false, false,
                        true, true, 0, 5);
                lm.setTestProviderEnabled(providerName, true);
        }

        public void pushLocation(double lat, double lon) {
                LocationManager lm = (LocationManager) ctx.getSystemService(
                        Context.LOCATION_SERVICE);

                Location mockLocation = new Location(providerName);
                mockLocation.setLatitude(lat);
                mockLocation.setLongitude(lon);
                mockLocation.setAltitude(0);
                mockLocation.setTime(System.currentTimeMillis());
                lm.setTestProviderLocation(providerName, mockLocation);
        }

        public void shutdown() {
                LocationManager lm = (LocationManager) ctx.getSystemService(
                        Context.LOCATION_SERVICE);
                lm.removeTestProvider(providerName);
        }
}