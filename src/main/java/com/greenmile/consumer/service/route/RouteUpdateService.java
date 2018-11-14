package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import java.util.Set;

/**
 *
 * @author caioalbmelo
 */
public interface RouteUpdateService {
    
    boolean update(Route route, Set<VehicleCoordinates> coordinates);
    
}
