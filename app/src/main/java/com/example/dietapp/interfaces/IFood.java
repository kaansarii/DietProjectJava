package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.AddFoodDto;
import com.example.dietapp.dtos.GetFoodDto;
import com.example.dietapp.dtos.GetUserInformationDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFood {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/Food/getFoodWithType/{type}")
    Call<List<GetFoodDto>> getFoodWithType(@Path("type") String type);

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/Food")
    Call<Void> addFood(@Body AddFoodDto model);
}
