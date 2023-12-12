package com.example.dietapp.dtos;

public class UserIdDto {
    public UserIdDto(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public  int userId;
}
