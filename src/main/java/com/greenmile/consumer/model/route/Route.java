package com.greenmile.consumer.model.route;

import com.greenmile.consumer.model.coordinates.VehicleCoordinates;
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

    @Id
    private String id;

    @Indexed
    private String assignedVehicle;

    private String routePlan;

    private List<PlannedStop> plannedStops;

    private List<PlannedStop> executedStops;

    private RouteStatus status = RouteStatus.PENDING;

    private PlannedStop longest;

    private VehicleCoordinates lastCoordinates;

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

    public VehicleCoordinates getLastCoordinates() {
        return lastCoordinates;
    }

    public void setLastCoordinates(VehicleCoordinates lastCoordinates) {
        this.lastCoordinates = lastCoordinates;
    }

    public boolean updateLongestStop() {
        boolean updated = false;
        PlannedStop lastExecutedStop = getExecutedStops().get(getExecutedStops().size() - 1);
        if (lastExecutedStop.getDuration() > getLongest().getDuration()) {
            setLongest(lastExecutedStop);
            updated = true;
        }
        return updated;
    }

}
