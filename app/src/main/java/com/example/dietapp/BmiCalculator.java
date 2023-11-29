package com.example.dietapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity{

        EditText edKg,edFeet,edIns;
        TextView textBmi;
        CardView calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        edFeet = findViewById(R.id.edFeet);
        edKg = findViewById(R.id.edKg);
        View calculateButton = findViewById(R.id.calculateButton);
        textBmi = findViewById(R.id.textBmi);
        edIns = findViewById(R.id.edIns);




        calculateButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                String kg = edKg.getText().toString();
                String feet = edFeet.getText().toString();
                String inc = edIns.getText().toString();

                if (kg.length()>0&&feet.length()>0&&inc.length()>0){





                    float weight = Float.parseFloat(kg);
                    float efeet = Float.parseFloat(feet);
                    float eInc = Float.parseFloat(inc);

                    float height = (float) (efeet*0.3048+ eInc*0.0254);
                    float bmiIndex = weight/(height*height);

                    if (bmiIndex>24){

                        textBmi.setText("Overweight : "+bmiIndex);

                    }else if (bmiIndex>18){

                        textBmi.setText("Normal weight : "+bmiIndex);


                    }else {

                        textBmi.setText("Underweight : "+bmiIndex);


                    }


                }else {


                    textBmi.setText("Please Input All Box");


                }



            }
        });
        
    }
}


