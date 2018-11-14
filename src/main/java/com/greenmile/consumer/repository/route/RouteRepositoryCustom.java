package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.model.route.Route;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public interface RouteRepositoryCustom {

    Route findByVehicleId(String vehicleId);

    List<Route> findAll();

}
