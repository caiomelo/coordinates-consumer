package com.greenmile.info.provider.model;

import java.io.Serializable;

/**
 *
 * @author caioalbmelo
 */
public class PlannedStop implements Serializable {

    private double latitude;

    private double longitude;

    private String description;

    private int deliveryRadius;

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

}
