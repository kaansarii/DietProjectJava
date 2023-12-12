package com.example.dietapp.dtos;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LoginResponse(int userId) {
        this.userId = userId;
    }

    @SerializedName("UserId")
    public int userId;
}
