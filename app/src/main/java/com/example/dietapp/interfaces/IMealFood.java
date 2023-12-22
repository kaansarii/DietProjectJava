package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.PostMealFoodDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IMealFood {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/Meal/addMealFood")
    Call<Void> postMealFood(@Body PostMealFoodDto mealFoodDto);
}
