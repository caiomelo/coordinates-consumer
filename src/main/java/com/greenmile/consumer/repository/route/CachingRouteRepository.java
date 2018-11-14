package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.model.route.Route;
import java.util.Optional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author caioalbmelo
 */
public interface CachingRouteRepository extends CrudRepository<Route, String> {

    @Override
    @Cacheable("route.by.id")
    public Optional<Route> findById(String id);

    @Override
    @Caching(
            put = {
                @CachePut(value = "route.by.id", key = "#p0.id", condition = "#result != null"),
                @CachePut(value = "route.by.vehicleId", key = "#p0.assignedVehicle", condition = "#result != null")
            }
    )
    public <S extends Route> S save(S s);

}
