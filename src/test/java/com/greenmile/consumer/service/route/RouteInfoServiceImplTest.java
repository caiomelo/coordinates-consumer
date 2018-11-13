package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.RouteInfo;
import com.greenmile.consumer.repository.route.RouteInfoRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author caioalbmelo
 */
public class RouteInfoServiceImplTest {

    private RouteInfoServiceImpl service;

    @Mock
    private RouteInfoRepository repositoryMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new RouteInfoServiceImpl();
        service.setRepository(repositoryMock);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenSavingRouteStatusInfo() {
        RouteInfo info = new RouteInfo();

        service.saveStatus(info);

        verify(repositoryMock, times(1)).saveStatus(info);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenSavingRouteLongestStopInfo() {
        RouteInfo info = new RouteInfo();

        service.saveLongestStop(info);

        verify(repositoryMock, times(1)).saveLongestStop(info);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenSavingRouteExecutedStopsInfo() {
        RouteInfo info = new RouteInfo();

        service.saveExecutedStop(info);

        verify(repositoryMock, times(1)).saveExecutedStop(info);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingARouteInfo() {
        RouteInfo info = new RouteInfo();

        when(repositoryMock.findById(info.getId())).thenReturn(info);

        assertEquals(info, service.findById(info.getId()));
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingAllStatusRelatedRoutesInfo() {
        List<RouteInfo> allRoutesInfo = new ArrayList<>();

        when(repositoryMock.getAllStatuses()).thenReturn(allRoutesInfo);

        assertEquals(allRoutesInfo, service.getAllStatuses());
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingAllLongestStopsRelatedRoutesInfo() {
        List<RouteInfo> allRoutesInfo = new ArrayList<>();

        when(repositoryMock.getAllLongestStops()).thenReturn(allRoutesInfo);

        assertEquals(allRoutesInfo, service.getAllLongestStops());
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingAllExecutedStopsRelatedRoutesInfo() {
        List<RouteInfo> allRoutesInfo = new ArrayList<>();

        when(repositoryMock.getAllExecutedStops()).thenReturn(allRoutesInfo);

        assertEquals(allRoutesInfo, service.getAllExecutedStops());
    }

}
