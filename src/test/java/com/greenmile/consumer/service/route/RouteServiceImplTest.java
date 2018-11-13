package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.repository.route.RouteRepository;
import java.util.Optional;
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
    
}
