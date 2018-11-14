package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.PlannedStop;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author caioalbmelo
 */
public class RouteInAStopHandlerTest {

    private RouteInAStopHandler handler;

    @Before
    public void setUp() {
        handler = new RouteInAStopHandler();
    }

    @Test
    public void testThatItUpdatesRouteIfCurrentCoordinatesAreOutOfTheLastStopDeliveryRadius() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 40000));
        plannedStop1.setFinishedDate(new Date(new Date().getTime() - 30000));

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 20000));

        PlannedStop plannedStop3 = new PlannedStop();
        plannedStop3.setLatitude(-3.740905);
        plannedStop3.setLongitude(38.471691);
        plannedStop3.setDescription("Shopping RioMar");
        plannedStop3.setDeliveryRadius(100);

        Route route = new Route();
        route.setAssignedVehicle(UUID.randomUUID().toString());
        route.setRoutePlan("A");
        route.getPlannedStops().add(plannedStop1);
        route.getPlannedStops().add(plannedStop2);
        route.getPlannedStops().add(plannedStop3);
        route.setInAStop(true);
        route.setExecutedStops(Arrays.asList(plannedStop1, plannedStop2));
        route.setStatus(RouteStatus.IN_PROGRESS);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.727105);
        firstCoordinates.setLongitude(-38.475576);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date());
        secondCoordinates.setLatitude(-3.772392);
        secondCoordinates.setLongitude(-38.483516);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertTrue(handler.updade(route, twoLastCoordinates));
        assertFalse(route.isInAStop());
        assertEquals(plannedStop2.getFinishedDate().getTime(), secondCoordinates.getInstant().getTime(), 0);
        assertEquals(RouteStatus.IN_PROGRESS, route.getStatus());
    }

    @Test
    public void testThatItDoesNotUpdateRouteIfCurrenCoordinatesAreStillWithinTheLastStopDeliveryRadius() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 40000));
        plannedStop1.setFinishedDate(new Date(new Date().getTime() - 30000));

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 20000));

        PlannedStop plannedStop3 = new PlannedStop();
        plannedStop3.setLatitude(-3.740905);
        plannedStop3.setLongitude(-38.471691);
        plannedStop3.setDescription("Shopping RioMar");
        plannedStop3.setDeliveryRadius(100);

        Route route = new Route();
        route.setAssignedVehicle(UUID.randomUUID().toString());
        route.setRoutePlan("A");
        route.getPlannedStops().add(plannedStop1);
        route.getPlannedStops().add(plannedStop2);
        route.getPlannedStops().add(plannedStop3);
        route.setInAStop(true);
        route.setExecutedStops(Arrays.asList(plannedStop1, plannedStop2));
        route.setStatus(RouteStatus.IN_PROGRESS);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.727105);
        firstCoordinates.setLongitude(-38.475576);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date());
        secondCoordinates.setLatitude(-3.727106);
        secondCoordinates.setLongitude(-38.475577);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertFalse(handler.updade(route, twoLastCoordinates));
        assertTrue(route.isInAStop());
        assertNull(plannedStop2.getFinishedDate());
        assertEquals(RouteStatus.IN_PROGRESS, route.getStatus());
    }

    @Test
    public void testThatSetsRouteAsFinishedIfItJustExitedTheLastStop() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 40000));
        plannedStop1.setFinishedDate(new Date(new Date().getTime() - 30000));

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);
        plannedStop1.setStartedDate(new Date(new Date().getTime() - 20000));
        plannedStop1.setFinishedDate(new Date(new Date().getTime() - 15000));

        PlannedStop plannedStop3 = new PlannedStop();
        plannedStop3.setLatitude(-3.740905);
        plannedStop3.setLongitude(-38.471691);
        plannedStop3.setDescription("Shopping RioMar");
        plannedStop3.setDeliveryRadius(100);
        plannedStop3.setStartedDate(new Date(new Date().getTime() - 1000));

        Route route = new Route();
        route.setAssignedVehicle(UUID.randomUUID().toString());
        route.setRoutePlan("A");
        route.getPlannedStops().add(plannedStop1);
        route.getPlannedStops().add(plannedStop2);
        route.getPlannedStops().add(plannedStop3);
        route.setInAStop(true);
        route.setExecutedStops(Arrays.asList(plannedStop1, plannedStop2, plannedStop3));
        route.setLongest(plannedStop2);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.762392);
        firstCoordinates.setLongitude(-38.483516);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date());
        secondCoordinates.setLatitude(-3.772392);
        secondCoordinates.setLongitude(-38.483516);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertTrue(handler.updade(route, twoLastCoordinates));
        assertFalse(route.isInAStop());
        assertEquals(plannedStop3.getFinishedDate().getTime(), secondCoordinates.getInstant().getTime(), 0);
        assertEquals(RouteStatus.FINISHED, route.getStatus());
    }

}
