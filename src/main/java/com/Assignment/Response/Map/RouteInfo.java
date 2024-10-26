package com.Assignment.Response.Map;

import com.Assignment.Entity.Map.Route;

public class RouteInfo {
    private String fromPincode;
    private String toPincode;
    private String distance;
    private String duration;

    public RouteInfo(String fromPincode, String toPincode, String distance, String duration) {
        this.fromPincode = fromPincode;
        this.toPincode = toPincode;
        this.distance = distance;
        this.duration = duration;
    }

    public RouteInfo(Route route) {
        this.fromPincode = route.getFromPincode();
        this.toPincode = route.getToPincode();
        this.distance = route.getDistance();
        this.duration = route.getDuration();
    }

    // Getters and setters
    public String getFromPincode() {
        return fromPincode;
    }

    public void setFromPincode(String fromPincode) {
        this.fromPincode = fromPincode;
    }

    public String getToPincode() {
        return toPincode;
    }

    public void setToPincode(String toPincode) {
        this.toPincode = toPincode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
