package com.greenmile.info.provider.rest;

import com.greenmile.info.provider.model.RouteInfo;
import com.greenmile.info.provider.service.RouteInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author caioalbmelo
 */
@RestController
@RequestMapping("/RoutesInfo")
public class RouteInfoRestController {

    private RouteInfoService routeInfoService;

    @Autowired
    public void setRouteInfoService(RouteInfoService routeInfoService) {
        this.routeInfoService = routeInfoService;
    }

    @RequestMapping(value = "/All/Status", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<RouteInfo>>> statuses() {
        List<RouteInfo> statuses = routeInfoService.getAllStatuses();
        Map<String, List<RouteInfo>> body = new HashMap<>();
        body.put("statuses", statuses);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @RequestMapping(value = "/All/Longest", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<RouteInfo>>> longestStops() {
        List<RouteInfo> longest = routeInfoService.getAllLongestStops();
        Map<String, List<RouteInfo>> body = new HashMap<>();
        body.put("longestStops", longest);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @RequestMapping(value = "/All/ExecutedStops", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<RouteInfo>>> executedStops() {
        List<RouteInfo> executedStops = routeInfoService.getAllExecutedStops();
        Map<String, List<RouteInfo>> body = new HashMap<>();
        body.put("executedStops", executedStops);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
