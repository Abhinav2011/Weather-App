package com.example.weatherapp;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WeatherDataService {
    public static final String QUERY_TO_GET_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    Context context;

    WeatherDataService(Context context){
        this.context = context;
    }

    //this is for getCityID
    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String CityID);
    }

    //This function will use volley library to only find the cityID
    public void getCityID(String cityName,VolleyResponseListener volleyResponseListener){
        String url = QUERY_TO_GET_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                String cityID = "";
                JSONObject cityInfo;
                try{
                    cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                if(cityID == ""){
                    //Toast.makeText(context, "City ID not available", Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onResponse("not available");
                }
                else{
                    //Toast.makeText(context,"City ID is " + cityID , Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onResponse(cityID);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Error occured");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);


    }
    public interface VolleyWeatherByID{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }
    public void getWeatherByID(String cityID,VolleyWeatherByID volleyWeatherByID){
        List<WeatherReportModel> array_of_weather = new ArrayList<>();
        String url = QUERY_FOR_WEATHER_BY_ID + cityID;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray consolidated_weather = response.getJSONArray("consolidated_weather");

                    for(int i=0;i<consolidated_weather.length();++i){
                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject weather_data_from_api = (JSONObject) consolidated_weather.get(i);
                        one_day_weather.setId(weather_data_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(weather_data_from_api.getString("weather_state_name"));
                        one_day_weather.setDate(weather_data_from_api.getString("created"));
                        one_day_weather.setMin_temp(weather_data_from_api.getLong("min_temp"));
                        one_day_weather.setMax_temp(weather_data_from_api.getLong("max_temp"));
                        one_day_weather.setThe_temp(weather_data_from_api.getLong("the_temp"));
                        one_day_weather.setAir_pressure(weather_data_from_api.getLong("air_pressure"));
                        one_day_weather.setHumidity(weather_data_from_api.getInt("humidity"));
                        one_day_weather.setVisibility(weather_data_from_api.getLong("visibility"));
                        array_of_weather.add(one_day_weather);

                    }
                    volleyWeatherByID.onResponse(array_of_weather);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyWeatherByID.onError("Error Occured");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface VolleyGetWeatherByName{

        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);


    }

    public void getWeatherByName(String cityName,VolleyGetWeatherByName volleyGetWeatherByName){
        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                volleyGetWeatherByName.onError("Error occured");
            }

            @Override
            public void onResponse(String CityID) {
                getWeatherByID(CityID, new VolleyWeatherByID() {
                    @Override
                    public void onError(String message) {
                        volleyGetWeatherByName.onError("Error Occured");
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        volleyGetWeatherByName.onResponse(weatherReportModels);
                    }
                });
            }
        });
    }

}
