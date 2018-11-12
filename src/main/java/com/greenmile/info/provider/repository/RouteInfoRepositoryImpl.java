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
    public void save(RouteInfo info) {
        ops.put(ROUTE_INFO_KEY, info.getId(), info);
    }

    @Override
    public List<RouteInfo> findAll() {
        return ops.values(ROUTE_INFO_KEY);
    }

    @Override
    public RouteInfo findById(String id) {
        return ops.get(ROUTE_INFO_KEY, id);
    }

}
