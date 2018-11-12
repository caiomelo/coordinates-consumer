package com.greenmile.info.provider.repository;

import com.greenmile.info.provider.model.PlannedStop;
import com.greenmile.info.provider.model.RouteInfo;
import com.greenmile.info.provider.model.RouteStatus;
import java.util.List;
import javax.annotation.Resource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author caioalbmelo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RouteInfoRepositoryImplTest {

    @Resource(name = "routeInfoRedisTemplate")
    private RedisTemplate template;

    @Autowired
    private RouteInfoRepository repository;

    @Before
    public void setUp() {
        template.delete(RouteInfoRepository.ROUTE_INFO_KEY);
    }

    @Test
    public void testThatItCanSaveARouteInfo() {
        RouteInfo info = createRouteInfo();

        repository.save(info);

        RouteInfo foundInfo = (RouteInfo) template.opsForHash().get(RouteInfoRepository.ROUTE_INFO_KEY, info.getId());
        assertNotNull(foundInfo);
        assertEquals(info.getId(), foundInfo.getId());
        assertEquals(info.getStatus(), foundInfo.getStatus());
        assertEquals(info.getExecutedStops().size(), foundInfo.getExecutedStops().size());
        assertEquals(info.getLongest().getDescription(), foundInfo.getLongest().getDescription());
    }

    @Test
    public void testThatItCanRetrieveARouteInfo() {
        RouteInfo info = createRouteInfo();
        template.opsForHash().put(RouteInfoRepository.ROUTE_INFO_KEY, info.getId(), info);

        RouteInfo found = repository.findById(info.getId());
        assertEquals(info.getId(), found.getId());
    }

    @Test
    public void testThatItCanRetreiveAllRouteInfo() {
        RouteInfo info1 = createRouteInfo();
        RouteInfo info2 = createRouteInfo();

        template.opsForHash().put(RouteInfoRepository.ROUTE_INFO_KEY, info1.getId(), info1);
        template.opsForHash().put(RouteInfoRepository.ROUTE_INFO_KEY, info2.getId(), info2);

        List<RouteInfo> allFound = repository.findAll();
        assertEquals(2, allFound.size());

        for (RouteInfo info : allFound) {
            if (!(info.getId().equals(info1.getId()) || info.getId().equals(info2.getId()))) {
                fail("Route info not recognized");
            }
        }
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
        info.setStatus(RouteStatus.IN_PROGRESS);
        info.setLongest(plannedStop2);
        info.getExecutedStops().add(plannedStop1);
        info.getExecutedStops().add(plannedStop2);
        info.getExecutedStops().add(plannedStop3);
        return info;
    }

}
