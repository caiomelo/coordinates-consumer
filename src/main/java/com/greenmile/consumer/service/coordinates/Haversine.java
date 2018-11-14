package com.greenmile.consumer.service.coordinates;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;

/**
 *
 * @author caioalbmelo
 */
public class Haversine {

    public static final double EARTH_RADIUS = 6372.8;

    public static double distanceInMeters(VehicleCoordinates coordinates1, VehicleCoordinates coordinates2) {
        double latitudeDelta = Math.toRadians(coordinates1.getLatitude() - coordinates2.getLatitude());
        double longitudeDelta = Math.toRadians(coordinates1.getLongitude() - coordinates2.getLongitude());

        double latitudePointOne = Math.toRadians(coordinates1.getLatitude());
        double latitudePointTwo = Math.toRadians(coordinates2.getLatitude());

        double insideSquareRoot = Math.pow(Math.sin(latitudeDelta / 2), 2)
                + Math.pow(Math.sin(longitudeDelta / 2), 2)
                * Math.cos(latitudePointOne)
                * Math.cos(latitudePointTwo);
        
        double twoTimesArcsin = 2 * Math.asin(Math.sqrt(insideSquareRoot));
        
        return EARTH_RADIUS * twoTimesArcsin * 1000;
    }

}
