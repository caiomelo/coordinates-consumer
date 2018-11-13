package com.greenmile.consumer.repository.route;

import com.greenmile.consumer.model.RouteInfo;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
public interface RouteInfoRepository {

    static final String ROUTES_STATUSES_KEY = "routes-status";
    static final String ROUTES_LONGEST_STOP_KEY = "routes-longest";
    static final String ROUTES_STOPS_KEY = "routes-stops";

    RouteInfo findById(String id);

    void saveStatus(RouteInfo info);

    List<RouteInfo> getAllStatuses();

    void saveExecutedStop(RouteInfo info);

    List<RouteInfo> getAllExecutedStops();

    void saveLongestStop(RouteInfo info);

    List<RouteInfo> getAllLongestStops();
}
