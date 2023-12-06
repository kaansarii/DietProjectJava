package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.LoginDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ILogin {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/Auth/login")
    Call<Void> loginUser(@Body LoginDto loginDto);
}
