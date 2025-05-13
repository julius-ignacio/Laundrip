package com.example.laundrip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WashBookingFragment extends Fragment {

    private RecyclerView recyclerView;
    private WasherAdapter washerAdapter;
    private List<users> washerList = new ArrayList<>();
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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        washerAdapter = new WasherAdapter(washerList);
        recyclerView.setAdapter(washerAdapter);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        fetchUserAccountType();
    }

    private void fetchUserAccountType() {
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).child("accountType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().getValue() != null) {
                String accountType = task.getResult().getValue(String.class);
                if ("Booker".equalsIgnoreCase(accountType)) {
                    fetchWashers();
                } else {
                    Toast.makeText(requireContext(), "Only Bookers can view Washers.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Failed to fetch account type.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWashers() {
        databaseReference.orderByChild("accountType").equalTo("Washer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                washerList.clear(); // Clear the list before adding new data
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        users washer = childSnapshot.getValue(users.class);
                        if (washer != null) {
                            washerList.add(washer);
                        }
                    }
                    washerAdapter.notifyDataSetChanged(); // Notify adapter of data changes
                } else {
                    Toast.makeText(requireContext(), "No Washers found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Failed to fetch Washers: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}