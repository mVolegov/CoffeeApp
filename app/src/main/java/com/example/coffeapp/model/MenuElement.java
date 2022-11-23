package com.example.coffeapp.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MenuElement implements Serializable {

    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private MenuCategory category;

    public MenuElement() {}

    public MenuElement(int id,
                       String name,
                       BigDecimal price,
                       String description,
                       MenuCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
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

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }
}
