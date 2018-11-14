package com.greenmile.consumer.model.route;

import com.fasterxml.jackson.annotation.JsonView;
import com.greenmile.consumer.model.Views.ExecutedStopsView;
import com.greenmile.consumer.model.Views.LongestStopView;
import com.greenmile.consumer.model.Views.RoutesView;
import com.greenmile.consumer.model.Views.StatusesView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author caioalbmelo
 */
@Document(collection = "routes")
public class Route implements Serializable {

    @JsonView(RoutesView.class)
    @Id
    private String id;

    @JsonView(RoutesView.class)
    @Indexed
    private String assignedVehicle;

    @JsonView(RoutesView.class)
    private String routePlan;

    private List<PlannedStop> plannedStops;

    @JsonView(ExecutedStopsView.class)
    private List<PlannedStop> executedStops;

    @JsonView(StatusesView.class)
    private RouteStatus status = RouteStatus.PENDING;

    @JsonView(LongestStopView.class)
    private PlannedStop longest;

    private boolean inAStop = false;

    public Route() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoutePlan() {
        return routePlan;
    }

    public void setRoutePlan(String routePlan) {
        this.routePlan = routePlan;
    }

    public String getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(String assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public List<PlannedStop> getPlannedStops() {
        if (plannedStops == null) {
            plannedStops = new ArrayList<>();
        }
        return plannedStops;
    }

    public void setPlannedStops(List<PlannedStop> plannedStops) {
        this.plannedStops = plannedStops;
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

    public boolean isInAStop() {
        return inAStop;
    }

    public void setInAStop(boolean inAStop) {
        this.inAStop = inAStop;
    }

    public boolean updateLongestStop() {
        boolean updated = false;
        PlannedStop lastExecutedStop = getExecutedStops().get(getExecutedStops().size() - 1);
        if (getLongest() == null || lastExecutedStop.getDuration() > getLongest().getDuration()) {
            setLongest(lastExecutedStop);
            updated = true;
        }
        return updated;
    }

}
