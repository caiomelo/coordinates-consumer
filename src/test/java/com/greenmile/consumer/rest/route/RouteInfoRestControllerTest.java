package com.greenmile.consumer.rest.route;

import com.greenmile.consumer.model.route.RouteInfo;
import com.greenmile.consumer.service.route.RouteInfoService;
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
    private RouteInfoService routeInfoServiceMock;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        controller = new RouteInfoRestController();
        controller.setRouteInfoService(routeInfoServiceMock);
    }
    
    
    @Test
    public void testThatItRequestsRouteInfoServiceToProvideCurrentStatusInfoFromAllRoutes() {
        List<RouteInfo> statusesInfo = new ArrayList<>();
        when(routeInfoServiceMock.getAllStatuses()).thenReturn(statusesInfo);
        
        ResponseEntity<Map<String, List<RouteInfo>>> response = controller.statuses();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        Map<String, List<RouteInfo>> body = response.getBody();
        List<RouteInfo> statusesInfoFromBody = body.get("statuses");
        assertEquals(statusesInfo, statusesInfoFromBody);
    }
    
    @Test
    public void testThatItRequestsRouteInfoServiceToProvideCurrentInfoAboutLongestStopsForAllRoutes() {
        List<RouteInfo> longestStopsInfo = new ArrayList<>();
        when(routeInfoServiceMock.getAllLongestStops()).thenReturn(longestStopsInfo);
        
        ResponseEntity<Map<String, List<RouteInfo>>> response = controller.longestStops();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        Map<String, List<RouteInfo>> body = response.getBody();
        List<RouteInfo> longestStopsInfoFromBody = body.get("longestStops");
        assertEquals(longestStopsInfo, longestStopsInfoFromBody);
    }
    
    @Test
    public void testThatItRequestsRouteInfoServiceToProvideTheCurrentExecutedStopsForAllRoutes() {
        List<RouteInfo> executedStopsInfo = new ArrayList<>();
        when(routeInfoServiceMock.getAllExecutedStops()).thenReturn(executedStopsInfo);
        
        ResponseEntity<Map<String, List<RouteInfo>>> response = controller.executedStops();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        Map<String, List<RouteInfo>> body = response.getBody();
        List<RouteInfo> executedStopsInfoFromBody = body.get("executedStops");
        assertEquals(executedStopsInfo, executedStopsInfoFromBody);
    }
    
}
