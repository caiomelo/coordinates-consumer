package com.greenmile.consumer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caioalbmelo
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class RouteInfo implements Serializable {

    private String id;
    private RouteStatus status = RouteStatus.PENDING;
    private PlannedStop longest;
    private List<PlannedStop> executedStops;

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

    public PlannedStop getLongest() {
        return longest;
    }

    public void setLongest(PlannedStop longest) {
        this.longest = longest;
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
