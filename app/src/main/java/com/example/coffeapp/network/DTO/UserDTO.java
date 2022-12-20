package com.example.coffeapp.network.DTO;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public UserDTO() {}

    public UserDTO(String username,
                String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
