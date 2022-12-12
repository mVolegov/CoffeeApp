package com.example.coffeapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.network.CoffeeAPI;
import com.example.coffeapp.network.RetrofitInstance;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuElementsRepository {

    private static MenuElementsRepository instance;

    private MutableLiveData<List<MenuElement>> menuElements;

    public static MenuElementsRepository getInstance() {
        if (instance == null) instance = new MenuElementsRepository();

        return instance;
    }

    private MenuElementsRepository() {
        menuElements = new MutableLiveData<>();
        makeApiCall();
    }

    public MutableLiveData<List<MenuElement>> getMenuElementsObserver() {
        return menuElements;
    }

    public void makeApiCall() {
        CoffeeAPI coffeeAPI = RetrofitInstance.getRetrofitClient().create(CoffeeAPI.class);

        Call<List<MenuElement>> call = coffeeAPI.getAllMenuElements();
        call.enqueue(new Callback<List<MenuElement>>() {
            @Override
            public void onResponse(Call<List<MenuElement>> call,
                                   Response<List<MenuElement>> response) {
                if (!response.isSuccessful()) {
                    menuElements.postValue(Collections.singletonList(new MenuElement(
                            1,
                            "NONE",
                            BigDecimal.ZERO,
                            String.valueOf(response.code()),
                            null
                            )
                        )
                    );
                }

                menuElements.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MenuElement>> call, Throwable t) {
                menuElements.postValue(Collections.singletonList(new MenuElement(
                        1,
                        "Возникла ошибка " + t.getMessage(),
                        BigDecimal.ZERO,
                        t.getMessage(),
                        null
                        )
                    )
                );
            }
        });
    }
}
