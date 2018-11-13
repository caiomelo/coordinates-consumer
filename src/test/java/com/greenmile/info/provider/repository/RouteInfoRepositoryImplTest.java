package com.greenmile.info.provider.repository;

import com.greenmile.info.provider.configuration.TestRedisConfiguration;
import com.greenmile.info.provider.model.PlannedStop;
import com.greenmile.info.provider.model.RouteInfo;
import com.greenmile.info.provider.model.RouteStatus;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 *
 * @author caioalbmelo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestRedisConfiguration.class})
public class RouteInfoRepositoryImplTest {

    private RedisTemplate template;

    private RouteInfoRepositoryImpl repository;

    @Autowired
    private LettuceConnectionFactory connFactory;

    @Before
    public void setUp() {
        template = new RedisTemplate();
        template.setConnectionFactory(connFactory);
        template.afterPropertiesSet();

        repository = new RouteInfoRepositoryImpl();
        repository.setTemplate(template);
    }

    @After
    public void tearDown() {
        connFactory.getConnection().flushAll();
    }

    @Test
    public void testThatItCanSaveAStatusUpdateForARouteInfo() {
        RouteInfo info = createRouteInfo();

        repository.saveStatus(info);

        RouteInfo foundInfo = (RouteInfo) template.opsForHash().get(RouteInfoRepository.ROUTES_STATUSES_KEY, info.getId());
        assertNotNull(foundInfo);
        assertEquals(info.getId(), foundInfo.getId());
        assertEquals(info.getStatus(), foundInfo.getStatus());
        assertEquals(info.getExecutedStops().size(), foundInfo.getExecutedStops().size());
        assertEquals(info.getLongest().getDescription(), foundInfo.getLongest().getDescription());
    }

    @Test
    public void testThatItCanSaveAExecutedStopUpdateForARouteInfo() {
        RouteInfo info = createRouteInfo();

        repository.saveExecutedStop(info);

        RouteInfo foundInfo = (RouteInfo) template.opsForHash().get(RouteInfoRepository.ROUTES_STOPS_KEY, info.getId());
        assertNotNull(foundInfo);
        assertEquals(info.getId(), foundInfo.getId());
        assertEquals(info.getStatus(), foundInfo.getStatus());
        assertEquals(info.getExecutedStops().size(), foundInfo.getExecutedStops().size());
        assertEquals(info.getLongest().getDescription(), foundInfo.getLongest().getDescription());
    }

    @Test
    public void testThatItCanSaveALongestStopUpdateForARouteInfo() {
        RouteInfo info = createRouteInfo();

        repository.saveLongestStop(info);

        RouteInfo foundInfo = (RouteInfo) template.opsForHash().get(RouteInfoRepository.ROUTES_LONGEST_STOP_KEY, info.getId());
        assertNotNull(foundInfo);
        assertEquals(info.getId(), foundInfo.getId());
        assertEquals(info.getStatus(), foundInfo.getStatus());
        assertEquals(info.getExecutedStops().size(), foundInfo.getExecutedStops().size());
        assertEquals(info.getLongest().getDescription(), foundInfo.getLongest().getDescription());
    }

    @Test
    public void testThatItCanRetrieveARouteInfo() {
        RouteInfo info = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STATUSES_KEY, info.getId(), info);

        RouteInfo found = repository.findById(info.getId());
        assertEquals(info.getId(), found.getId());
    }

    @Test
    public void testThatItCanRetrieveTheStatusRelatedInfoOnly() {
        RouteInfo statusInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STATUSES_KEY, statusInfo.getId(), statusInfo);

        RouteInfo longestStopInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_LONGEST_STOP_KEY, longestStopInfo.getId(), longestStopInfo);

        RouteInfo executedStopsInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STOPS_KEY, executedStopsInfo.getId(), executedStopsInfo);

        List<RouteInfo> allFound = repository.getAllStatuses();
        assertEquals(1, allFound.size());
        assertEquals(statusInfo.getId(), allFound.get(0).getId());
    }

    @Test
    public void testThatItCanRetrieveTheLongestStopRelatedInfoOnly() {
        RouteInfo statusInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STATUSES_KEY, statusInfo.getId(), statusInfo);

        RouteInfo longestStopInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_LONGEST_STOP_KEY, longestStopInfo.getId(), longestStopInfo);

        RouteInfo executedStopsInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STOPS_KEY, executedStopsInfo.getId(), executedStopsInfo);

        List<RouteInfo> allFound = repository.getAllLongestStops();
        assertEquals(1, allFound.size());
        assertEquals(longestStopInfo.getId(), allFound.get(0).getId());
    }

    @Test
    public void testThatItCanRetrieveTheExecutedStopsRelatedInfoOnly() {
        RouteInfo statusInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STATUSES_KEY, statusInfo.getId(), statusInfo);

        RouteInfo longestStopInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_LONGEST_STOP_KEY, longestStopInfo.getId(), longestStopInfo);

        RouteInfo executedStopsInfo = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTES_STOPS_KEY, executedStopsInfo.getId(), executedStopsInfo);

        List<RouteInfo> allFound = repository.getAllExecutedStops();
        assertEquals(1, allFound.size());
        assertEquals(executedStopsInfo.getId(), allFound.get(0).getId());
    }

    private RouteInfo createRouteInfo() {
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

        RouteInfo info = new RouteInfo();
        info.setId(UUID.randomUUID().toString());
        info.setStatus(RouteStatus.IN_PROGRESS);
        info.setLongest(plannedStop2);
        info.getExecutedStops().add(plannedStop1);
        info.getExecutedStops().add(plannedStop2);
        info.getExecutedStops().add(plannedStop3);
        return info;
    }

}
