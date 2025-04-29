package com.example.laundrip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TypeSelection extends AppCompatActivity {

    Button washerType, bookerType;
    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_type_selection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Link buttons
        washerType = findViewById(R.id.btn_WasherType);
        bookerType = findViewById(R.id.btn_BookerType);

        // Set onClickListeners for buttons
        washerType.setOnClickListener(v -> updateUserType("Washer"));
        bookerType.setOnClickListener(v -> updateUserType("Booker"));
    }

    private void updateUserType(String accountType) {
        String userId = auth.getCurrentUser().getUid();

        // Update the isWasher field in the database
        databaseReference.child(userId).child("accountType").setValue(accountType)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TypeSelection", "Account type saved: " + accountType);
                        Intent intent = new Intent(TypeSelection.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TypeSelection.this, "Failed to update user type.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}