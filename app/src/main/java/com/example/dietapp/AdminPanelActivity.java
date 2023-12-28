package com.example.dietapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dietapp.dtos.AddFoodDto;
import com.example.dietapp.interfaces.IFood;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText foodNameField, calorieField, proteinField, fatField, carbohydrateField;
    private CheckBox breakfastCheckbox, lunchCheckbox, dinnerCheckbox;
    private Button addButton, outButton;
    private TextView foodListArea;
    private List<String> foodList;
    private String meal = " ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        foodNameField = findViewById(R.id.foodNameField);
        calorieField = findViewById(R.id.calorieField);
        proteinField = findViewById(R.id.proteinField);
        fatField = findViewById(R.id.fatField);
        carbohydrateField = findViewById(R.id.carbohydrateField);
        breakfastCheckbox = findViewById(R.id.breakfastCheckbox);
        lunchCheckbox = findViewById(R.id.lunchCheckbox);
        dinnerCheckbox = findViewById(R.id.dinnerCheckbox);
        addButton = findViewById(R.id.addButton);
        outButton = findViewById(R.id.outButton);

        foodList = new ArrayList<>(); //eklenen besinler bu list'de yer alacak

        //Besin ekleme butonu
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });

        //cıkış yapma metodu intnet tanımlı
        outButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(AdminPanelActivity.this, MainActivity.class);
                AdminPanelActivity.this.startActivity(intent);
                AdminPanelActivity.this.finish();
            }
        });
    }

    //besin ekleme kısmı
    private void addFood() {
        // Kullanıcının girdiği bilgileri alınıyor
        String foodName = foodNameField.getText().toString();
        String calorie = calorieField.getText().toString();
        String protein = proteinField.getText().toString();
        String fat = fatField.getText().toString();
        String carbohydrate = carbohydrateField.getText().toString();
        double doubleCalorie = Double.parseDouble(calorie);
        double doubleProtein = Double.parseDouble(protein);
        double doubleFat = Double.parseDouble(fat);
        double doubleCarbohydrate = Double.parseDouble(carbohydrate);

        // hangi öğün  kontrol et
        boolean isBreakfast = breakfastCheckbox.isChecked();
        boolean isLunch = lunchCheckbox.isChecked();
        boolean isDinner = dinnerCheckbox.isChecked();
        if(isBreakfast){
            meal = "Kahvaltı";
        } else if (isLunch) {
            meal = "Öğle Yemeği";
        } else if (isDinner) {
            meal = "Akşam Yemeği";
        }
        // Gerekli bilgileri kontrol et
        if (foodName.isEmpty() || calorie.isEmpty() || protein.isEmpty() || fat.isEmpty() || carbohydrate.isEmpty()) {
            // Eğer gerekli bilgiler eksikse, kullanıcıyı uyar
            Toast.makeText(this, "Lütfen tüm bilgileri doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Yiyecek bilgilerini yukardaki tanımlanmş  listeye ekleniyr
        String foodInfo = "Yiyecek Adı: " + foodName +
                "\nKalori: " + calorie +
                "\nProtein: " + protein +
                "\nYağ: " + fat +
                "\nKarbonhidrat: " + carbohydrate +
                "\nTürler: " + (isBreakfast ? "Kahvaltı " : "") +
                (isLunch ? "Öğle Yemeği " : "") +
                (isDinner ? "Akşam Yemeği" : "");

        // Alert Dialog oluşturuldu kullanıcya soracak ekleyeyim mi iptal mi edeyim diye
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Besin Ekleniyor"); //başlığı
        builder.setMessage("Aşağıdaki besine ait bilgileri eklemek istiyor musunuz?\n\n" + foodInfo);

        //ekledderse
        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı "Ekle" dediğinde
                foodList.add(foodInfo);
                IFood iFood = RetrofitClient.getRetrofitInstance().create(IFood.class);
                AddFoodDto addFoodDto = new AddFoodDto(foodName,meal,doubleCalorie,doubleFat,doubleCarbohydrate,doubleProtein);
                Call<Void> call = iFood.addFood(addFoodDto);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(AdminPanelActivity.this, "Veri Başarıyla Eklendi", Toast.LENGTH_LONG).show();
                        }else{
                            // Başarısız cevap alındı, hata durumunu göster
                            String errorMessage = "Error: " + response.code() + " " + response.message();
                            Toast.makeText(AdminPanelActivity.this, "Veri Eklenemedi", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                 //temizle
                clearFields();

                // Kullanıcıya başarı mesajını göster
                Toast.makeText(AdminPanelActivity.this, "Besin başarıyla eklendi.", Toast.LENGTH_SHORT).show();
            }
        });
        //iptal derse
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı "İptal" dediğinde
                dialog.dismiss();
            }
        });

        // Alert Dialog'u göster
        builder.show();
    }



    // ekleme yapıldıktam sonra kutuları temizleyip tekrar girinesi hale getiriyor
    private void clearFields() {
        foodNameField.setText("");
        calorieField.setText("");
        proteinField.setText("");
        fatField.setText("");
        carbohydrateField.setText("");
        breakfastCheckbox.setChecked(false);
        lunchCheckbox.setChecked(false);
        dinnerCheckbox.setChecked(false);
    }





}


