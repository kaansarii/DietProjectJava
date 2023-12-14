package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.RegisterDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IRegister {

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/Auth/register")

    Call<Void> registerUser(@Body RegisterDto registerDto);
}
