package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.UserInformationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IUserInformation {
    @POST("api/UserInformation")
    Call<Void> postUserInformations(@Body UserInformationDto userInformationDto);
}
