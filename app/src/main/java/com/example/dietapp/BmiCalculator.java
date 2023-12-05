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
import java.text.DecimalFormat; //virgülden sonra basamak ayarlamak için dahil edildi

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

                    //değerleri float türüne çevirdik
                    float weight = Float.parseFloat(kg);
                    float height = Float.parseFloat(cm);
                    float ageYear = Float.parseFloat(age);

                    float bmiIndex = weight / (height * height);


                    //virgülden sonra en fazla iki rakam olsun diye yaptım
                    DecimalFormat df2 = new DecimalFormat("#.##");
                    String endBmiIndex=df2.format(bmiIndex);


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
                        Toast.makeText(BmiCalculator.this, "Kalorinizin hesaplanabilmesi için Lütfen bir hedef seçin", Toast.LENGTH_SHORT).show();
                        return;
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
                    double bmrMale = ((10 * weight) + (625 * height) - (5 * ageYear) + 5)*calorieMultiplier;
                    double bmrFemale = ((10 * weight) + (625 * height) - (5 * ageYear) - 161)*calorieMultiplier;


                    if (selectedGenderId == -1) {
                        Toast.makeText(BmiCalculator.this, "Lütfen kcal ve makroların hesaplanabilmesi için bir cinsiyet seçin", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    //Protein Hedefi (gram): 1.6-2.2 gram/kg * kilo    1.9 aldım
                    //Yağ Hedefi (gram): 0.25-0.35 gram/kg * kilo      0.30 aldım
                    //Kalan Kaloriler Karbonhidratlardan gelecek.

                    //1g protein: 4 kalori
                    //1g karbonhidrat: 4 kalori
                    //1g yağ: 9 kalori
                    double protein =weight*1.9;
                    double fat=weight*0.30;
                    double carbohydrateMale=(bmrMale-(protein+fat))/9 ;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım alınması gereken kaloriden diğer iki makronun gramını çıkarıp böldüm böylece karbonhidratında makrosunu elde ettim
                    double carbohydrateFemale=(bmrMale-(protein+fat))/9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım

                    //virgülden sonra en fazla iki rakam olsun diye yaptım
                    DecimalFormat df = new DecimalFormat("#.##");
                    String endProtein = df.format(protein);
                    String endFat = df.format(fat);
                    String endCarbohydrateMale = df.format(carbohydrateMale);
                    String endCarbohydrateFemale =df.format(carbohydrateFemale);
                    String endBmrFemale =df.format(bmrFemale);
                    String endBmrMale=df.format(bmrMale);



                    //cinsiyet durumuna göre alınması gereken kcal değişiyor
                    if (selectedGenderId == radioButtonMale.getId()) {
                        textCal.setText("Calorie's\n " + endBmrMale);
                        textMakro.setText("Carb: "+endCarbohydrateMale+"\nProtein: "+endProtein + "\nFat: "+endFat);
                    }
                    else if (selectedGenderId == radioButtonFemale.getId()) {
                        textCal.setText("Calorie's " + endBmrFemale);
                        textMakro.setText("Carb: "+endCarbohydrateFemale+"\nProtein: "+endProtein + "\nFat: "+endFat);
                    }

                } else {
                    textBmi.setText("Please Input All Box");
                }




            }







        });
    }
}


