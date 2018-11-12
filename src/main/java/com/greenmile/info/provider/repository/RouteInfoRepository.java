package com.greenmile.info.provider.repository;

import com.greenmile.info.provider.model.RouteInfo;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoRepository {
    
    static final String ROUTE_INFO_KEY = "route-info";
    
    void save(RouteInfo info);
    
    List<RouteInfo> findAll();
    
    RouteInfo findById(String id);
}
