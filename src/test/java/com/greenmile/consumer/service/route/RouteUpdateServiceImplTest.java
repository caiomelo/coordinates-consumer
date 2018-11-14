package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author caioalbmelo
 */
public class RouteUpdateServiceImplTest {

    private RouteUpdateServiceImpl service;

    @Mock
    private RouteInTransitHandler routeInTransitHandler;

    @Mock
    private RouteInAStopHandler routeInAStopHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new RouteUpdateServiceImpl();
        service.setRouteInAStopHandler(routeInAStopHandler);
        service.setRouteInTransitHandler(routeInTransitHandler);
    }

    @Test
    public void testThatItOnlyUpdatesTheRouteStatusWhenReceivingTheFirstCoordinate() {
        Route route = new Route();
        Set<VehicleCoordinates> coordinates = new HashSet<>();
        coordinates.add(new VehicleCoordinates());

        assertTrue(service.update(route, coordinates));

        assertEquals(RouteStatus.IN_PROGRESS, route.getStatus());
        verify(routeInAStopHandler, never()).updade(any(Route.class), any(List.class));
        verify(routeInTransitHandler, never()).update(any(Route.class), any(List.class));
    }

    @Test
    public void testThatItDelegatesRouteInTransitHandlerToUpdateRouteIfItIsNotInAStop() {
        Route route = new Route();
        Set<VehicleCoordinates> coordinates = new HashSet<>();
        coordinates.add(new VehicleCoordinates());
        coordinates.add(new VehicleCoordinates());
        coordinates.add(new VehicleCoordinates());

        when(routeInTransitHandler.update(eq(route), any(List.class))).thenReturn(true);

        assertTrue(service.update(route, coordinates));

        ArgumentCaptor<List> coordinatesListCaptor = ArgumentCaptor.forClass(List.class);

        verify(routeInAStopHandler, never()).updade(any(Route.class), any(List.class));
        verify(routeInTransitHandler, times(1)).update(eq(route), coordinatesListCaptor.capture());

        List<VehicleCoordinates> twoLastCoordinates = coordinatesListCaptor.getValue();
        assertEquals(2, twoLastCoordinates.size());

        Iterator<VehicleCoordinates> iterator = coordinates.iterator();
        iterator.next();

        assertEquals(iterator.next(), twoLastCoordinates.get(0));
        assertEquals(iterator.next(), twoLastCoordinates.get(1));
    }

    @Test
    public void testThatItDelegatesRouteInAStopHandlerToUpdateRouteIfItIsInAStop() {
        Route route = new Route();
        route.setInAStop(true);

        Set<VehicleCoordinates> coordinates = new HashSet<>();
        coordinates.add(new VehicleCoordinates());
        coordinates.add(new VehicleCoordinates());
        coordinates.add(new VehicleCoordinates());

        when(routeInAStopHandler.updade(eq(route), anyList())).thenReturn(true);

        assertTrue(service.update(route, coordinates));

        ArgumentCaptor<List> coordinatesListCaptor = ArgumentCaptor.forClass(List.class);

        verify(routeInTransitHandler, never()).update(any(Route.class), anyList());
        verify(routeInAStopHandler, times(1)).updade(eq(route), coordinatesListCaptor.capture());

        List<VehicleCoordinates> twoLastCoordinates = coordinatesListCaptor.getValue();
        assertEquals(2, twoLastCoordinates.size());

        Iterator<VehicleCoordinates> iterator = coordinates.iterator();
        iterator.next();

        assertEquals(iterator.next(), twoLastCoordinates.get(0));
        assertEquals(iterator.next(), twoLastCoordinates.get(1));
    }

}
