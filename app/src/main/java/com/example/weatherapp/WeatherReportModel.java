package com.example.weatherapp;

public class WeatherReportModel {

    WeatherReportModel(){

    }
    public int id;
    public String weather_state_name;
    public String date;
    public float min_temp;
    public float max_temp;
    public float the_temp,air_pressure,humidity,visibility;

    public WeatherReportModel(int id, String weather_state_name, String date, float min_temp, float max_temp, float the_temp, float air_pressure, float humidity, float visibility) {
        this.id = id;
        this.weather_state_name = weather_state_name;
        this.date = date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
        this.air_pressure = air_pressure;
        this.humidity = humidity;
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return  weather_state_name + "date :  " + date + '\'' +
                " Low: " + min_temp +
                " Hi:  " + max_temp +
                " Current Temp: " + the_temp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public float getAir_pressure() {
        return air_pressure;
    }

    public void setAir_pressure(float air_pressure) {
        this.air_pressure = air_pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }
}
