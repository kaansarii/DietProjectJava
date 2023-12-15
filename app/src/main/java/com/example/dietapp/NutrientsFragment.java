package com.example.dietapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietapp.dtos.GetUserInformationDto;
import com.example.dietapp.interfaces.IUserInformation;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NutrientsFragment extends Fragment {
    TextView carbonhydrate;
    TextView fat;
    TextView protein;
    TextView calorie;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrients,container,false);
        carbonhydrate = rootView.findViewById(R.id.tVCarbNutrients);
        fat = rootView.findViewById(R.id.tVFatNutrients);
        protein = rootView.findViewById(R.id.tVProteinNutrients);
        calorie = rootView.findViewById(R.id.tVCalNutrients);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        IUserInformation userInformation = RetrofitClient.getRetrofitInstance().create(IUserInformation.class);
        Call<GetUserInformationDto> call = userInformation.getUserInformationWithDailyInformation(8);
        call.enqueue(new Callback<GetUserInformationDto>() {
            @Override
            public void onResponse(Call<GetUserInformationDto> call, Response<GetUserInformationDto> response) {
                if(response.isSuccessful()){
                    GetUserInformationDto model = response.body();
                    carbonhydrate.setText("Merhaba");
                    fat.setText(decimalFormat.format(model.getDailyFatRequirement()));
                    protein.setText(decimalFormat.format(model.getDailyProteinRequirement()));
                    calorie.setText(decimalFormat.format(model.getDailyCalorieRequirement()));
                }else{
                    Toast.makeText(getContext(),"Merhaba",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserInformationDto> call, Throwable t) {

            }
        });
        return inflater.inflate(R.layout.fragment_nutrients, container, false);


    }
}
