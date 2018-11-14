package com.greenmile.consumer.repository.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import java.util.Set;

/**
 *
 * @author caioalbmelo
 */
public interface VehicleCoordinatesRepository {

    public static String COORDINATES_ZSET_KEY = "coordinates";

    void add(VehicleCoordinates coordinate);
    
    void add(Route route, VehicleCoordinates coordinate);

    Set<VehicleCoordinates> getAllUntil(long limit);
    
    Set<VehicleCoordinates> getAllUntil(Route route, long limit);
    
    String buildRouteKey(Route route);
    
}
