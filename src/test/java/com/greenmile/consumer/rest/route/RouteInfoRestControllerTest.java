package com.greenmile.consumer.rest.route;

import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.service.route.RouteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author caioalbmelo
 */
public class RouteInfoRestControllerTest {

    private RouteInfoRestController controller;

    @Mock
    private RouteService routeServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new RouteInfoRestController();
        controller.setRouteService(routeServiceMock);
    }

    @Test
    public void testThatItRequestsRouteServiceToProvideCurrentStatusInfoFromAllRoutes() {
        List<Route> statusesInfo = new ArrayList<>();
        when(routeServiceMock.findAll()).thenReturn(statusesInfo);

        ResponseEntity<Map<String, List<Route>>> response = controller.statuses();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, List<Route>> body = response.getBody();
        List<Route> statusesInfoFromBody = body.get("statuses");
        assertEquals(statusesInfo, statusesInfoFromBody);
    }

    @Test
    public void testThatItRequestsRouteServiceToProvideCurrentInfoAboutLongestStopsForAllRoutes() {
        List<Route> longestStopsInfo = new ArrayList<>();
        when(routeServiceMock.findAll()).thenReturn(longestStopsInfo);

        ResponseEntity<Map<String, List<Route>>> response = controller.longestStops();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, List<Route>> body = response.getBody();
        List<Route> longestStopsInfoFromBody = body.get("longestStops");
        assertEquals(longestStopsInfo, longestStopsInfoFromBody);
    }

    @Test
    public void testThatItRequestsRouteServiceToProvideTheCurrentExecutedStopsForAllRoutes() {
        List<Route> executedStopsInfo = new ArrayList<>();
        when(routeServiceMock.findAll()).thenReturn(executedStopsInfo);

        ResponseEntity<Map<String, List<Route>>> response = controller.executedStops();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, List<Route>> body = response.getBody();
        List<Route> executedStopsInfoFromBody = body.get("executedStops");
        assertEquals(executedStopsInfo, executedStopsInfoFromBody);
    }

}
