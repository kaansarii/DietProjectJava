package com.example.dietapp.dtos;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LoginResponse(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    @SerializedName("UserId")
    private int userId;
    @SerializedName("Role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
