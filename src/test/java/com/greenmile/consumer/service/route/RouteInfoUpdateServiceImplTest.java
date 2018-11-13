package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.PlannedStop;
import com.greenmile.consumer.model.RouteInfo;
import com.greenmile.consumer.model.RouteStatus;
import com.greenmile.consumer.model.RouteUpdate;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
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
public class RouteInfoUpdateServiceImplTest {

    private RouteInfoUpdateServiceImpl service;

    @Mock
    private RouteInfoService routeInfoServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        service = new RouteInfoUpdateServiceImpl();
        service.setRouteInfoService(routeInfoServiceMock);
    }

    @Test
    public void testThatItDoesNotUpdateStatusIfRouteIdIsNotFromAValidRoute() {
        RouteUpdate update = new RouteUpdate();
        update.setId(UUID.randomUUID().toString());

        service.receiveStatusUpdate(update);

        verify(routeInfoServiceMock, never()).saveStatus(any(RouteInfo.class));
    }

    @Test
    public void testThatItUpdatesStatusIfRouteIdIsFromAValidRoute() {
        RouteUpdate update = new RouteUpdate();
        update.setNewStatus(RouteStatus.IN_PROGRESS);
        update.setId(UUID.randomUUID().toString());

        RouteInfo info = new RouteInfo();
        assertEquals(RouteStatus.PENDING, info.getStatus());

        when(routeInfoServiceMock.findById(update.getId())).thenReturn(info);

        service.receiveStatusUpdate(update);

        verify(routeInfoServiceMock, times(1)).saveStatus(any(RouteInfo.class));

        assertEquals(RouteStatus.IN_PROGRESS, info.getStatus());
    }
    
    @Test
    public void testThatItDoesNotUpdateLongestStopIfRouteIdIsNotFromAValidRoute() {
        RouteUpdate update = new RouteUpdate();
        update.setId(UUID.randomUUID().toString());

        service.receiveLongestStopUpdate(update);

        verify(routeInfoServiceMock, never()).saveLongestStop(any(RouteInfo.class));
    }

    @Test
    public void testThatItUpdatesLongestStopIfRouteIdIsFromAValidRoute() {
        PlannedStop longest = new PlannedStop();
        
        RouteUpdate update = new RouteUpdate();
        update.setNewStatus(RouteStatus.IN_PROGRESS);
        update.setId(UUID.randomUUID().toString());
        update.setLongest(longest);

        RouteInfo info = new RouteInfo();
        assertNull(info.getLongest());

        when(routeInfoServiceMock.findById(update.getId())).thenReturn(info);

        service.receiveLongestStopUpdate(update);

        verify(routeInfoServiceMock, times(1)).saveLongestStop(any(RouteInfo.class));

        assertEquals(longest, info.getLongest());
    }
    
    @Test
    public void testThatItDoesNotUpdateNewStopIfRouteIdIsNotFromAValidRoute() {
        RouteUpdate update = new RouteUpdate();
        update.setId(UUID.randomUUID().toString());

        service.receiveNewStopUpdate(update);

        verify(routeInfoServiceMock, never()).saveExecutedStop(any(RouteInfo.class));
    }

    @Test
    public void testThatItAddsNewStopIfRouteIdIsFromAValidRoute() {
        PlannedStop newStop = new PlannedStop();
        
        RouteUpdate update = new RouteUpdate();
        update.setNewStatus(RouteStatus.PENDING);
        update.setId(UUID.randomUUID().toString());
        update.setNewStop(newStop);

        RouteInfo info = new RouteInfo();
        assertEquals(0, info.getExecutedStops().size());

        when(routeInfoServiceMock.findById(update.getId())).thenReturn(info);

        service.receiveNewStopUpdate(update);

        verify(routeInfoServiceMock, times(1)).saveExecutedStop(any(RouteInfo.class));

        assertEquals(1, info.getExecutedStops().size());
        assertEquals(newStop, info.getExecutedStops().get(0));
    }

}
