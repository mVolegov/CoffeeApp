package com.example.coffeapp.network.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

public class OrderDTO {
    @SerializedName("username")
    private String username;
    @SerializedName("cart")
    private Map<Long, Integer> cart;

    public OrderDTO() {}

    public OrderDTO(String username, Map<Long, Integer> cart) {
        this.username = username;
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Long, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Long, Integer> cart) {
        this.cart = cart;
    }
}
