package com.example.coffeapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MenuElement implements Serializable {

    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private List<MenuCategory> categories;

    public MenuElement() {}

    public MenuElement(int id, String name, BigDecimal price, String description, List<MenuCategory> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MenuCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MenuCategory> categories) {
        this.categories = categories;
    }
}
