package com.example.laundrip;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, password, confirmPassword;
    private Button signupButton;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Link UI components
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        signupButton = findViewById(R.id.signup_button);

        // Set OnClickListener for the Sign-Up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userConfirmPassword = confirmPassword.getText().toString().trim();

                // Validate input fields
                if (TextUtils.isEmpty(userName)) {
                    username.setError("Username cannot be empty!");
                    return;
                }
                if (TextUtils.isEmpty(userEmail)) {
                    email.setError("Email cannot be empty!");
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    password.setError("Password cannot be empty!");
                    return;
                }
                if (userPassword.length() < 6) {
                    password.setError("Password must be at least 6 characters long!");
                    return;
                }
                if (!userPassword.equals(userConfirmPassword)) {
                    confirmPassword.setError("Passwords do not match!");
                    return;
                }

                // Register the user with Firebase
                registerUser(userName, userEmail, userPassword);
            }
        });
    }

    private void registerUser(String userName, String userEmail, String userPassword) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Fetch the signed-in user
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        // Save user details to Realtime Database
                        users user = new users();
                        user.setUserId(firebaseUser.getUid());
                        user.setName(userName);
                        user.setAddress("");
                        user.setProfile(""); // You can add a default profile image URL
                        user.setPassword(userPassword); // Avoid storing plain passwords in production!

                        database.getReference().child("users").child(firebaseUser.getUid()).setValue(user)
                                .addOnCompleteListener(databaseTask -> {
                                    if (databaseTask.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show();

                                        // Navigate to the Login or Home screen
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Database Error: " + databaseTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(SignupActivity.this, "Sign-Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}