package com.example.laundrip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private TextView temperatureText, weatherDescriptionText;

    // OpenWeather API Key (Replace with your API key)
    private static final String API_KEY = "API HEREEE";
    // Location for weather data (Replace with desired location)
    private static final String LOCATION = "Guiguinto";
    // OpenWeather API URL
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&units=metric&appid=" + API_KEY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Link UI components
        temperatureText = view.findViewById(R.id.temperature_text);
        weatherDescriptionText = view.findViewById(R.id.weather_description_text);

        // Fetch and display weather data
        fetchWeatherData();
    }

    private void fetchWeatherData() {
        // Create a Volley request queue
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // Create a JSON Object request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse temperature
                            JSONObject main = response.getJSONObject("main");
                            double temperature = main.getDouble("temp");

                            // Parse weather description
                            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                            String description = weather.getString("description");

                            // Update UI with weather data
                            temperatureText.setText("Temperature: " + temperature + "°C");
                            weatherDescriptionText.setText("Weather: " + description);

                        } catch (JSONException e) {
                            Log.e("Weather", "JSON Parsing error: " + e.getMessage());
                            Toast.makeText(requireContext(), "Error parsing weather data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Weather", "Volley error: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching weather data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the queue
        queue.add(request);
    }
}