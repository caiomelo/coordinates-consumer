package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.RouteInfo;
import com.greenmile.consumer.model.route.RouteUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author caioalbmelo
 */
@Service
public class RouteInfoUpdateServiceImpl implements RouteInfoUpdateService {

    private RouteInfoService routeInfoService;

    @Autowired
    public void setRouteInfoService(RouteInfoService routeInfoService) {
        this.routeInfoService = routeInfoService;
    }

    @Override
    public void receiveStatusUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.setStatus(update.getNewStatus());
            routeInfoService.saveStatus(found);
        }
    }

    @Override
    public void receiveLongestStopUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.setLongest(update.getLongest());
            routeInfoService.saveLongestStop(found);
        }
    }

    @Override
    public void receiveNewStopUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.getExecutedStops().add(update.getNewStop());
            routeInfoService.saveExecutedStop(found);
        }
    }

}
