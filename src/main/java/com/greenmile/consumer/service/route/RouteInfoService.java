package com.greenmile.consumer.service.route;

import com.greenmile.consumer.model.RouteInfo;
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
