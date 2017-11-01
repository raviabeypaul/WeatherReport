package com.example.ravia.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ravia.weatherapplication.Model.WeatherResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherReport extends AppCompatActivity {

    @BindView(R.id.briefTV)
    TextView briefTV;

    @BindView(R.id.descTV)
    TextView descTV;

    @BindView(R.id.pressureTV)
    TextView pressureTV;

    @BindView(R.id.humidityTV)
    TextView humidityTV;

    @BindView(R.id.windTV)
    TextView windTV;

    WeatherResponse weatherReport;

    Gson gson;

    @BindView(R.id.bgIV)
    ImageView bgIV;

    String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        ButterKnife.bind(this);
        gson = new Gson();
        String a = getIntent().getStringExtra("weather");
        weatherReport = gson.fromJson(getIntent().getStringExtra("weather"), WeatherResponse.class);
        Log.e(TAG, "onCreate: " + weatherReport);
        briefTV.setText(weatherReport.getWeather().get(0).getMain());
        descTV.setText(weatherReport.getWeather().get(0).getDescription());
        pressureTV.setText(weatherReport.getMain().getPressure());
        humidityTV.setText(weatherReport.getMain().getHumidity());
        windTV.setText(weatherReport.getWind().getSpeed());
        Picasso.with(getApplicationContext()).load(R.drawable.home_bg).placeholder(R.drawable.home_bg).into(bgIV);
    }
}
