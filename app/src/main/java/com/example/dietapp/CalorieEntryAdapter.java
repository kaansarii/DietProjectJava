package com.example.dietapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dietapp.interfaces.CalorieEntry;
import java.util.List;


//Bu CalorieEntryAdapter sınıfı, CalorieEntry nesnelerini ListView içinde görüntülemek için tasrlandı.
//İŞLEYİSİ;
//CalorieEntryAdapter sınıfının constructor'ı, veri kaynağı olan List<CalorieEntry>'yi alır ve ArrayAdapter'a geçer.
//getView metodu, her bir listedeki öğeyi temsil eden bir görünüm döndürür. Eğer mevcut bir görünüm yoksa, yeni bir tane oluşturulur.
//Bu metot, CalorieEntry nesnesinin tarihini ve kalori farkını içeren iki TextView öğesini günceller.

//CalorieEntryAdapter,  CalorieEntry'den nesnelerini alır ve bir ListView görünümde gösterir.

public class CalorieEntryAdapter extends ArrayAdapter<CalorieEntry> {

    public CalorieEntryAdapter(Context context, List<CalorieEntry> entries) {
        super(context, 0, entries);
    }

    @NonNull
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // convertView kullanımını kaldır
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);

        CalorieEntry entry = getItem(position);


        // Yeni verinin atanması
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        TextView textViewCalorieDifference = convertView.findViewById(R.id.textViewCalorieDifference);

        if (entry != null) {
            textViewDate.setText(entry.getDate());
            textViewCalorieDifference.setText("Calorie Difference: " + entry.getCalorieDifference());
        }

        return convertView;
    }
}

