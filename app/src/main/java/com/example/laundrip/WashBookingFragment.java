package com.example.laundrip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WashBookingFragment extends Fragment {

    private Button chatSpecialUserButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wash_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the button
        chatSpecialUserButton = view.findViewById(R.id.chatSpecialUserButton);

        // Set click listener to open ChatActivity
        chatSpecialUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("receiverId", "6tS6ZlqFtjaHGoxmxtbdJVaJo5g2"); // Special user's ID
            intent.putExtra("receiverName", "Special User");
            startActivity(intent);
        });
    }
}