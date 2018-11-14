package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.PlannedStop;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author caioalbmelo
 */
public class HaversineTest {

    @Test
    public void testThatItCalculatesTheDistanceBetweenTwoCoordinatesCorrectly() {
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

    @Test
    public void testThatItCalculatesTheDistanceBetweenAPlannedStopAndACoordindateCorrectly() {
        PlannedStop plannedStop = new PlannedStop();
        plannedStop.setLatitude(-3.740376);
        plannedStop.setLongitude(-38.566944);

        VehicleCoordinates coordinates1 = new VehicleCoordinates();
        coordinates1.setLatitude(-3.762392);
        coordinates1.setLongitude(-38.483516);

        double distance = Haversine.distanceInMeters(plannedStop, coordinates1);
        assertEquals(9577, distance, 1);

        VehicleCoordinates coordinates2 = new VehicleCoordinates();
        coordinates2.setLatitude(-3.762052);
        coordinates2.setLongitude(-38.481321);

        double distance2 = Haversine.distanceInMeters(plannedStop, coordinates2);
        assertEquals(246, distance2, 1);

    }
}
