package com.greenmile.info.provider.service;

import com.greenmile.info.provider.model.RouteInfo;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoService {

    RouteInfo findById(String id);

    void saveStatus(RouteInfo info);

    List<RouteInfo> getAllStatuses();

    void saveExecutedStop(RouteInfo info);

    List<RouteInfo> getAllExecutedStops();

    void saveLongestStop(RouteInfo info);

    List<RouteInfo> getAllLongestStops();
}
