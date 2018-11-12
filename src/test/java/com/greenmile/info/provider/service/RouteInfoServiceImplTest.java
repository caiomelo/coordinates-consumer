package com.greenmile.info.provider.service;

import com.greenmile.info.provider.model.RouteInfo;
import com.greenmile.info.provider.repository.RouteInfoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public void testThatItDelegatesRepositoryWhenSavingRouteInfo() {
        RouteInfo info = new RouteInfo();

        service.save(info);

        verify(repositoryMock, times(1)).save(info);
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingARouteInfo() {
        RouteInfo info = new RouteInfo();

        when(repositoryMock.findById(info.getId())).thenReturn(info);

        assertEquals(info, service.findOne(info.getId()));
    }

    @Test
    public void testThatItDelegatesRepositoryWhenRetrievingAllRoutesInfoAvailable() {
        List<RouteInfo> allRoutesInfo = new ArrayList<>();

        when(repositoryMock.findAll()).thenReturn(allRoutesInfo);

        assertEquals(allRoutesInfo, service.findAll());
    }

}
