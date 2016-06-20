package com.dmytri.forecast.Weather.Models;

import com.google.gson.annotations.Expose;

/**
 * Created by Dmytri on 07.06.2016.
 */
public class Coord {
    @Expose
    private Double lon;
    @Expose
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
