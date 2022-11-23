package com.example.coffeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.coffeapp.MainActivity;
import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView orderList = findViewById(R.id.order_list);

        List<String> menuElementsName = MainActivity.getMenuElementsFull()
                .stream()
                .filter(el -> Order.menuElementsId.containsKey(el.getId()))
                .map(el -> Order.menuElementsId.get(el.getId()) + "x " + el.getName())
                .collect(Collectors.toList());

        if (menuElementsName.isEmpty()) {
            menuElementsName.add("Пусто");
        }

        orderList.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, menuElementsName));
    }
}