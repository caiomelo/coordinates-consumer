package com.greenmile.consumer.repository.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caioalbmelo
 */
@Repository
public class VehicleCoordinatesRepositoryImpl implements VehicleCoordinatesRepository {

    private ZSetOperations<String, VehicleCoordinates> ops;

    @Autowired
    public void setTemplate(@Qualifier("coordinatesRedisTemplate") RedisTemplate template) {
        ops = template.opsForZSet();
    }

    @Override
    public void add(VehicleCoordinates coordinate) {
        Double score = Double.valueOf(coordinate.getInstant().getTime());
        ops.add(COORDINATES_ZSET_KEY, coordinate, score);
    }

    @Override
    public Set<VehicleCoordinates> getAllUntil(long limit) {
        return ops.rangeByScore(COORDINATES_ZSET_KEY, 0, limit);
    }

}
