package com.example.dietapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietapp.dtos.GetFoodDto;
import com.example.dietapp.dtos.GetUserInformationDto;
import com.example.dietapp.dtos.PostMealFoodDto;
import com.example.dietapp.interfaces.IFood;
import com.example.dietapp.interfaces.IMealFood;
import com.example.dietapp.interfaces.IUserInformation;
import com.example.dietapp.dtos.SharedId;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NutrientsFragment extends Fragment {
    TextView carbonhydrate;
    TextView fat;
    TextView protein;
    TextView calorie;
    TextView dayFat, dayProtein, dayCalorie, dayCarbonhydrate;
    TextView tVTotalBrekakfastCalorie, tvTotalLuncCalorie, tvTotalDinnerCalorie;
    ListView listBreakfast, listLunch, listDinner;
    Button listBreakfastItems, listLunchItems, listDinnerItems;
    SharedId sharedId = SharedId.getInstance();
    int appUserId = sharedId.getSharedData();
    double todayFat, todayProtein, todayCalorie, todayCarbonhydrate = 0;
    double totalBrekakfastCalorie, totalLuncCalorie, totalDinnerCalorie = 0;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    List<GetFoodDto> data = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrients,container,false);
        carbonhydrate = rootView.findViewById(R.id.dailytVCarbNutrients);
        fat = rootView.findViewById(R.id.dailytVFatNutrients);
        protein = rootView.findViewById(R.id.dailytVProteinNutrients);
        calorie = rootView.findViewById(R.id.dailytVCalNutrients);
        dayCarbonhydrate = rootView.findViewById(R.id.todaytVCarbNutrients);
        dayCalorie  = rootView.findViewById(R.id.todaytVCalNutrients);
        dayFat = rootView.findViewById(R.id.todaytVFatNutrients);
        dayProtein = rootView.findViewById(R.id.todaytVProteinNutrients);
        listBreakfast = (ListView) rootView.findViewById(R.id.listBreakfast);
        listBreakfastItems = (Button) rootView.findViewById(R.id.breakfastButton);
        listLunchItems = (Button) rootView.findViewById(R.id.lunchButton);
        listLunch = (ListView) rootView.findViewById(R.id.listLunch);
        listDinnerItems = (Button) rootView.findViewById(R.id.dinnerButton);
        listDinner = (ListView) rootView.findViewById(R.id.listDinner);
        tVTotalBrekakfastCalorie = rootView.findViewById(R.id.totalBreakfast);
        tvTotalLuncCalorie = rootView.findViewById(R.id.totalLunch);
        tvTotalDinnerCalorie = rootView.findViewById(R.id.totalDinner);

        IUserInformation userInformation = RetrofitClient.getRetrofitInstance().create(IUserInformation.class);
        Call<GetUserInformationDto> call = userInformation.getUserInformationWithDailyInformation(appUserId);
        call.enqueue(new Callback<GetUserInformationDto>() {
            @Override
            public void onResponse(Call<GetUserInformationDto> call, Response<GetUserInformationDto> response) {
                if(response.isSuccessful()){
                    GetUserInformationDto model = response.body();
                    carbonhydrate.setText(decimalFormat.format(model.getDailyCarbonhydrateRequirement()) + " g");
                    fat.setText(decimalFormat.format(model.getDailyFatRequirement()) + " g");
                    protein.setText(decimalFormat.format(model.getDailyProteinRequirement()) + " g");
                    calorie.setText(decimalFormat.format(model.getDailyCalorieRequirement()) + " kcal");
                    dayCarbonhydrate.setText(decimalFormat.format(todayCarbonhydrate) + " g");
                    dayFat.setText(decimalFormat.format(todayFat) + " g");
                    dayProtein.setText(decimalFormat.format(todayProtein) + " g");
                    dayCalorie.setText(decimalFormat.format(todayCalorie) + " kcal");
                }else{
                    Toast.makeText(getContext(),"İstek İşlenirken Bir Hata Meydana Geldi",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserInformationDto> call, Throwable t) {

            }
        });
        listBreakfastItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data =  getFoodWithType("Kahvaltı");
                List<GetFoodDto> foods = new ArrayList<>();
                List<Integer> ids = new ArrayList<>();
                List<String> foodNames = new ArrayList<>();
                List<Double> calories = new ArrayList<>();
                List<Double> fats = new ArrayList<>();
                List<Double> proteins = new ArrayList<>();
                List<Double> carbohydrates = new ArrayList<>();
                for(GetFoodDto foodDto : data){
                    foods.add(foodDto); //Liste şeklinde gelen data'lar foods isimli değişkene atanıyor
                    ids.add(foodDto.getId());
                    foodNames.add((foodDto.getName()));
                    calories.add(foodDto.getCalorie());
                    fats.add(foodDto.getFat());
                    proteins.add(foodDto.getProtein());
                    carbohydrates.add(foodDto.getCarbonhydrate());
                }
                List<String> names = new ArrayList<>();
                for(GetFoodDto foodDto : foods){
                    names.add(foodDto.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
                listBreakfast.setAdapter(adapter);
                listBreakfast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int foodId= ids.get(i);

                        // AlertDialog göster
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Öğüne Tıklandı");
                        String foodInfo = "\nYiyecek Adı: " + foodNames.get(i) +
                                "\nKalori: " + calories.get(i) + " gr" +
                                "\nProtein: " + proteins.get(i) + " gr" +
                                "\nYağ: " + fats.get(i) + " gr" +
                                "\nKarbonhidrat: " + carbohydrates.get(i) +" kcal";
                        builder.setMessage(names.get(i) + " isimli besine tıkladınız. " +foodInfo);
                        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                postMealFood(appUserId,foodId);
                                todayFat += fats.get(i);
                                todayProtein  += proteins.get(i);
                                todayCalorie  += calories.get(i);
                                todayCarbonhydrate += carbohydrates.get(i);
                                updateTodayMacros(todayFat, todayProtein, todayCalorie, todayCarbonhydrate);
                                totalBrekakfastCalorie += calories.get(i);
                                tVTotalBrekakfastCalorie.setText("Toplam : " + totalBrekakfastCalorie +" Kalori");
                                Toast.makeText(getContext(), "Öğün başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
            }
        });
        listLunchItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {;
                data = getFoodWithType("Öğle Yemeği");
                List<GetFoodDto> foods = new ArrayList<>();
                // Json verisi içindeki id, calori, fat, protein, carbohydrates bilgilerini tutabilmek e gerektiği zamanda çağırabilmek için arrayList oluşturduk
                List<Integer> ids = new ArrayList<>();
                List<String> foodNames = new ArrayList<>();
                List<Double> calories = new ArrayList<>();
                List<Double> fats = new ArrayList<>();
                List<Double> proteins = new ArrayList<>();
                List<Double> carbohydrates = new ArrayList<>();
                for(GetFoodDto foodDto : data){
                    //Foreach döngüsü sayesinde data Listesinnin içindeki id vb elemanlar ilgili dizilere aktarılıyor. Bu sayede setOnItemClikListener
                    //methodu çalıştığında tıknalınal listview elemanına ait verileri görüntüleyebileceğiz.
                    foods.add(foodDto);
                    foodNames.add((foodDto.getName()));
                    ids.add(foodDto.getId());
                    calories.add(foodDto.getCalorie());
                    fats.add(foodDto.getFat());
                    proteins.add(foodDto.getProtein());
                    carbohydrates.add(foodDto.getCarbonhydrate());
                }
                List<String> names = new ArrayList<>();
                for(GetFoodDto foodDto : foods){
                    names.add(foodDto.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
                listLunch.setAdapter(adapter);
                listLunch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int foodId= ids.get(i);

                        // AlertDialog göster
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Öğüne Tıklandı");
                        String foodInfo = "\nYiyecek Adı: " + foodNames.get(i) +
                                "\nKalori: " + calories.get(i) + " gr" +
                                "\nProtein: " + proteins.get(i) + " gr" +
                                "\nYağ: " + fats.get(i) + " gr" +
                                "\nKarbonhidrat: " + carbohydrates.get(i) +" kcal";
                        builder.setMessage(names.get(i) + " isimli besine tıkladınız.\n " +foodInfo);
                        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                postMealFood(appUserId,foodId);
                                todayFat += fats.get(i);
                                todayProtein  += proteins.get(i);
                                todayCalorie  += calories.get(i);
                                todayCarbonhydrate += carbohydrates.get(i);
                                updateTodayMacros(todayFat, todayProtein, todayCalorie, todayCarbonhydrate);
                                totalLuncCalorie += calories.get(i);
                                tvTotalLuncCalorie.setText("Toplam: " + totalLuncCalorie + " Kalori");
                                Toast.makeText(getContext(), "Öğün başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
            }
        });
        listDinnerItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = getFoodWithType("Akşam Yemeği");
                List<GetFoodDto> foods = new ArrayList<>();
                // Json verisi içindeki id, calori, fat, protein, carbohydrates bilgilerini tutabilmek e gerektiği zamanda çağırabilmek için arrayList oluşturduk
                List<Integer> ids = new ArrayList<>();
                List<String> foodNames = new ArrayList<>();
                List<Double> calories = new ArrayList<>();
                List<Double> fats = new ArrayList<>();
                List<Double> proteins = new ArrayList<>();
                List<Double> carbohydrates = new ArrayList<>();
                for(GetFoodDto foodDto : data){
                    //Foreach döngüsü sayesinde data Listesinnin içindeki id vb elemanlar ilgili dizilere aktarılıyor. Bu sayede setOnItemClikListener
                    //methodu çalıştığında tıknalınal listview elemanına ait verileri görüntüleyebileceğiz.
                    foods.add(foodDto);
                    foodNames.add((foodDto.getName()));
                    ids.add(foodDto.getId());
                    calories.add(foodDto.getCalorie());
                    fats.add(foodDto.getFat());
                    proteins.add(foodDto.getProtein());
                    carbohydrates.add(foodDto.getCarbonhydrate());
                }
                List<String> names = new ArrayList<>();
                for(GetFoodDto foodDto : foods){
                    names.add(foodDto.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, names);
                listDinner.setAdapter(adapter);
                listDinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int foodId= ids.get(i);

                        // AlertDialog göster
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Öğüne Tıklandı");
                        String foodInfo = "\nYiyecek Adı: " + foodNames.get(i) +
                                "\nKalori: " + calories.get(i) + " gr" +
                                "\nProtein: " + proteins.get(i) + " gr" +
                                "\nYağ: " + fats.get(i) + " gr" +
                                "\nKarbonhidrat: " + carbohydrates.get(i) +" kcal";
                        builder.setMessage(names.get(i) + " isimli besine tıkladınız. \n" +foodInfo);
                        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                postMealFood(appUserId,foodId);
                                todayFat += fats.get(i);
                                todayProtein  += proteins.get(i);
                                todayCalorie  += calories.get(i);
                                todayCarbonhydrate += carbohydrates.get(i);
                                updateTodayMacros(todayFat, todayProtein, todayCalorie, todayCarbonhydrate);
                                totalDinnerCalorie += calories.get(i);
                                tvTotalDinnerCalorie.setText("Toplam: " +totalDinnerCalorie + " Kalori");
                                Toast.makeText(getContext(), "Öğün başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
                
            }
        });
        return rootView;

    }
    // 43. satırda tanımlamış olduğumuz data'yı döndürüyoruz. Eğer methodumuz başarıyla çalışırsa json listesini geriye döndürecektir
    private List<GetFoodDto> getFoodWithType(String type){
        IFood food = RetrofitClient.getRetrofitInstance().create(IFood.class);
        Call<List<GetFoodDto>> call = food.getFoodWithType(type);
        call.enqueue(new Callback<List<GetFoodDto>>() {
            @Override
            public void onResponse(Call<List<GetFoodDto>> call, Response<List<GetFoodDto>> response) {
                if(response.isSuccessful()){
                    data = response.body();
                    Toast.makeText(getContext(),"Başarılı",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Başarısız",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetFoodDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }
    private void postMealFood(int appUserId, int foodId){
        IMealFood iMealFood = RetrofitClient.getRetrofitInstance().create(IMealFood.class);
        PostMealFoodDto postMealFoodDto = new PostMealFoodDto(appUserId,foodId);
        Call<Void> call = iMealFood.postMealFood(postMealFoodDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"Öğününüz Başarıyla Kaydedilmiştir", Toast.LENGTH_LONG).show();
                }else {
                    // Başarısız cevap alındı, hata durumunu göster
                    String errorMessage = "Error: " + response.code() + " " + response.message();
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();

                    // Server tarafından dönen detaylı hata mesajını almak için:
                    try {
                        String errorBody = response.errorBody().string();
                        // errorBody içinde server tarafından dönen JSON formatında hata mesajını kullanabilirsiniz.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void updateTodayMacros(double todayFat, double todayProtein, double todayCalorie, double todayCarbonhydrate  ){
        dayCarbonhydrate.setText(decimalFormat.format(todayCarbonhydrate) + " g");
        dayFat.setText(decimalFormat.format(todayFat) + " g");
        dayProtein.setText(decimalFormat.format(todayProtein) + " g");
        dayCalorie.setText(decimalFormat.format(todayCalorie) + " kcal");
    }
}
