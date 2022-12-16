package com.example.coffeapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MenuCategory {

    private int id;

    @SerializedName("title")
    private String title;

    public MenuCategory() {}

    public MenuCategory(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuCategory that = (MenuCategory) o;

        return id == that.id && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
