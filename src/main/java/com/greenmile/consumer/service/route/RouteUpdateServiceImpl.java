package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.model.route.RouteStatus;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class RouteUpdateServiceImpl implements RouteUpdateService {

    private RouteInTransitHandler routeInTransitHandler = new RouteInTransitHandler();
    
    private RouteInAStopHandler routeInAStopHandler = new RouteInAStopHandler();

    public void setRouteInAStopHandler(RouteInAStopHandler routeInAStopHandler) {
        this.routeInAStopHandler = routeInAStopHandler;
    }

    public void setRouteInTransitHandler(RouteInTransitHandler routeInTransitHandler) {
        this.routeInTransitHandler = routeInTransitHandler;
    }

    @Override
    public boolean update(Route route, Set<VehicleCoordinates> coordinates) {
        boolean update;
        if (coordinates.size() == 1) {
            route.setStatus(RouteStatus.IN_PROGRESS);
            update = true;
        } else {
            update = handleInProgressRoute(route, coordinates);
        }
        return update;
    }

    private boolean handleInProgressRoute(Route route, Set<VehicleCoordinates> coordinates) {
        List<VehicleCoordinates> twoLastCoordinates = coordinates.stream().skip(coordinates.size() - 2).collect(Collectors.toList());
        boolean update;

        if (route.isInAStop()) {
            update = routeInAStopHandler.updade(route, twoLastCoordinates);
        } else {
            update = routeInTransitHandler.update(route, twoLastCoordinates);
        }

        return update;
    }
}
