package com.dmytri.weather.Weather.Models;


import com.google.gson.annotations.Expose;

public class Wind {

    @Expose
    private Double speed;
    @Expose
    private Integer deg;


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
}

