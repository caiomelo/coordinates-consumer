package com.greenmile.info.provider.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author caioalbmelo
 */
public class RouteInfo implements Serializable {

    private String id;
    private RouteStatus status;
    private PlannedStop mostTimeConsuming;
    private List<PlannedStop> executedStops;

    public RouteInfo() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RouteStatus getStatus() {
        return status;
    }

    public void setStatus(RouteStatus status) {
        this.status = status;
    }

    public PlannedStop getMostTimeConsuming() {
        return mostTimeConsuming;
    }

    public void setMostTimeConsuming(PlannedStop mostTimeConsuming) {
        this.mostTimeConsuming = mostTimeConsuming;
    }

    public List<PlannedStop> getExecutedStops() {
        if (executedStops == null) {
            executedStops = new ArrayList<>();
        }
        return executedStops;
    }

    public void setExecutedStops(List<PlannedStop> executedStops) {
        this.executedStops = executedStops;
    }

}
