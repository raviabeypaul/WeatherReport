package com.example.ravia.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.ravia.weatherapplication.Model.WeatherResponse;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherReport extends AppCompatActivity {

    @BindView(R.id.briefET)
    EditText briefET;

    @BindView(R.id.descET)
    EditText descET;

    @BindView(R.id.pressureET)
    EditText pressureET;

    @BindView(R.id.humidityET)
    EditText humidityET;

    @BindView(R.id.windET)
    EditText windET;
    WeatherResponse weatherReport;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        ButterKnife.bind(this);
        gson = new Gson();
        String a = getIntent().getStringExtra("weather");
        weatherReport  = gson.fromJson(getIntent().getStringExtra("weather"), WeatherResponse.class);
        briefET.setText(weatherReport.getWeather().getMain());
        descET.setText(weatherReport.getWeather().getDescription());
        pressureET.setText(weatherReport.getMain().getPressure());
        humidityET.setText(weatherReport.getMain().getHumidity());
        windET.setText(weatherReport.getWind().getSpeed());

    }
}
