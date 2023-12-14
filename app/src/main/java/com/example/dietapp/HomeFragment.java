package com.example.dietapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.dietapp.dtos.SharedId;
import com.example.dietapp.dtos.UserInformationDto;
import com.example.dietapp.interfaces.IUserInformation;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    EditText edKg, edCm, edAge, edKgDest;
    TextView textCal, textProtein, textCarbohydrate, textFat;
    CardView calculateButton;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    SharedId sharedId = SharedId.getInstance();
    int appUserId = sharedId.getSharedData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        edCm = rootView.findViewById(R.id.edCm);
        edKg = rootView.findViewById(R.id.edKg);
        edAge = rootView.findViewById(R.id.edAge);
        edKgDest = rootView.findViewById(R.id.edKgDest);
        calculateButton = rootView.findViewById(R.id.calculateButton);
        textCal = rootView.findViewById(R.id.textCal);
        textProtein = rootView.findViewById(R.id.textProtein);
        textCarbohydrate = rootView.findViewById(R.id.textCarbohydrate);
        textFat = rootView.findViewById(R.id.textFat);
        radioGroupGender = rootView.findViewById(R.id.radioGroupGender);
        radioButtonMale = rootView.findViewById(R.id.radioButtonMale);
        radioButtonFemale = rootView.findViewById(R.id.radioButtonFemale);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hesapla(); //metodu çağırdık
            }
        });

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void hesapla() {

        String kgStr = edKg.getText().toString();
        String cmStr = edCm.getText().toString();
        String ageStr = edAge.getText().toString();
        String kgDestStr = edKgDest.getText().toString();

        //boş alan olursa mesaj verecek
        if (kgStr.isEmpty() || cmStr.isEmpty() || ageStr.isEmpty() || kgDestStr.isEmpty()) {
            Toast.makeText(getContext(), "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(kgStr);
            double height = Double.parseDouble(cmStr);
            int ageYear = Integer.parseInt(ageStr);
            double destWeight = Double.parseDouble(kgDestStr);
            String age = String.valueOf(ageYear);

            // Kullanıcıya boyunun ondalıklı bir sayı girmesini söyleyecek
            if (height%1==0){
                Toast.makeText(getContext(), "Lütfen boyunuzu ondalık bir sayı olarak girin (örn. 1.90).", Toast.LENGTH_SHORT).show();
                return;
            }
            //yaş sınırı koydum
            if (ageYear < 0 || ageYear > 100) {
                Toast.makeText(getContext(), "Lütfen geçerli bir yaş girin.", Toast.LENGTH_SHORT).show();
                return;
            }
            //kilo sınır koydum
            if (weight <= 45) {
                Toast.makeText(getContext(), "Lütfen kilonuzu doğru girin.", Toast.LENGTH_SHORT).show();
                return;
            }


            double bmr;
            if (radioButtonMale.isChecked()) {
                bmr = ((10 * weight) + (625 * height) - (5 * ageYear) + 5);
            }
            else {
                bmr = ((10 * weight) + (625 * height) - (5 * ageYear) - 161);
            }

            //mevcut kilo ile hedefkilo farkı
            double fark = weight - destWeight;

            //aşağıdakiler hedefkilo ile şuanki kilo arasındaki farka göre kcal ve makrolar değişiyor ve cinsiyete göre
            if (fark<=10&&fark>=0){
                if (radioButtonMale.isChecked()){
                    double calorieMultiplier = 1.25;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.25;
                    double carbohydrateRatio = 3.35;
                    double fatRatio = 1.15;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId); //api'a veri göndermemizi sağlayacak method
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.2;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.2;
                    double carbohydrateRatio = 3.3;
                    double fatRatio = 1.1;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }



            }
            if (fark<=20 && fark>=11){
                if (radioButtonMale.isChecked()){
                    double calorieMultiplier = 1.15;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.35;
                    double carbohydrateRatio = 3.25;
                    double fatRatio = 1.05;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.1;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.3;
                    double carbohydrateRatio = 3.2;
                    double fatRatio = 1.0;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }



            }
            if (fark<=30 && fark>=21){
                if (radioButtonMale.isChecked()){
                    double calorieMultiplier = 1.05;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.45;
                    double carbohydrateRatio = 3.15;
                    double fatRatio = 0.95;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.0;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.4;
                    double carbohydrateRatio = 3.1;
                    double fatRatio = 0.9;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
            }
            if (fark<=40 && fark>=31) {
                if (radioButtonMale.isChecked()) {
                    double calorieMultiplier = 1.05;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.65;
                    double carbohydrateRatio = 2.85;
                    double fatRatio = 0.85;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.0;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.6;
                    double carbohydrateRatio = 2.8;
                    double fatRatio = 0.8;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
            }

            if (fark<=60 &&fark>=41) {
                if (radioButtonMale.isChecked()) {
                    double calorieMultiplier = 1.05;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.75;
                    double carbohydrateRatio = 2.75;
                    double fatRatio = 0.75;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }

                if (radioButtonFemale.isChecked()) {
                    double calorieMultiplier = 1.0;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.7;
                    double carbohydrateRatio = 2.7;
                    double fatRatio = 0.7;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                 }
            }
            if (fark>=61)
            {
                if (radioButtonMale.isChecked()){
                    double calorieMultiplier = 1.05;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.85;
                    double carbohydrateRatio = 2.35;
                    double fatRatio = 0.45;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }

                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.0;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.8;
                    double carbohydrateRatio = 2.3;
                    double fatRatio = 0.4;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }

            }
            if (fark>=-10 && fark <=0) {
                if (radioButtonMale.isChecked()) {
                    double calorieMultiplier = 1.4;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.4;
                    double carbohydrateRatio = 3.9;
                    double fatRatio = 1.4;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.45;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.35;
                    double carbohydrateRatio = 3.85;
                    double fatRatio = 1.3;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
            }


            if (fark>=-25 && fark <=-11) {
                if (radioButtonMale.isChecked()) {
                    double calorieMultiplier = 1.45;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.55;
                    double carbohydrateRatio = 4.05;
                    double fatRatio = 1.65;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.45;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.5;
                    double carbohydrateRatio = 4.0;
                    double fatRatio = 1.6;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
            }


            if (fark>=-40 && fark <=-25){
                if (radioButtonMale.isChecked()){
                    double calorieMultiplier = 1.45;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.75;
                    double carbohydrateRatio = 4.35;
                    double fatRatio = 1.85;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    //virgülden sonra iki basamak olsun diye yaptım
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Erkek",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
                if (radioButtonFemale.isChecked()){
                    double calorieMultiplier = 1.4;
                    double calculatedCalories = bmr * calorieMultiplier;

                    double proteinRatio = 2.7;
                    double carbohydrateRatio = 4.3;
                    double fatRatio = 1.8;

                    double calculatedProtein = destWeight * proteinRatio;
                    double calculatedCarbohydrate = destWeight * carbohydrateRatio;
                    double calculatedFat = destWeight * fatRatio;

                    //virgülden sonra iki basamak olsun diye yaptım
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    requestUserInformations(height,weight,destWeight,calculatedCalories,calculatedProtein,calculatedCarbohydrate,calculatedFat,"Kadın",age,appUserId);
                    textCal.setText("Günlük Kalori: " + decimalFormat.format(calculatedCalories) + " kcal");
                    textProtein.setText("Protein: " + decimalFormat.format(calculatedProtein) + " g");
                    textCarbohydrate.setText("Karbonhidrat: " + decimalFormat.format(calculatedCarbohydrate) + " g");
                    textFat.setText("Yağ: " + decimalFormat.format(calculatedFat) + " g");
                }
            }

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Geçersiz giriş. Lütfen sayısal değerler girin.", Toast.LENGTH_SHORT).show();
        }
    }
    private void requestUserInformations(double length, double weight, double targetWeight, double dailyCalorieRequirement, double dailyProteinRequirement, double dailyCarbRequirement, double dailyFatRequirement, String gender, String age, int appUserId){
        UserInformationDto userInformationDto = new UserInformationDto(length,weight,targetWeight,dailyCalorieRequirement,dailyProteinRequirement,dailyCarbRequirement,dailyFatRequirement,gender,age,appUserId);
        IUserInformation iUserInformation = RetrofitClient.getRetrofitInstance().create(IUserInformation.class);
        Call<Void> call = iUserInformation.postUserInformations(userInformationDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
              if(response.isSuccessful()){
                  Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();
              }else{
                  String errorMessage = "Error: " + response.code() + " " + response.message();
                  Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
              }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}

       //TANIM: catch, Java programlama dilinde bir hata yakalama (exception handling) mekanizmasıdır. Bir kod bloğunda hata meydana geldiğinde, bu hatayı ele almak ve uygun bir tepki vermek için kullanılır.
      // try bloğu içindeki kodlar çalıştırılır ve eğer bu blok içinde bir hata olursa, program catch bloğuna atlar. catch bloğu, belirli bir türdeki hatayı ele alır ve bu hataya karşı bir işlem yapar. Hata türü, catch bloğu içinde belirtilir.


