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

import java.text.DecimalFormat;

public class BmiFragment extends Fragment {

    EditText edKg, edCm, edAge;
    TextView textBmi, textCal, textMakro;
    CardView calculateButton;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    RadioGroup radioGroupPurpose;
    RadioButton radioLoseWeight, radioMaintainWeight, radioGainWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);

        edCm = rootView.findViewById(R.id.edCm);
        edKg = rootView.findViewById(R.id.edKg);
        edAge = rootView.findViewById(R.id.edAge);
        calculateButton = rootView.findViewById(R.id.calculateButton);
        textBmi = rootView.findViewById(R.id.textBmi);
        textCal = rootView.findViewById(R.id.textCal);
        textMakro = rootView.findViewById(R.id.textMakro);
        radioGroupGender = rootView.findViewById(R.id.radioGroupGender);
        radioButtonMale = rootView.findViewById(R.id.radioButtonMale);
        radioButtonFemale = rootView.findViewById(R.id.radioButtonFemale);
        radioGroupPurpose = rootView.findViewById(R.id.radioGroupPurpose);
        radioLoseWeight = rootView.findViewById(R.id.radioLoseWeight);
        radioMaintainWeight = rootView.findViewById(R.id.radioMaintainWeight);
        radioGainWeight = rootView.findViewById(R.id.radioGainWeight);


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                String kg = edKg.getText().toString();
                String cm = edCm.getText().toString();
                String age = edAge.getText().toString();


                if (kg.length() > 0 && cm.length() > 0 && age.length() > 0) {

                    //değerleri float türüne çevirdik
                    float weight = Float.parseFloat(kg);
                    float height = Float.parseFloat(cm);
                    float ageYear = Float.parseFloat(age);

                    float bmiIndex = weight / (height * height);


                    //virgülden sonra en fazla iki rakam olsun diye yaptım
                    DecimalFormat df2 = new DecimalFormat("#.##");
                    String endBmiIndex = df2.format(bmiIndex);


                    //bmi oranına göre sonuç verecek
                    if (bmiIndex > 24) {
                        textBmi.setText("Overweight : " + endBmiIndex);
                    } else if (bmiIndex > 18) {
                        textBmi.setText("Normal weight : " + endBmiIndex);
                    } else {
                        textBmi.setText("Underweight : " + endBmiIndex);
                    }

                    //burda hedef seçiyoruz; kilo al,koru,ver
                    int selectedPurposeId = radioGroupPurpose.getCheckedRadioButtonId();
                    double calorieMultiplier = 0;
                    if (selectedPurposeId == -1) {
                        Toast.makeText(getContext(), "Kalorinizin hesaplanabilmesi için Lütfen bir hedef seçin", Toast.LENGTH_SHORT).show();
                    }


                    if (selectedPurposeId == radioLoseWeight.getId()) {
                        // Kilo verme durumu
                        calorieMultiplier = 0.72;
                    } else if (selectedPurposeId == radioGainWeight.getId()) {
                        // Kilo alma durumu
                        calorieMultiplier = 1.28;
                    } else if (selectedPurposeId == radioMaintainWeight.getId()) {
                        // Kilo koruma durumu
                        calorieMultiplier = 1.0;
                    }


                    //Burda kalori hesaplaması yapıyorz, formülü internetten aldım
                    int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                    double bmrMale = ((10 * weight) + (625 * height) - (5 * ageYear) + 5) * calorieMultiplier;
                    double bmrFemale = ((10 * weight) + (625 * height) - (5 * ageYear) - 161) * calorieMultiplier;


                    if (selectedGenderId == -1) {
                        Toast.makeText(getContext(), "Lütfen kcal ve makroların hesaplanabilmesi için bir cinsiyet seçin", Toast.LENGTH_SHORT).show();
                    }



                    //Protein Hedefi (gram): 1.6-2.2 gram/kg * kilo    1.9 aldım
                    //Yağ Hedefi (gram): 0.25-0.35 gram/kg * kilo      0.30 aldım
                    //Kalan Kaloriler Karbonhidratlardan gelecek.

                    //1g protein: 4 kalori
                    //1g karbonhidrat: 4 kalori
                    //1g yağ: 9 kalori
                    double protein = weight * 1.9;
                    double fat = weight * 0.30;
                    double carbohydrateMale = (bmrMale - (protein + fat)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım alınması gereken kaloriden diğer iki makronun gramını çıkarıp böldüm böylece karbonhidratında makrosunu elde ettim
                    double carbohydrateFemale = (bmrMale - (protein + fat)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım

                    //virgülden sonra en fazla iki rakam olsun diye yaptım
                    DecimalFormat df = new DecimalFormat("#.##");
                    String endProtein = df.format(protein);
                    String endFat = df.format(fat);
                    String endCarbohydrateMale = df.format(carbohydrateMale);
                    String endCarbohydrateFemale = df.format(carbohydrateFemale);
                    String endBmrFemale = df.format(bmrFemale);
                    String endBmrMale = df.format(bmrMale);


                    //cinsiyet durumuna göre alınması gereken kcal değişiyor
                    if (selectedGenderId == radioButtonMale.getId()) {
                        textCal.setText("Calorie's\n " + endBmrMale);
                        textMakro.setText("Carb: " + endCarbohydrateMale + "\nProtein: " + endProtein + "\nFat: " + endFat);
                    } else if (selectedGenderId == radioButtonFemale.getId()) {
                        textCal.setText("Calorie's " + endBmrFemale);
                        textMakro.setText("Carb: " + endCarbohydrateFemale + "\nProtein: " + endProtein + "\nFat: " + endFat);
                    }

                } else {
                    textBmi.setText("Please Input All Box");
                }

            }


        });
        return rootView;
            }
}
