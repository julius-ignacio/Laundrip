package com.example.laundrip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeAccountType extends AppCompatActivity {

    RadioButton radioWasher, radioBooker;
    RadioGroup radioGroupAccountType;
    private Button saveAccountTypeButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        radioWasher = findViewById(R.id.radioWasher);
        radioBooker = findViewById(R.id.radioBooker);
        saveAccountTypeButton = findViewById(R.id.saveAccountTypeButton); // Initialize the button
        auth = FirebaseAuth.getInstance();

        saveAccountTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newType = passwordEditText.getText().toString().trim();
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        savePasswordButton.setEnabled(false); // Disable button
                        ProgressDialog progressDialog = new ProgressDialog(ChangeAccountType.this);
                        progressDialog.setMessage("Updating password...");
                        progressDialog.show();

                        // Update password
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(task -> {
                                    progressDialog.dismiss();
                                    savePasswordButton.setEnabled(true); // Re-enable button
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangeAccountType.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                        finish(); // Close the activity
                                    } else {
                                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                                        Toast.makeText(ChangeAccountType.this, "Failed to update password: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ChangeAccountType.this, "User not logged in.", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
}