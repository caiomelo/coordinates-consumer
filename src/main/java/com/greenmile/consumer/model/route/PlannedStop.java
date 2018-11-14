package com.greenmile.consumer.model.route;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author caioalbmelo
 */
public class PlannedStop implements Serializable {

    private double latitude;
    private double longitude;
    private String description;
    private int deliveryRadius;
    private Date startedDate;
    private Date finishedDate;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(int deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public long getDuration() {
        long duration = Long.MIN_VALUE;
        if (getStartedDate() != null && getFinishedDate() != null) {
            long finish = finishedDate.getTime();
            long start = startedDate.getTime();
            duration = finish - start;
        }
        return duration;
    }

}
