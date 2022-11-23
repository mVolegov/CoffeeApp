package com.example.coffeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeapp.CartActivity;
import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.model.Order;

public class MenuElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_element);

        TextView menuElementName = findViewById(R.id.menu_element_page_name_label);
        TextView menuElementDescription = findViewById(R.id.menu_element_page_description_label);
        TextView menuElementPrice = findViewById(R.id.menu_element_page_price_label);

        menuElementName.setText(getIntent().getStringExtra("menuElementName"));
        menuElementDescription.setText(getIntent().getStringExtra("menuElementDescription"));
        menuElementPrice.setText(getIntent().getStringExtra("menuElementPrice"));

        Button addToCartButton = findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(this::addToCart);

        View menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(this::openMenu);

        View cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(this::openCart);
    }
    
    private void openMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void addToCart(View view) {
        int menuElementId = getIntent().getIntExtra("menuElementId", 0);

        if (Order.menuElementsId.containsKey(menuElementId)) {
            Integer amount = Order.menuElementsId.get(menuElementId);
            Order.menuElementsId.put(menuElementId, ++amount);
        } else {
            Order.menuElementsId.put(menuElementId, 1);
        }

        Toast.makeText(this, "Добавлено!", Toast.LENGTH_SHORT).show();
    }

    private void openCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
