package com.example.laundrip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAccountType extends AppCompatActivity {

    RadioButton radioWasher, radioBooker;
    RadioGroup radioGroupAccountType;
    private Button saveAccountTypeButton;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_type);

        radioWasher = findViewById(R.id.radioWasher);
        radioBooker = findViewById(R.id.radioBooker);
        radioGroupAccountType = findViewById(R.id.radioGroupAccountType);
        saveAccountTypeButton = findViewById(R.id.saveAccountTypeButton);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        saveAccountTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupAccountType.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(ChangeAccountType.this, "Please select an account type.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String accountType = selectedId == R.id.radioWasher ? "Washer" : "Booker";
                FirebaseUser user = auth.getCurrentUser();

                if (user != null) {
                    ProgressDialog progressDialog = new ProgressDialog(ChangeAccountType.this);
                    progressDialog.setMessage("Updating account type...");
                    progressDialog.show();

                    String userId = user.getUid();
                    databaseReference.child(userId).child("accountType").setValue(accountType)
                            .addOnCompleteListener(task -> {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangeAccountType.this, "Account type updated successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ChangeAccountType.this, "Failed to update account type.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ChangeAccountType.this, "User not logged in.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}