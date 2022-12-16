package com.example.coffeapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.coffeapp.fragment.AccountFragment;
import com.example.coffeapp.fragment.CartFragment;
import com.example.coffeapp.fragment.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        MenuFragment menuFragment = MenuFragment.newInstance();
        CartFragment cartFragment = CartFragment.newInstance();
        AccountFragment accountFragment = AccountFragment.newInstance();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment, "selected_fragment")
                .commit();

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_menu) {
                selectedFragment = menuFragment;
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = cartFragment;
            } else if (itemId == R.id.nav_account) {
                selectedFragment = accountFragment;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment, "selected_fragment")
                    .commit();

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}