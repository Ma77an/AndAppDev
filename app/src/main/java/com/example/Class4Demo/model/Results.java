package com.example.Class4Demo.model;

import com.example.Class4Demo.Weather;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {

    @SerializedName("weather")
    private List<Weather> weatherList;

    public List<Weather> getWeather() { return weatherList; }

    public void setWeather(List<Weather> weather) { this.weatherList = weather; }
}

