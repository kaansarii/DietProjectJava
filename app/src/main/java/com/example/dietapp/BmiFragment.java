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
        Bundle bundle = getArguments();

            int userId = bundle.getInt("UserId");

            System.out.println(userId);

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

                    if (height % 1 == 0) {
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
                        return;
                    }


                    //Protein Hedefi (gram): 1.6-2.2 gram/kg * kilo    1.9 aldım
                    //Yağ Hedefi (gram): 0.25-0.35 gram/kg * kilo      0.30 aldım
                    //Kalan Kaloriler Karbonhidratlardan gelecek.

                    //1g protein: 4 kalori
                    //1g karbonhidrat: 4 kalori
                    //1g yağ: 9 kalori
                    double proteinMaleLose = weight * 1.95;
                    double proteinMaleGain = weight * 1.85;
                    double proteinMaleMain = weight * 1.9;

                    double proteinFemaleLose = weight * 1.72;
                    double proteinFemaleGain = weight * 1.62;
                    double proteinFemaleMain = weight * 1.7;

                    double fatMaleLose = weight * 0.20;
                    double fatMaleGain = weight * 0.35;
                    double fatMaleMain = weight * 0.25;

                    double fatFemaleLose = weight * 0.18;
                    double fatFemaleGain = weight * 0.30;
                    double fatFemaleMain = weight * 0.23;

                    double carbohydrateMaleLose = (bmrMale - (proteinMaleLose + fatMaleLose)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım alınması gereken kaloriden diğer iki makronun gramını çıkarıp böldüm böylece karbonhidratında makrosunu elde ettim
                    double carbohydrateMaleGain = (bmrMale - (proteinMaleGain + fatMaleGain)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım alınması gereken kaloriden diğer iki makronun gramını çıkarıp böldüm böylece karbonhidratında makrosunu elde ettim
                    double carbohydrateMaleMain = (bmrMale - (proteinMaleMain + fatMaleMain)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım alınması gereken kaloriden diğer iki makronun gramını çıkarıp böldüm böylece karbonhidratında makrosunu elde ettim
                    double carbohydrateFemaleLose = (bmrMale - (proteinFemaleLose + fatFemaleLose)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım
                    double carbohydrateFemaleGain = (bmrMale - (proteinFemaleGain + fatFemaleGain)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım
                    double carbohydrateFemaleMain = (bmrMale - (proteinFemaleMain + fatFemaleMain)) / 9;    //kcal'in kalan kısmını karbonhidrat alacağımız için böyle bir işlem yaptım

                    //virgülden sonra en fazla iki rakam olsun diye yaptım
                    DecimalFormat df = new DecimalFormat("#.##");
                    String endProteinMaleLose = df.format(proteinMaleLose);
                    String endProteinMaleGain = df.format(proteinMaleGain);
                    String endProteinMaleMain = df.format(proteinMaleMain);

                    String endProteinFemaleLose = df.format(proteinFemaleLose);
                    String endProteinFemaleGain = df.format(proteinFemaleGain);
                    String endProteinFemaleMain = df.format(proteinFemaleMain);

                    String endFatMaleLose = df.format(fatMaleLose);
                    String endFatMaleGain = df.format(fatMaleGain);
                    String endFatMaleMain = df.format(fatMaleMain);

                    String endFatFemaleLose = df.format(fatFemaleLose);
                    String endFatFemaleGain = df.format(fatFemaleGain);
                    String endFatFemaleMain = df.format(fatFemaleMain);

                    String endCarbohydrateMaleLose = df.format(carbohydrateMaleLose);
                    String endCarbohydrateMaleGain = df.format(carbohydrateMaleGain);
                    String endCarbohydrateMaleMain = df.format(carbohydrateMaleMain);

                    String endCarbohydrateFemaleLose = df.format(carbohydrateFemaleLose);
                    String endCarbohydrateFemaleGain = df.format(carbohydrateFemaleGain);
                    String endCarbohydrateFemaleMain = df.format(carbohydrateFemaleMain);
                    String endBmrFemale = df.format(bmrFemale);
                    String endBmrMale = df.format(bmrMale);


                    if (selectedPurposeId == radioLoseWeight.getId()) {
                        //Kilo verme durumu
                        //cinsiyet durumuna göre alınması gereken kcal değişiyor
                        if (selectedGenderId == radioButtonMale.getId()) {
                            textCal.setText("Calorie's\n " + endBmrMale);
                            textMakro.setText("Carb: " + endCarbohydrateMaleLose + "\nProtein: " + endProteinMaleLose + "\nFat: " + endFatMaleLose);
                        }
                     else if (selectedGenderId == radioButtonFemale.getId()) {
                        textCal.setText("Calorie's " + endBmrFemale);
                        textMakro.setText("Carb: " + endCarbohydrateFemaleLose + "\nProtein: " + endProteinFemaleLose + "\nFat: " + endFatFemaleLose);
                    }


                    } else if (selectedPurposeId == radioGainWeight.getId()) {
                        // Kilo alma durumu
                        //cinsiyet durumuna göre alınması gereken kcal değişiyor
                        if (selectedGenderId == radioButtonMale.getId()) {
                            textCal.setText("Calorie's\n " + endBmrMale);
                            textMakro.setText("Carb: " + endCarbohydrateMaleGain + "\nProtein: " + endProteinMaleGain + "\nFat: " + endFatMaleGain);
                        } else if (selectedGenderId == radioButtonFemale.getId()) {
                            textCal.setText("Calorie's " + endBmrFemale);
                            textMakro.setText("Carb: " + endCarbohydrateFemaleGain + "\nProtein: " + endProteinFemaleGain + "\nFat: " + endFatFemaleGain);
                        }


                    } else if (selectedPurposeId == radioMaintainWeight.getId()) {
                        // Kilo koruma durumu
                        //cinsiyet durumuna göre alınması gereken kcal değişiyor
                        if (selectedGenderId == radioButtonMale.getId()) {
                            textCal.setText("Calorie's\n " + endBmrMale);
                            textMakro.setText("Carb: " + endCarbohydrateMaleMain + "\nProtein: " + endProteinMaleMain + "\nFat: " + endFatMaleMain);
                        } else if (selectedGenderId == radioButtonFemale.getId()) {
                            textCal.setText("Calorie's " + endBmrFemale);
                            textMakro.setText("Carb: " + endCarbohydrateFemaleMain + "\nProtein: " + endProteinFemaleMain + "\nFat: " + endFatFemaleMain);
                        }
                    }


                } else {
                    textBmi.setText("Please Input All Box");
                }

            }


        });
        return rootView;
            }
}
