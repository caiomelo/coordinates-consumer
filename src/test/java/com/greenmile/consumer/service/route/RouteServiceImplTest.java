package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.repository.route.RouteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author caioalbmelo
 */
public class RouteServiceImplTest {

    private RouteServiceImpl service;

    @Mock
    private RouteRepository repositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new RouteServiceImpl();
        service.setRepository(repositoryMock);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenSavingARoute() {
        Route toSave = new Route();
        Route saved = new Route();

        when(repositoryMock.save(toSave)).thenReturn(saved);

        assertEquals(saved, service.save(toSave));
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingARouteByItsId() {
        Route retrieved = new Route();

        Optional<Route> optional = Optional.of(retrieved);

        when(repositoryMock.findById(retrieved.getId())).thenReturn(optional);

        assertEquals(optional, service.findOne(retrieved.getId()));
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingARouteByItsAssignedVehicleId() {
        Route retrieved = new Route();
        retrieved.setAssignedVehicle(UUID.randomUUID().toString());

        when(repositoryMock.findByVehicleId(retrieved.getId())).thenReturn(retrieved);

        assertEquals(retrieved, service.findByVehicleId(retrieved.getId()));
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingAllRoutes() {
        List<Route> retrieved = new ArrayList<>();

        when(repositoryMock.findAll()).thenReturn(retrieved);

        assertEquals(retrieved, service.findAll());
    }
}
