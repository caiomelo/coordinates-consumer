package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.Route;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author caioalbmelo
 */
public interface RouteService {

    Optional<Route> findOne(String id);

    Route save(Route route);

    Route findByVehicleId(String vehicleId);

    List<Route> findAll();
}
