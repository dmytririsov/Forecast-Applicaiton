package com.dmytri.weather.Weather.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rain {

    @SerializedName("3h")
    @Expose
    private Double rainVar;


    public Double getRainVar () { return rainVar; }

    public void  setRainVar (Double rainVar) { this.rainVar = rainVar; }
}
