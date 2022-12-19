package com.example.coffeapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeapp.database.Cart;
import com.example.coffeapp.fragment.AccountLoginFragment;
import com.example.coffeapp.fragment.AccountPageFragment;
import com.example.coffeapp.fragment.CartFragment;
import com.example.coffeapp.fragment.MenuFragment;
import com.example.coffeapp.viewmodel.CartViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        MenuFragment menuFragment = MenuFragment.newInstance();
        CartFragment cartFragment = CartFragment.newInstance();
        AccountLoginFragment accountLoginFragment = AccountLoginFragment.newInstance();
        AccountPageFragment accountPageFragment = AccountPageFragment.newInstance();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment, "selected_fragment")
                .commit();

        badge = bottomNavigation.getOrCreateBadge(R.id.nav_cart);
        badge.setBackgroundColor(getResources().getColor(R.color.yellow_default));

        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel
                .getAllCartItems()
                .observe(this, carts -> {
                    int total = carts.stream().mapToInt(Cart::getAmount).sum();

                    badge.setVisible(total != 0);
                    badge.setNumber(total);
                });

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_menu) {
                selectedFragment = menuFragment;
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = cartFragment;
            } else if (itemId == R.id.nav_account) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String username = prefs.getString("username", "");
                if (username.equals("")){
                    selectedFragment = accountLoginFragment;
                } else {
                    selectedFragment = accountPageFragment;
                }
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