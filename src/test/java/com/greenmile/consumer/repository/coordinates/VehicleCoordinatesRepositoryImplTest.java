package com.greenmile.consumer.repository.coordinates;

import com.greenmile.consumer.configuration.TestRedisConfiguration;
import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author caioalbmelo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestRedisConfiguration.class})
public class VehicleCoordinatesRepositoryImplTest {

    private RedisTemplate template;

    private VehicleCoordinatesRepositoryImpl repository;

    @Autowired
    private LettuceConnectionFactory connFactory;

    @Before
    public void setUp() {
        template = new RedisTemplate();
        template.setConnectionFactory(connFactory);
        template.afterPropertiesSet();

        repository = new VehicleCoordinatesRepositoryImpl();
        repository.setTemplate(template);
    }

    @After
    public void tearDown() {
        connFactory.getConnection().flushAll();
    }

    @Test
    public void testThatItCanStoreCoordinates() {
        VehicleCoordinates coordinates = new VehicleCoordinates();
        coordinates.setInstant(new Date());
        coordinates.setLatitude(Math.random());
        coordinates.setLongitude(Math.random());
        coordinates.setVehicleId(UUID.randomUUID().toString());

        repository.add(coordinates);

        ZSetOperations ops = template.opsForZSet();

        assertEquals(1, ops.count(VehicleCoordinatesRepository.COORDINATES_ZSET_KEY, 0, coordinates.getInstant().getTime()), 0);

        Set<VehicleCoordinates> foundSet = ops.rangeByScore(VehicleCoordinatesRepository.COORDINATES_ZSET_KEY, 0,
                coordinates.getInstant().getTime());

        assertNotNull(foundSet);
        assertEquals(1, foundSet.size());

        VehicleCoordinates foundCoordinates = foundSet.iterator().next();
        assertEquals(coordinates.getVehicleId(), foundCoordinates.getVehicleId());
        assertEquals(coordinates.getLatitude(), foundCoordinates.getLatitude(), 0);
        assertEquals(coordinates.getLongitude(), foundCoordinates.getLongitude(), 0);
        assertEquals(coordinates.getInstant(), foundCoordinates.getInstant());
    }

    @Test
    public void testThatItCanRetrieveCoordinatesUntilGivenDate() {
        VehicleCoordinates coordinates1 = new VehicleCoordinates();
        coordinates1.setInstant(new Date());
        coordinates1.setLatitude(Math.random());
        coordinates1.setLongitude(Math.random());
        coordinates1.setVehicleId(UUID.randomUUID().toString());

        VehicleCoordinates coordinates2 = new VehicleCoordinates();
        coordinates2.setInstant(new Date(new Date().getTime() + 1000));
        coordinates2.setLatitude(Math.random());
        coordinates2.setLongitude(Math.random());
        coordinates2.setVehicleId(UUID.randomUUID().toString());

        VehicleCoordinates coordinates3 = new VehicleCoordinates();
        coordinates3.setInstant(new Date(new Date().getTime() + 100000000));
        coordinates3.setLatitude(Math.random());
        coordinates3.setLongitude(Math.random());
        coordinates3.setVehicleId(UUID.randomUUID().toString());

        repository.add(coordinates1);
        repository.add(coordinates2);
        repository.add(coordinates3);

        Set<VehicleCoordinates> coordinatesByRange = repository.getAllUntil(coordinates2.getInstant().getTime() + 2000);
        assertEquals(2, coordinatesByRange.size());

        Iterator<VehicleCoordinates> coordinatesIterator = coordinatesByRange.iterator();
        VehicleCoordinates first = coordinatesIterator.next();
        assertEquals(coordinates1.getVehicleId(), first.getVehicleId());
        assertEquals(coordinates1.getLatitude(), first.getLatitude(), 0);
        assertEquals(coordinates1.getLongitude(), first.getLongitude(), 0);
        assertEquals(coordinates1.getInstant(), first.getInstant());

        VehicleCoordinates second = coordinatesIterator.next();
        assertEquals(coordinates2.getVehicleId(), second.getVehicleId());
        assertEquals(coordinates2.getLatitude(), second.getLatitude(), 0);
        assertEquals(coordinates2.getLongitude(), second.getLongitude(), 0);
        assertEquals(coordinates2.getInstant(), second.getInstant());
    }
}
