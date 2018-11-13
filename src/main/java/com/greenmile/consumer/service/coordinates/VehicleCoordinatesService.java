package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;


/**
 *
 * @author caioalbmelo
 */
public interface VehicleCoordinatesService {
    
    void receive(VehicleCoordinates coordinate);
    
}
