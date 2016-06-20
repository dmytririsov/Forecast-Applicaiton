package com.dmytri.forecast.Weather.Models;


import com.google.gson.annotations.Expose;

public class Sys {

    @Expose
    private Integer type;
    @Expose
    private Integer id;
    @Expose
    private Double message;
    @Expose
    private String country;
    @Expose
    private Integer sunrise;
    @Expose
    private Integer sunset;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
}

