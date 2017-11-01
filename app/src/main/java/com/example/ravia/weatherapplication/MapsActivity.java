package com.example.ravia.weatherapplication;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ravia.weatherapplication.Model.Coord;
import com.example.ravia.weatherapplication.Model.GenericModel;
import com.example.ravia.weatherapplication.Model.GenericModelArr;
import com.example.ravia.weatherapplication.Model.Weather;
import com.example.ravia.weatherapplication.Model.WeatherResponse;
import com.example.ravia.weatherapplication.Utils.CommonUtility;
import com.example.ravia.weatherapplication.Utils.VolleyResponseListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager mLocationManager = null;
    private String provider = null;
    private LatLng mSourceLatLng = null;
    private Marker mCurrentPosition = null;
    String TAG = getClass().getName();
    CommonUtility cUtils;
    String apikey = "587d2b2798c7750c2c03a2cfc9745745";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        cUtils = new CommonUtility();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                final Location location = new Location("");
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                updateWithNewLocation(location);
            }
        });
        // Add a marker in Sydney and move the camera


        if (isProviderAvailable() && (provider != null)) {
            locateCurrentPosition();
        }




//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void locateCurrentPosition() {

        int status = getPackageManager().checkPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {
            Location location = mLocationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            //  mLocationManager.addGpsStatusListener(this);
            long minTime = 5000;// ms
            float minDist = 5.0f;// meter
            mLocationManager.requestLocationUpdates(provider, minTime, minDist,
                    this);
        }
    }


    private void updateWithNewLocation(Location location) {

        if (location != null && provider != null) {
            double lng = location.getLongitude();
            double lat = location.getLatitude();

            mSourceLatLng = new LatLng(lat, lng);
//            String url = "http://api.openweathermap.org/data/2.5/" + "weather?" + "lat=" + Float.toString(lat) + "&" + "lon=" + Float.toString(lon) + "&" + "mode=" + this.mode + "&" + "units=" + this.units + "&" + "appId=" + this.appId;

            addMarker(lat, lng);

            CameraPosition camPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)).zoom(10f).build();

            if (mMap != null)
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(camPosition));
        } else {
            Log.d("Location error", "Something went wrong");
        }

    }


    private void addMarker(final double lat, final double lang) {

        MarkerOptions mMarkerOptions = new MarkerOptions();
        mMarkerOptions.position(new LatLng(lat, lang));

//        mMarkerOptions.icon(BitmapDescriptorFactory
//                .fromResource(R.drawable.));
        mMarkerOptions.anchor(0.5f, 0.5f);


//        CircleOptions mOptions = new CircleOptions()
//                .center(new LatLng(lat, lang)).radius(10000)
//                .strokeColor(0x110000FF).strokeWidth(1).fillColor(0x110000FF);
//        mMap.addCircle(mOptions);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                String url = "http://api.openweathermap.org/data/2.5/" + "weather?" + "lat=" + Double.toString(lat) + "&" + "lon=" + Double.toString(lang) + "&" + "mode=json" + "&" + "units=metric"  + "&" + "appId=" + apikey;



                cUtils.stringRequest(Request.Method.GET, url, new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.e(TAG, "onError: " + message );
                    }

                    @Override
                    public void onResponse(Object response) {
                        Log.e(TAG, "onResponse: " + response );
                        WeatherResponse  weatherResponse = new WeatherResponse();
                        Gson gson = new Gson();
                        weatherResponse = gson.fromJson(response.toString(),WeatherResponse.class);

                        String a = gson.toJson(weatherResponse);
                        Log.e(TAG, "onResponse: " + a );
                        Intent intent = new Intent(MapsActivity.this,WeatherReport.class);
                        intent.putExtra("weather",a);
                        startActivity(intent);
                    }
                },null);

                return true;
            }
        });
        if (mCurrentPosition != null)
            mCurrentPosition.remove();
        mCurrentPosition = mMap.addMarker(mMarkerOptions);


    }




    private boolean isProviderAvailable() {
        mLocationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        provider = mLocationManager.getBestProvider(criteria, true);
        if (mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;

            return true;
        }

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            return true;
        }

        if (provider != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e(TAG, "onMarkerClick: ");
        Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
        return false;
    }



}
