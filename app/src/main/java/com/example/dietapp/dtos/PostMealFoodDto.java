package com.example.dietapp.dtos;

public class PostMealFoodDto {
    private int appUserId,foodId;

    public PostMealFoodDto(int appUserId, int foodId) {
        this.appUserId = appUserId;
        this.foodId = foodId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
