package com.example.ravia.weatherapplication.Model;

import java.util.List;

/**
 * Created by ravia on 11/2/2017.
 */

public class WeatherResponse {


    List<Weather> weather;
    String base;
    MainO main;
    Wind wind;
    String name;


    public MainO getMain() {
        return main;
    }

    public void setMain(MainO main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }


}
