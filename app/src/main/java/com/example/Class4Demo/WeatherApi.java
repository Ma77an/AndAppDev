package com.example.Class4Demo;

import com.example.Class4Demo.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather?APPID=d154e01470f2e0a318ad97ac7b413442&units=metric")
    Call<Results> getWeatherData(@Query("q") String name);
}
