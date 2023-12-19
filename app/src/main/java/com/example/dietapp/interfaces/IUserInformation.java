package com.example.dietapp.interfaces;

import com.example.dietapp.dtos.GetUserInformationDto;
import com.example.dietapp.dtos.UserInformationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserInformation {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/UserInformation")
    Call<Void> postUserInformations(@Body UserInformationDto userInformationDto);
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/UserInformation/{id}")
    Call<GetUserInformationDto> getUserInformationWithDailyInformation(@Path("id") int id);
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("api/UserInformation/{id}")
    Call<UserInformationDto> getUserInformationAll(@Path("id") int id);
}
