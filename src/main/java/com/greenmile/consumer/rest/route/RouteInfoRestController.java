package com.greenmile.consumer.rest.route;

import com.fasterxml.jackson.annotation.JsonView;
import com.greenmile.consumer.model.Views.ExecutedStopsView;
import com.greenmile.consumer.model.Views.LongestStopView;
import com.greenmile.consumer.model.Views.RoutesView;
import com.greenmile.consumer.model.Views.StatusesView;
import com.greenmile.consumer.model.route.Route;
import com.greenmile.consumer.service.route.RouteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private RouteService routeService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @JsonView(value = {RoutesView.class, StatusesView.class})
    @RequestMapping(value = "/All/Status", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Route>>> statuses() {
        List<Route> statuses = routeService.findAll();
        Map<String, List<Route>> body = new HashMap<>();
        body.put("statuses", statuses);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @JsonView(value = {RoutesView.class, LongestStopView.class})
    @RequestMapping(value = "/All/LongestStops", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Route>>> longestStops() {
        List<Route> longest = routeService.findAll();
        Map<String, List<Route>> body = new HashMap<>();
        body.put("longestStops", longest);
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @JsonView(value = {RoutesView.class, ExecutedStopsView.class})
    @RequestMapping(value = "/All/ExecutedStops", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Route>>> executedStops() {
        List<Route> executedStops = routeService.findAll();
        Map<String, List<Route>> body = new HashMap<>();
        body.put("executedStops", executedStops);
        return new ResponseEntity(body, HttpStatus.OK);
    }
}
