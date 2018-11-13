package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author caioalbmelo
 */
public class VehicleCoordinatesServiceImplTest {

    private VehicleCoordinatesServiceImpl service;

    @Before
    public void setUp() {
        service = new VehicleCoordinatesServiceImpl();
    }
    
    @Test
    public void testThatItDoesNotExplode() {
        VehicleCoordinates coordinates = new VehicleCoordinates();
        service.receive(coordinates);
    }

}
