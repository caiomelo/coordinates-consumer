package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import com.greenmile.consumer.repository.coordinates.VehicleCoordinatesRepository;
import com.greenmile.consumer.service.route.RouteService;
import com.greenmile.consumer.service.route.RouteUpdateService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
public class VehicleCoordinatesServiceImplTest {

    private VehicleCoordinatesServiceImpl service;

    @Mock
    private VehicleCoordinatesRepository repositoryMock;

    @Mock
    private RouteService routeServiceMock;

    @Mock
    private RouteUpdateService routeUpdateServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        service = new VehicleCoordinatesServiceImpl();
        service.setRepository(repositoryMock);
        service.setRouteService(routeServiceMock);
        service.setRouteUpdateService(routeUpdateServiceMock);
    }

    @Test
    public void testThatItDoesNotDoAnythingIfThereIsNoRouteForTheVehicleFromWhichTheCoordinatesWereReceived() {
        when(routeServiceMock.findByVehicleId("vehicleId")).thenReturn(null);

        VehicleCoordinates coordinates = new VehicleCoordinates();
        coordinates.setVehicleId("vehicleId");

        service.receive(coordinates);

        verify(repositoryMock, never()).add(any(Route.class), any(VehicleCoordinates.class));
        verify(repositoryMock, never()).getAllUntil(any(Route.class), anyLong());
        verify(routeUpdateServiceMock, never()).update(any(Route.class), any(Set.class));
        verify(routeServiceMock, never()).save(any(Route.class));
    }

    @Test
    public void testThatItDoesNotDoAnythingIfTheFoundRouteRelatedToTheVehicleFromWhichTheCoordinatesWereReceivedIsAlreadyFinished() {
        Route route = new Route();
        route.setStatus(RouteStatus.FINISHED);

        when(routeServiceMock.findByVehicleId("vehicleId")).thenReturn(route);

        VehicleCoordinates coordinates = new VehicleCoordinates();
        coordinates.setVehicleId("vehicleId");

        service.receive(coordinates);

        verify(repositoryMock, never()).add(any(Route.class), any(VehicleCoordinates.class));
        verify(repositoryMock, never()).getAllUntil(any(Route.class), anyLong());
        verify(routeUpdateServiceMock, never()).update(any(Route.class), any(Set.class));
        verify(routeServiceMock, never()).save(any(Route.class));
    }

    @Test
    public void testThatItAddsCoordinateAndUpdatesRouteIfRouteIsNotFinished() {
        Route route = new Route();
        route.setStatus(RouteStatus.IN_PROGRESS);

        when(routeServiceMock.findByVehicleId("vehicleId")).thenReturn(route);

        VehicleCoordinates coordinates = new VehicleCoordinates();
        coordinates.setVehicleId("vehicleId");
        coordinates.setInstant(new Date());

        Set<VehicleCoordinates> allCoordinates = new HashSet<>();
        when(repositoryMock.getAllUntil(route, coordinates.getInstant().getTime())).thenReturn(allCoordinates);

        when(routeUpdateServiceMock.update(route, allCoordinates)).thenReturn(true);

        service.receive(coordinates);

        verify(repositoryMock, times(1)).add(route, coordinates);
        verify(repositoryMock, times(1)).getAllUntil(route, coordinates.getInstant().getTime());
        verify(routeUpdateServiceMock, times(1)).update(route, allCoordinates);
        verify(routeServiceMock, times(1)).save(route);
    }

    @Test
    public void testThatItDoesNotUpdateRouteIfNothingOnItWasChanged() {
        Route route = new Route();
        route.setStatus(RouteStatus.IN_PROGRESS);

        when(routeServiceMock.findByVehicleId("vehicleId")).thenReturn(route);

        VehicleCoordinates coordinates = new VehicleCoordinates();
        coordinates.setVehicleId("vehicleId");
        coordinates.setInstant(new Date());

        Set<VehicleCoordinates> allCoordinates = new HashSet<>();
        when(repositoryMock.getAllUntil(route, coordinates.getInstant().getTime())).thenReturn(allCoordinates);

        when(routeUpdateServiceMock.update(route, allCoordinates)).thenReturn(false);

        service.receive(coordinates);

        verify(repositoryMock, times(1)).add(route, coordinates);
        verify(repositoryMock, times(1)).getAllUntil(route, coordinates.getInstant().getTime());
        verify(routeUpdateServiceMock, times(1)).update(route, allCoordinates);
        verify(routeServiceMock, never()).save(any(Route.class));
    }
}
