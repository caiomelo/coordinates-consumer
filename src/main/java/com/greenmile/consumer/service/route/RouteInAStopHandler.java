package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.PlannedStop;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import com.greenmile.consumer.service.coordinates.Haversine;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public class RouteInAStopHandler {

    public boolean updade(Route route, List<VehicleCoordinates> twoLastCoordinates) {
        boolean update = false;

        List<PlannedStop> executedStops = route.getExecutedStops();
        PlannedStop currentStop = executedStops.get(executedStops.size() - 1);

        VehicleCoordinates currentCoordinates = twoLastCoordinates.get(1);
        double distanceFromStop = Haversine.distanceInMeters(currentStop, currentCoordinates);

        if (!isInDeliveryRadius(distanceFromStop, currentStop)) {
            updateRoute(route, currentStop, currentCoordinates, executedStops);
            update = true;
        }

        return update;
    }

    private void updateRoute(Route route, PlannedStop currentStop, VehicleCoordinates currentCoordinates, List<PlannedStop> executedStops) {
        route.setInAStop(false);
        currentStop.setFinishedDate(currentCoordinates.getInstant());
        if (route.getPlannedStops().size() == executedStops.size()) {
            route.setStatus(RouteStatus.FINISHED);
        }
        route.updateLongestStop();
    }

    private boolean isInDeliveryRadius(double distanceFromStop, PlannedStop stop) {
        return distanceFromStop <= stop.getDeliveryRadius();
    }
}
