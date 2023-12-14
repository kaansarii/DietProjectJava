package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.UserInformationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserInformation {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/UserInformation")
    Call<Void> postUserInformations(@Body UserInformationDto userInformationDto);
}
