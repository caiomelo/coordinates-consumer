package com.greenmile.consumer.repository.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import java.util.Set;

/**
 *
 * @author caioalbmelo
 */
public interface VehicleCoordinatesRepository {

    public static String COORDINATES_ZSET_KEY = "coordinates";

    void add(VehicleCoordinates coordinate);

    Set<VehicleCoordinates> getAllUntil(long limit);

}
