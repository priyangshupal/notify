package com.example.geofence;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.geofence.Fragments.AccountFragment;
import com.example.geofence.Fragments.ItemsFragment;
import com.example.geofence.Fragments.SearchFragment;
import com.example.geofence.Fragments.ServicesFragment;
import com.example.geofence.Fragments.ShopFragment;
import com.example.geofence.Notifications.FcmNotifs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent backgroundService = new Intent(getApplicationContext(), FcmNotifs.class);
        startService(backgroundService);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bn_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AccountFragment())
                .commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bn_shop:
                            selectedFragment = new ShopFragment();
                            break;
                        case R.id.bn_items:
                            selectedFragment = new ItemsFragment();
                            break;
                        case R.id.bn_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.bn_services:
                            selectedFragment = new ServicesFragment();
                            break;
                        case R.id.bn_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    if(selectedFragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }

                    return true;
                }
            };
}