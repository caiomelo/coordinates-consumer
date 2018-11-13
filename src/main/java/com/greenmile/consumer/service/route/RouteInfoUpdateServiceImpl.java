package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.RouteInfo;
import com.greenmile.consumer.model.RouteUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "route-status-update", containerFactory = "routeUpdateListenerContainerFactory")
    @Override
    public void receiveStatusUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.setStatus(update.getNewStatus());
            routeInfoService.saveStatus(found);
        }
    }

    @KafkaListener(topics = "route-longest-stop-update", containerFactory = "routeUpdateListenerContainerFactory")
    @Override
    public void receiveLongestStopUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.setLongest(update.getLongest());
            routeInfoService.saveLongestStop(found);
        }
    }

    @KafkaListener(topics = "route-new-stop-update", containerFactory = "routeUpdateListenerContainerFactory")
    @Override
    public void receiveNewStopUpdate(RouteUpdate update) {
        RouteInfo found = routeInfoService.findById(update.getId());
        if (found != null) {
            found.getExecutedStops().add(update.getNewStop());
            routeInfoService.saveExecutedStop(found);
        }
    }

}
