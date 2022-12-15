package com.example.coffeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.adapter.MenuCategoryAdapter;
//import com.example.coffeapp.adapter.MenuElementAdapter;
import com.example.coffeapp.adapter.MenuElementAdapter;
import com.example.coffeapp.mocks.MockedDataInitializer;
import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static MenuCategoryAdapter menuCategoryAdapter;
    private static MenuElementAdapter menuElementAdapter;

    private static List<MenuCategory> menuCategories;
    private static List<MenuElement> menuElementsFull;
    private static List<MenuElement> menuElementsToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ApiMenuCategories apiMenuCategories = new ApiMenuCategories();

        MockedDataInitializer mockedData = new MockedDataInitializer();

        menuCategories = mockedData.getMockedMenuCategories();
//        menuCategories = apiMenuCategories.getAllMenuCategories();
        setMenuCategoryRecycler(menuCategories);
//        setAllMenuElementsCategoryButton();

        menuElementsFull = mockedData.getMockedMenuElements();
        menuElementsToShow = new ArrayList<MenuElement>() {{
            addAll(menuElementsFull);
        }};
        setMenuElementRecycler(menuElementsToShow);

//        setCart();
    }

    private void setMenuCategoryRecycler(List<MenuCategory> menuCategories) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        menuCategoryAdapter = new MenuCategoryAdapter(this, menuCategories);

        RecyclerView menuCategoryRecycler = findViewById(R.id.menu_category_recycler);
        menuCategoryRecycler.setLayoutManager(layoutManager);
        menuCategoryRecycler.setAdapter(menuCategoryAdapter);
    }

    private void setMenuElementRecycler(List<MenuElement> menuElements) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

//        menuElementAdapter = new MenuElementAdapter(this, menuElements);

        RecyclerView menuElementRecycler = findViewById(R.id.menu_element_recycler);
        menuElementRecycler.setLayoutManager(layoutManager);
        menuElementRecycler.setAdapter(menuElementAdapter);
    }

    /* Здесь */
//    public static void showMenuElementsByCategory(int category) {
//        menuElementsToShow.clear();
//
//        List<MenuElement> filterMenuElements = menuElementsFull
//                .stream()
//                .filter(el -> el.getCategory().getId() == category)
//                .collect(Collectors.toList());
//
//        menuElementsToShow.clear();
//        menuElementsToShow.addAll(filterMenuElements);
//
//        menuElementAdapter.notifyDataSetChanged();
//    }
//
//    private void setAllMenuElementsCategoryButton() {
//        TextView allMenuElementsShow = findViewById(R.id.all_menu_elements_label);
//        allMenuElementsShow.setOnClickListener(view -> {
//            menuElementsToShow.clear();
//            menuElementsToShow.addAll(menuElementsFull);
//
//            menuElementAdapter.notifyDataSetChanged();
//        });
//    }

//    private void setCart() {
//        ImageView cartIcon = findViewById(R.id.cart_icon);
//        cartIcon.setOnClickListener(this::openCart);
//    }

    private void openCart(View view) {
        startActivity(new Intent(this, CartActivity.class));
    }

    public static List<MenuElement> getMenuElementsFull() {
        return menuElementsFull;
    }
}