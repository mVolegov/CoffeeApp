package com.example.coffeapp.api;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiMenuElements {

    private final CoffeeAPI coffeeAPI;
    private final List<MenuElement> menuElements;

    public ApiMenuElements() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/coffee/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coffeeAPI = retrofit.create(CoffeeAPI.class);

        menuElements = new ArrayList<>();
    }

    public List<MenuElement> getAllMenuElements() {
        Call<List<MenuElement>> menuElementsCall = coffeeAPI.getAllMenuElements();

        menuElementsCall.enqueue(new Callback<List<MenuElement>>() {
            @Override
            public void onResponse(Call<List<MenuElement>> call,
                                   Response<List<MenuElement>> response) {
                if (!response.isSuccessful()) {
                    menuElements.add(new MenuElement(
                            1,
                            "NONE",
                            BigDecimal.ZERO,
                            String.valueOf(response.code()),
                            null
                    ));
                }

                menuElements.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuElement>> call, Throwable t) {
                menuElements.add(new MenuElement(
                        1,
                        "Возникла ошибка" + t.getMessage(),
                        BigDecimal.ZERO,
                        t.getMessage(),
                        null
                ));
            }
        });

        return menuElements;
    }
}
