package com.greenmile.consumer.model.route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "vehicle ID cannot be null")
    @Indexed
    private String assignedVehicle;

    @NotNull(message = "route plan cannot be null")
    private String routePlan;

    @NotEmpty(message = "there must be at least one planned stop")
    private List<PlannedStop> plannedStops;

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

}
