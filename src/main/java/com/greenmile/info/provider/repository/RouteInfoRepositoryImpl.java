package com.greenmile.info.provider.repository;

import com.greenmile.info.provider.model.RouteInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author caioalbmelo
 */
@Repository
public class RouteInfoRepositoryImpl implements RouteInfoRepository {

    private HashOperations<String, String, RouteInfo> ops;

    @Autowired
    public void setTemplate(@Qualifier("routeInfoRedisTemplate") RedisTemplate template) {
        ops = template.opsForHash();
    }

    @Override
    public RouteInfo findById(String id) {
        return ops.get(ROUTES_STATUSES_KEY, id);
    }

    @Override
    public void saveStatus(RouteInfo info) {
        ops.put(ROUTES_STATUSES_KEY, info.getId(), info);
    }

    @Override
    public List<RouteInfo> getAllStatuses() {
        return ops.values(ROUTES_STATUSES_KEY);
    }

    @Override
    public void saveExecutedStop(RouteInfo info) {
        ops.put(ROUTES_STOPS_KEY, info.getId(), info);
    }

    @Override
    public List<RouteInfo> getAllExecutedStops() {
        return ops.values(ROUTES_STOPS_KEY);
    }

    @Override
    public void saveLongestStop(RouteInfo info) {
        ops.put(ROUTES_LONGEST_STOP_KEY, info.getId(), info);
    }

    @Override
    public List<RouteInfo> getAllLongestStops() {
        return ops.values(ROUTES_LONGEST_STOP_KEY);
    }
}
