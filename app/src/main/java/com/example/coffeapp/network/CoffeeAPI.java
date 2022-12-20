package com.example.coffeapp.network;

import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.network.DTO.OrderDTO;
import com.example.coffeapp.network.DTO.UserDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CoffeeAPI {

    @GET("v1/menu-categories")
    Call<List<MenuCategory>> getAllMenuCategories();

    @GET("v1/menu-items")
    Call<List<MenuElement>> getAllMenuElements();

    @GET("v1/users/token/refresh")
    Call<Map<String, String>> refreshJwtToken(String refreshToken);

    @POST("v1/users")
    Call<UserDTO> registerUser(@Body UserDTO user);

    @POST("v1/orders")
    Call<Integer> sendOrder(@Body OrderDTO order); // HttpStatus - int? или тело пустое, просто код какой то возвращается?
}
