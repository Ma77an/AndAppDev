package com.example.Class4Demo.model;

import com.example.Class4Demo.Main;
import com.example.Class4Demo.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;
    @SerializedName("main")
    @Expose
    private Main main;


    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}