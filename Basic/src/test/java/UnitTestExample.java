/**
 * Created by SonamSadarangani on 10/04/2017.
 */

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
public class UnitTestExample {

    String message = "Robert";

    @Test
    public void testPrintMessage() {

        assertEquals(message, "Robert");
    }

}
