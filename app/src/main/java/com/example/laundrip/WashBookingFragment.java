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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WashBookingFragment extends Fragment {

    private TextView tv;
    private String location = "Guiguinto"; // Default location
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wash_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        tv = view.findViewById(R.id.textt);

        tv.setText("Welcome to this section!\n" +
                "This is where you can book a wash service.\n" +
                "Please select your preferred date and time.");
        // Fetch location from Firebase
        fetchLocationFromFirebase();
    }

    private void fetchLocationFromFirebase() {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).child("address").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().getValue() != null) {
                location = task.getResult().getValue(String.class); // Use the address as the location
            } else {
                Toast.makeText(requireContext(), "No address found, using default location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}