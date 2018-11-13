package com.greenmile.consumer.model;

/**
 *
 * @author caioalbmelo
 */
public class RouteUpdate {

    private String id;
    private PlannedStop longest;
    private PlannedStop newStop;
    private RouteStatus newStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlannedStop getLongest() {
        return longest;
    }

    public void setLongest(PlannedStop longest) {
        this.longest = longest;
    }

    public PlannedStop getNewStop() {
        return newStop;
    }

    public void setNewStop(PlannedStop newStop) {
        this.newStop = newStop;
    }

    public RouteStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(RouteStatus newStatus) {
        this.newStatus = newStatus;
    }
}
