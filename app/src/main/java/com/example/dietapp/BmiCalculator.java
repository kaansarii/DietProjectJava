package com.example.dietapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BmiCalculator extends AppCompatActivity {

    EditText edKg, edCm, edAge;
    TextView textBmi, textCal, textMakro;
    CardView calculateButton;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;

    RadioGroup radioGroupPurpose;
    RadioButton radioLoseWeight,radioMaintainWeight,radioGainWeight;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        edCm = findViewById(R.id.edCm);
        edKg = findViewById(R.id.edKg);
        edAge = findViewById(R.id.edAge);
        calculateButton = findViewById(R.id.calculateButton);
        textBmi = findViewById(R.id.textBmi);
        textCal = findViewById(R.id.textCal);
        textMakro = findViewById(R.id.textMakro);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioGroupPurpose = findViewById(R.id.radioGroupPurpose);
        radioLoseWeight = findViewById(R.id.radioLoseWeight);
        radioMaintainWeight = findViewById(R.id.radioMaintainWeight);
        radioGainWeight = findViewById(R.id.radioGainWeight);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                String kg = edKg.getText().toString();
                String cm = edCm.getText().toString();
                String age = edAge.getText().toString();

                if (kg.length() > 0 && cm.length() > 0 && age.length() > 0) {

                    float weight = Float.parseFloat(kg);
                    float height = Float.parseFloat(cm);
                    float ageYear = Float.parseFloat(age);

                    float bmiIndex = weight / (height * height);

                    if (bmiIndex > 24) {
                        textBmi.setText("Overweight : " + bmiIndex);
                    } else if (bmiIndex > 18) {
                        textBmi.setText("Normal weight : " + bmiIndex);
                    } else {
                        textBmi.setText("Underweight : " + bmiIndex);
                    }

                    //burda hedef seçiyoruz; kilo al,koru,ver
                    int selectedPurposeId = radioGroupPurpose.getCheckedRadioButtonId();
                    double calorieMultiplier = 0;

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

                    //Burda kalori hesaplaması yapıyorz
                    int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                    double bmrMale = ((10 * weight) + (625 * height) - (5 * ageYear) + 5)*calorieMultiplier;
                    double bmrFemale = ((10 * weight) + (625 * height) - (5 * ageYear) - 161)*calorieMultiplier;


                    if (selectedGenderId == radioButtonMale.getId()) {
                        Toast.makeText(getApplicationContext(), "Erkek seçildi", Toast.LENGTH_SHORT).show();
                        textCal.setText("Calories " + bmrMale);
                    } else if (selectedGenderId == radioButtonFemale.getId()) {
                        textCal.setText("Calories " + bmrFemale);
                    }

                } else {
                    textBmi.setText("Please Input All Box");
                }
            }




        });
    }
}


