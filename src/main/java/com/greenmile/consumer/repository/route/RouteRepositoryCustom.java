package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.model.route.Route;

/**
 *
 * @author caioalbmelo
 */
public interface RouteRepositoryCustom {
    
    Route findByVehicleId(String vehicleId);
    
}
