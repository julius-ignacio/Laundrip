package com.example.laundrip;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.laundrip.databinding.ActivityHomeBinding;
import com.example.laundrip.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());




        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.Guide) {
                replaceFragment(new GuideFragment());
            } else if (item.getItemId() == R.id.Chatbot) {
                replaceFragment(new WashBookingFragment());
            } else if (item.getItemId() == R.id.Account) {
                replaceFragment(new AccountFragment());
            }
            return true;
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager FragmentManager = getSupportFragmentManager();
        FragmentTransaction Fragmenttransaction = FragmentManager.beginTransaction();
        Fragmenttransaction.replace(R.id.frameLayout, fragment);
        Fragmenttransaction.commit();
    }
}