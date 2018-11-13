package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.route.RouteUpdate;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoUpdateService {
    
    void receiveStatusUpdate(RouteUpdate update);
    
    void receiveLongestStopUpdate(RouteUpdate update);
    
    void receiveNewStopUpdate(RouteUpdate update);
    
}
