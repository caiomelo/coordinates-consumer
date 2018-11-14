package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.configuration.TestRedisConfiguration;
import com.greenmile.consumer.model.route.PlannedStop;
import com.greenmile.consumer.model.route.Route;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author caioalbmelo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestRedisConfiguration.class})
public class RouteRepositoryTest {

    @Autowired
    private RouteRepository repository;

    @Autowired
    private MongoTemplate template;

    @Test
    public void testThatItSavesRouteCorrectly() {
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
        plannedStop3.setLongitude(38.471691);
        plannedStop3.setDescription("Shopping RioMar");
        plannedStop3.setDeliveryRadius(100);

        Route route = new Route();
        route.setAssignedVehicle(UUID.randomUUID().toString());
        route.setRoutePlan("A");
        route.getPlannedStops().add(plannedStop1);
        route.getPlannedStops().add(plannedStop2);
        route.getPlannedStops().add(plannedStop3);

        Route saved = repository.save(route);
        assertEquals(route.getId(), saved.getId());
        assertEquals(route.getRoutePlan(), saved.getRoutePlan());
        assertEquals(route.getAssignedVehicle(), saved.getAssignedVehicle());

        List<PlannedStop> savedPlannedStops = saved.getPlannedStops();
        assertEquals(3, savedPlannedStops.size());

        assertEquals(plannedStop1.getLatitude(), savedPlannedStops.get(0).getLatitude(), 0);
        assertEquals(plannedStop1.getLongitude(), savedPlannedStops.get(0).getLongitude(), 0);
        assertEquals(plannedStop1.getDeliveryRadius(), savedPlannedStops.get(0).getDeliveryRadius(), 0);
        assertEquals(plannedStop1.getDescription(), savedPlannedStops.get(0).getDescription());

        assertEquals(plannedStop2.getLatitude(), savedPlannedStops.get(1).getLatitude(), 0);
        assertEquals(plannedStop2.getLongitude(), savedPlannedStops.get(1).getLongitude(), 0);
        assertEquals(plannedStop2.getDeliveryRadius(), savedPlannedStops.get(1).getDeliveryRadius(), 0);
        assertEquals(plannedStop2.getDescription(), savedPlannedStops.get(1).getDescription());

        assertEquals(plannedStop3.getLatitude(), savedPlannedStops.get(2).getLatitude(), 0);
        assertEquals(plannedStop3.getLongitude(), savedPlannedStops.get(2).getLongitude(), 0);
        assertEquals(plannedStop3.getDeliveryRadius(), savedPlannedStops.get(2).getDeliveryRadius(), 0);
        assertEquals(plannedStop3.getDescription(), savedPlannedStops.get(2).getDescription());

        assertNotNull(template.findById(route.getId(), Route.class));
    }

    @Test
    public void testThatItRetrievesRouteCorrectly() {
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
        plannedStop3.setLongitude(38.471691);
        plannedStop3.setDescription("Shopping RioMar");
        plannedStop3.setDeliveryRadius(100);

        Route route = new Route();
        route.setAssignedVehicle(UUID.randomUUID().toString());
        route.setRoutePlan("A");
        route.getPlannedStops().add(plannedStop1);
        route.getPlannedStops().add(plannedStop2);
        route.getPlannedStops().add(plannedStop3);

        assertNotNull(template.save(route));

        Optional<Route> optional = repository.findById(route.getId());
        assertTrue(optional.isPresent());

        Route saved = optional.get();
        assertEquals(route.getId(), saved.getId());
        assertEquals(route.getRoutePlan(), saved.getRoutePlan());
        assertEquals(route.getAssignedVehicle(), saved.getAssignedVehicle());

        List<PlannedStop> savedPlannedStops = saved.getPlannedStops();
        assertEquals(3, savedPlannedStops.size());

        assertEquals(plannedStop1.getLatitude(), savedPlannedStops.get(0).getLatitude(), 0);
        assertEquals(plannedStop1.getLongitude(), savedPlannedStops.get(0).getLongitude(), 0);
        assertEquals(plannedStop1.getDeliveryRadius(), savedPlannedStops.get(0).getDeliveryRadius(), 0);
        assertEquals(plannedStop1.getDescription(), savedPlannedStops.get(0).getDescription());

        assertEquals(plannedStop2.getLatitude(), savedPlannedStops.get(1).getLatitude(), 0);
        assertEquals(plannedStop2.getLongitude(), savedPlannedStops.get(1).getLongitude(), 0);
        assertEquals(plannedStop2.getDeliveryRadius(), savedPlannedStops.get(1).getDeliveryRadius(), 0);
        assertEquals(plannedStop2.getDescription(), savedPlannedStops.get(1).getDescription());

        assertEquals(plannedStop3.getLatitude(), savedPlannedStops.get(2).getLatitude(), 0);
        assertEquals(plannedStop3.getLongitude(), savedPlannedStops.get(2).getLongitude(), 0);
        assertEquals(plannedStop3.getDeliveryRadius(), savedPlannedStops.get(2).getDeliveryRadius(), 0);
        assertEquals(plannedStop3.getDescription(), savedPlannedStops.get(2).getDescription());

        assertNotNull(template.findById(route.getId(), Route.class));
    }

    @Test
    public void testThatItCanRetrieveARouteByItsVehicleId() {
        Route route1 = new Route();
        route1.setAssignedVehicle(UUID.randomUUID().toString());

        Route route2 = new Route();
        route2.setAssignedVehicle(UUID.randomUUID().toString());

        template.save(route1);
        template.save(route2);

        Route foundRoute1 = repository.findByVehicleId(route1.getAssignedVehicle());
        assertEquals(route1.getId(), foundRoute1.getId());

        Route foundRoute2 = repository.findByVehicleId(route2.getAssignedVehicle());
        assertEquals(route2.getId(), foundRoute2.getId());
    }

    @Test
    public void testThatItCanRetrieveAllRoutes() {
        Route route1 = new Route();
        route1.setAssignedVehicle(UUID.randomUUID().toString());

        Route route2 = new Route();
        route2.setAssignedVehicle(UUID.randomUUID().toString());

        template.save(route1);
        template.save(route2);

        List<Route> found = repository.findAll();
        assertEquals(2, found.size());

        for (Route route : found) {
            if (!(route.getId().equals(route1.getId()) || route.getId().equals(route2.getId()))) {
                fail("Unrecognized route");
            }
        }

    }
}
