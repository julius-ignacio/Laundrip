package com.example.laundrip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAddressActivity extends AppCompatActivity {

    private EditText addressEditText;
    private Button saveAddressButton;
    private DatabaseReference databaseReference;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);

        addressEditText = findViewById(R.id.addressEditText);
        saveAddressButton = findViewById(R.id.saveAddressButton);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newAddress = addressEditText.getText().toString().trim();
                if (!newAddress.isEmpty()) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference.child(userId).child("address").setValue(newAddress)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangeAddressActivity.this, "Address updated successfully!", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity
                                } else {
                                    Toast.makeText(ChangeAddressActivity.this, "Failed to update address.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ChangeAddressActivity.this, "Please enter a valid address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}