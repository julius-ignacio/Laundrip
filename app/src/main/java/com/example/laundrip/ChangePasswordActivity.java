package com.example.laundrip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private Button savePasswordButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordEditText = findViewById(R.id.passwordEditText);
        savePasswordButton = findViewById(R.id.savePasswordButton); // Initialize the button
        auth = FirebaseAuth.getInstance();

        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = passwordEditText.getText().toString().trim();
                if (!newPassword.isEmpty() && newPassword.length() >= 6) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        savePasswordButton.setEnabled(false); // Disable button
                        ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this);
                        progressDialog.setMessage("Updating password...");
                        progressDialog.show();

                        // Update password
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(task -> {
                                    progressDialog.dismiss();
                                    savePasswordButton.setEnabled(true); // Re-enable button
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChangePasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                        finish(); // Close the activity
                                    } else {
                                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                                        Toast.makeText(ChangePasswordActivity.this, "Failed to update password: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "User not logged in.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}