package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.PlannedStop;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author caioalbmelo
 */
public class RouteInTransitHandlerTest {

    private RouteInTransitHandler handler;

    @Before
    public void setUp() {
        handler = new RouteInTransitHandler();
    }

    @Test
    public void testThatItIsAbleToVerifyThatARouteIsInAStop() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);

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
        route.setStatus(RouteStatus.IN_PROGRESS);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.762392);
        firstCoordinates.setLongitude(-38.483516);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date(new Date().getTime() + 9000));
        secondCoordinates.setLatitude(-3.762392);
        secondCoordinates.setLongitude(-38.483516);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertTrue(handler.update(route, twoLastCoordinates));
        assertTrue(route.isInAStop());
        assertEquals(1, route.getExecutedStops().size());
        assertEquals(plannedStop1, route.getExecutedStops().get(0));
        assertEquals(firstCoordinates.getInstant().getTime(), plannedStop1.getStartedDate().getTime());
    }

    @Test
    public void testThatItIdentifiesThatTheRouteIsNotInAStopIfTheTimeBetweenCoordinatesIsGreaterThanFiveMinutes() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);

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
        route.setStatus(RouteStatus.IN_PROGRESS);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.762392);
        firstCoordinates.setLongitude(-38.483516);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date(new Date().getTime() + 5 * 61000));
        secondCoordinates.setLatitude(-3.762392);
        secondCoordinates.setLongitude(-38.483516);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertFalse(handler.update(route, twoLastCoordinates));
        assertFalse(route.isInAStop());
        assertTrue(route.getExecutedStops().isEmpty());
        assertNull(plannedStop1.getStartedDate());
    }

    @Test
    public void testThatItIdentifiesThatTheRouteIsNotInAStopIfTheLastCoordinatesAreOutOfTheDeliveryRadiusOfAnyPlannedStop() {
        PlannedStop plannedStop1 = new PlannedStop();
        plannedStop1.setLatitude(-3.762392);
        plannedStop1.setLongitude(-38.483516);
        plannedStop1.setDescription("GreenMile Brasil");
        plannedStop1.setDeliveryRadius(50);

        PlannedStop plannedStop2 = new PlannedStop();
        plannedStop2.setLatitude(-3.727105);
        plannedStop2.setLongitude(-38.475576);
        plannedStop2.setDescription("Restaurante Moqueca");
        plannedStop2.setDeliveryRadius(30);

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
        route.setStatus(RouteStatus.IN_PROGRESS);

        VehicleCoordinates firstCoordinates = new VehicleCoordinates();
        firstCoordinates.setInstant(new Date());
        firstCoordinates.setLatitude(-3.762392);
        firstCoordinates.setLongitude(-38.483516);

        VehicleCoordinates secondCoordinates = new VehicleCoordinates();
        secondCoordinates.setInstant(new Date(new Date().getTime() + 59000));
        secondCoordinates.setLatitude(-3.772392);
        secondCoordinates.setLongitude(-38.493516);

        List<VehicleCoordinates> twoLastCoordinates = new ArrayList<>();
        twoLastCoordinates.add(firstCoordinates);
        twoLastCoordinates.add(secondCoordinates);

        assertFalse(handler.update(route, twoLastCoordinates));
        assertFalse(route.isInAStop());
        assertTrue(route.getExecutedStops().isEmpty());
        assertNull(plannedStop1.getStartedDate());
    }

}
