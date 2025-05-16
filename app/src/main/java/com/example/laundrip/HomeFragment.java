package com.example.laundrip;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TextView temperatureText, weatherDescriptionText, day1Text, day2Text, day3Text, laundryTipCategory, laundryTipText;
    private String location = "Guiguinto"; // Default location
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    private Handler handler = new Handler();
    private JSONArray laundryTipsArray;
    private Random random = new Random();

    // OpenWeather API Key
    private static final String API_KEY = "0698d9d0b4ff8d86273f42965aeb045d";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Link UI components
        temperatureText = view.findViewById(R.id.temperature_text);
        weatherDescriptionText = view.findViewById(R.id.weather_description_text);
        day1Text = view.findViewById(R.id.day1_text);
        day2Text = view.findViewById(R.id.day2_text);
        day3Text = view.findViewById(R.id.day3_text);
        laundryTipCategory = view.findViewById(R.id.laundry_tip_category);
        laundryTipText = view.findViewById(R.id.laundry_tip_text);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Fetch location from Firebase
        fetchLocationFromFirebase();

        // Load laundry tips from JSON
        loadLaundryTips();

        // Start refreshing tips
        startTipRefresh();
    }

    private void fetchLocationFromFirebase() {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).child("address").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().getValue() != null) {
                location = task.getResult().getValue(String.class); // Use the address as the location
            } else {
                if (isAdded()) {
                    Toast.makeText(getContext(), "No address found, using default location.", Toast.LENGTH_SHORT).show();
                }
            }
            if (isAdded()) { // Ensure the fragment is attached before proceeding
                fetchWeatherData();
            }
        });
    }

    private void fetchWeatherData() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&units=metric&appid=" + API_KEY;

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse current weather
                            JSONObject currentWeather = response.getJSONArray("list").getJSONObject(0);
                            double temperature = currentWeather.getJSONObject("main").getDouble("temp");
                            String description = currentWeather.getJSONArray("weather").getJSONObject(0).getString("description");

                            temperatureText.setText("Temperature: " + temperature + "°C");
                            weatherDescriptionText.setText("Weather: " + description);

                            // Parse next 3 days
                            JSONArray list = response.getJSONArray("list");
                            day1Text.setText(parseDayWeather(list.getJSONObject(8)));  // 24 hours later
                            day2Text.setText(parseDayWeather(list.getJSONObject(16))); // 48 hours later
                            day3Text.setText(parseDayWeather(list.getJSONObject(24))); // 72 hours later

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

        queue.add(request);
    }


    private String parseDayWeather(JSONObject dayWeather) throws JSONException {
        String date = dayWeather.getString("dt_txt").split(" ")[0];
        double temp = dayWeather.getJSONObject("main").getDouble("temp");
        String description = dayWeather.getJSONArray("weather").getJSONObject(0).getString("description");

        // Convert date to day of the week
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayOfWeek;
        try {
            Date parsedDate = inputFormat.parse(date);
            dayOfWeek = outputFormat.format(parsedDate);
        } catch (ParseException e) {
            dayOfWeek = "Unknown Day"; // Fallback in case of error
        }

        return dayOfWeek + ": " + temp + "°C, " + description;
    }

    private void loadLaundryTips() {
        try {
            InputStream inputStream = requireContext().getAssets().open("Laundry_Tips.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            laundryTipsArray = jsonObject.getJSONArray("laundry_tips");
        } catch (IOException | JSONException e) {
            Toast.makeText(getContext(), "Error loading laundry tips", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTipRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (laundryTipsArray != null) {
                    try {
                        // Pick a random category and tip
                        int randomIndex = random.nextInt(laundryTipsArray.length());
                        JSONObject categoryObject = laundryTipsArray.getJSONObject(randomIndex);
                        String category = categoryObject.getString("category");
                        JSONArray tips = categoryObject.getJSONArray("tips");
                        String tip = tips.getString(random.nextInt(tips.length()));

                        // Update UI
                        laundryTipCategory.setText(category);
                        laundryTipText.setText("Tip: " + tip);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error parsing laundry tips", Toast.LENGTH_SHORT).show();
                    }
                }

                // Refresh every 10 seconds
                handler.postDelayed(this, 10000);
            }
        }, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null); // Stop refreshing tips
    }
}