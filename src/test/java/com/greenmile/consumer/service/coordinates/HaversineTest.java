package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author caioalbmelo
 */
public class HaversineTest {

    @Test
    public void testThatItCalculatesTheDistanceBetweenTwoPointsCorrectly() {
        VehicleCoordinates coordinates1 = new VehicleCoordinates();
        coordinates1.setLatitude(-3.740376);
        coordinates1.setLongitude(-38.566944);

        VehicleCoordinates coordinates2 = new VehicleCoordinates();
        coordinates2.setLatitude(-3.762392);
        coordinates2.setLongitude(-38.483516);

        double distance = Haversine.distanceInMeters(coordinates1, coordinates2);
        assertEquals(9577, distance, 1);

        VehicleCoordinates coordinates3 = new VehicleCoordinates();
        coordinates3.setLatitude(-3.762052);
        coordinates3.setLongitude(-38.481321);

        double distance2 = Haversine.distanceInMeters(coordinates2, coordinates3);
        assertEquals(246, distance2, 1);

    }

}
