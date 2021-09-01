package com.example.weatherapp;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button get_city_id, use_city_id,use_city_name;
    EditText name_typed;
    ListView weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_city_id = findViewById(R.id.get_city_id);
        use_city_id = findViewById(R.id.GetWeatherByID);
        use_city_name = findViewById(R.id.GetWeatherByName);
        name_typed = findViewById(R.id.city_text);
        weatherReport = findViewById(R.id.lv_weatherReport);
        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
        get_city_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                weatherDataService.getCityID(name_typed.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String CityID) {
                        Toast.makeText(MainActivity.this,"City ID is " +  CityID, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        use_city_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getWeatherByID(name_typed.getText().toString(), new WeatherDataService.VolleyWeatherByID() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        weatherReport.setAdapter(arrayAdapter);
                    }
                });

            }
        });

        use_city_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getWeatherByName(name_typed.getText().toString(), new WeatherDataService.VolleyGetWeatherByName() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,weatherReportModels);
                        weatherReport.setAdapter(arrayAdapter);
                    }
                });
            }
        });
    }
}