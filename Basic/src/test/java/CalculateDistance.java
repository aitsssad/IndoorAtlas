/**
 * Created by SonamSadarangani on 10/04/2017.
 */

//J unit test to get distance from Location at the time i have set it to 0.0
import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateDistance {
    private static final double DELTA = 1e-15;
    String message = "Robert";

    @Test
    public void testPrintMessage() {

        Location locationA = new Location("point A");

        locationA.setLatitude(51.4555145);
        locationA.setLongitude(-0.372388);

        Location locationB = new Location("point B");

        locationB.setLatitude(53.52180652);
        locationB.setLongitude(-0.13020650);
        double actual=0.0;
        double distance = locationA.distanceTo(locationB);
        assertEquals(distance,actual, DELTA);
    }

}
