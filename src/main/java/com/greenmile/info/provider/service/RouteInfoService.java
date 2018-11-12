package com.greenmile.info.provider.service;

import com.greenmile.info.provider.model.RouteInfo;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoService {
    
    List<RouteInfo> findAll();
    
    RouteInfo findOne(String id);
    
    void save(RouteInfo info);
    
}
