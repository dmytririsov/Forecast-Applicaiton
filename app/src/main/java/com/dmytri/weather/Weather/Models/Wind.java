package com.dmytri.weather.weather.models;


import com.google.gson.annotations.Expose;

public class Wind {

    @Expose
    private Double speed;
    @Expose
    private Integer deg;
    @Expose
    private Double gust;


    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }
}

