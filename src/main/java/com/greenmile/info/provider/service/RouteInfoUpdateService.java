package com.greenmile.info.provider.service;

import com.greenmile.info.provider.model.RouteUpdate;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoUpdateService {
    
    void receiveStatusUpdate(RouteUpdate update);
    
    void receiveLongestStopUpdate(RouteUpdate update);
    
    void receiveNewStopUpdate(RouteUpdate update);
    
}
