package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.PlannedStop;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.service.coordinates.Haversine;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public class RouteInTransitHandler {

    public boolean update(Route route, List<VehicleCoordinates> twoLastCoordinates) {
        boolean update = false;

        VehicleCoordinates currentCoordinates = twoLastCoordinates.get(1);
        List<PlannedStop> plannedStops = route.getPlannedStops();
        for (PlannedStop stop : plannedStops) {
            double distanceFromStop = Haversine.distanceInMeters(stop, currentCoordinates);
            if (isInDeliveryRadius(distanceFromStop, stop)) {
                VehicleCoordinates lastCoordinates = twoLastCoordinates.get(0);
                distanceFromStop = Haversine.distanceInMeters(stop, lastCoordinates);
                if (isInDeliveryRadius(distanceFromStop, stop) && noMoreThanFiveMinutesAgo(currentCoordinates, lastCoordinates)) {
                    updateRoute(route, stop, lastCoordinates);
                    update = true;
                    break;
                }
            }
        }

        return update;
    }

    private void updateRoute(Route route, PlannedStop stop, VehicleCoordinates lastCoordinates) {
        route.setInAStop(true);
        stop.setStartedDate(lastCoordinates.getInstant());
        route.getExecutedStops().add(stop);
    }

    private boolean noMoreThanFiveMinutesAgo(VehicleCoordinates currentCoordinates, VehicleCoordinates lastCoordinates) {
        return (currentCoordinates.getInstant().getTime() - lastCoordinates.getInstant().getTime()) <= 5 * 60000;
    }

    private boolean isInDeliveryRadius(double distanceFromStop, PlannedStop stop) {
        return distanceFromStop <= stop.getDeliveryRadius();
    }

}
