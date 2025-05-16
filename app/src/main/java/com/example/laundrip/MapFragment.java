package com.example.laundrip;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapFragment extends Fragment {

    private MapView mapView;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);
        mapView.setMultiTouchControls(true);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        if (auth.getCurrentUser() != null) {
            listenForAddressChanges();
        } else {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void listenForAddressChanges() {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String address = snapshot.getValue(String.class);
                    updateMapLocation(address);
                } else {
                    Toast.makeText(getContext(), "No address found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch address: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMapLocation(String address) {
        getGeoPointFromAddress(address, point -> {
            if (point != null) {
                mapView.getController().setZoom(15.0); // Adjusted zoom level
                mapView.getController().setCenter(point);

                // Search for nearby laundry shops
                searchNearbyLaundryShops(point);
            } else {
                Toast.makeText(getContext(), "Failed to get location for address.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchNearbyLaundryShops(GeoPoint userLocation) {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=laundry&" +
                "viewbox=" + (userLocation.getLongitude() - 0.05) + "," + (userLocation.getLatitude() + 0.05) + "," +
                (userLocation.getLongitude() + 0.05) + "," + (userLocation.getLatitude() - 0.05) + "&bounded=1";

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        mapView.getOverlays().clear(); // Clear existing markers
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject location = response.getJSONObject(i);
                            double lat = location.getDouble("lat");
                            double lon = location.getDouble("lon");
                            String name = location.optString("display_name", "Laundry Shop");

                            // Add marker for each laundry shop
                            Marker marker = new Marker(mapView);
                            marker.setPosition(new GeoPoint(lat, lon));
                            marker.setTitle(name);
                            mapView.getOverlays().add(marker);
                        }
                        Toast.makeText(getContext(), "Found " + response.length() + " laundry shops nearby.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error parsing laundry shop data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error fetching laundry shop data", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }

    private void getGeoPointFromAddress(String address, GeoPointCallback callback) {
        String url = "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json&limit=1";
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject location = response.getJSONObject(0);
                            double lat = location.getDouble("lat");
                            double lon = location.getDouble("lon");
                            callback.onGeoPointReceived(new GeoPoint(lat, lon));
                        } else {
                            callback.onGeoPointReceived(null);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error parsing geocoding response", Toast.LENGTH_SHORT).show();
                        callback.onGeoPointReceived(null);
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error fetching geocoding data", Toast.LENGTH_SHORT).show();
                    callback.onGeoPointReceived(null);
                });

        queue.add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    // Callback interface for GeoPoint
    private interface GeoPointCallback {
        void onGeoPointReceived(GeoPoint point);
    }
}