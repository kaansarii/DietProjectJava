package com.example.dietapp;


//Bu CalorieEntryAdapter sınıfı, CalorieEntry nesnelerini ListView içinde görüntülemek için tasarlandı.

//İŞLEYİSİ;
//CalorieEntryAdapter sınıfının constructor'ı, veri kaynağı olan List<CalorieEntry>'yi alır ve ArrayAdapter'a geçer.
//getView metodu, her bir listedeki öğeyi temsil eden bir görünüm döndürür. Eğer mevcut bir görünüm yoksa, yeni bir tane oluşturulur.
//Bu metot, CalorieEntry nesnesinin tarihini ve kalori farkını içeren iki TextView öğesini günceller.

//CalorieEntryAdapter,  CalorieEntry'den nesnelerini alır ve bir ListView görünümde gösterir.


import android.annotation.SuppressLint; //Bu  belirli bir kod bloğu, sınıf, metot veya alan üzerinde Android Studio'nun statik analiz uyarılarını devre dışı bırakmak veya özelleştirmek için kullanılır
import android.content.Context; //this kullanmak için ekleniyor, bir aktivitenin  kaynaklara ulaşabilmesini sağlar.
import android.view.LayoutInflater; //kullanıcı arayüzü elemanlarını XML dosyalarından Java koduna dönüştürmek ve bir View hiyerarşisi oluşturmak için kullanılan bir sınıftır.
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; //Bu sınıf, bir veri kaynağını alır ve bu veri kaynağını kullanarak  (view) eşleyerek, bu öğelerin gösterilmesini sağlar.
import android.widget.TextView;

import androidx.annotation.NonNull; // Java kodunda belirli bir parametre, dönüş değeri, veya sınıf alanının null olamayacağını belirtmek için kullanılır.

import com.example.dietapp.interfaces.CalorieEntry; //CalorieEntry adında bir arayüzün, dietapp adlı bir paket içindeki com.example isim alanında yer aldığını belirtir
import java.util.List; //elemanları sıralı bir şekilde tutmayı sağlıor




public class CalorieEntryAdapter extends ArrayAdapter<CalorieEntry> {

    public CalorieEntryAdapter(Context context, List<CalorieEntry> entries) {
        super(context, 0, entries);
    }

    @NonNull
    @SuppressLint({"SetTextI18n", "ViewHolder"}) //tırnak içeirindeki hataların önüne geçke ve gizelemek için
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        //Bu yöntem, liste elemanlarının düzenini ve tasarımını özelleştirmek için kullanılır.
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);

        CalorieEntry entry = getItem(position); //adapter sınıfındaki öğeleri almak için


        // Yeni verinin atanması
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);
        TextView textViewCalorieDifference = convertView.findViewById(R.id.textViewCalorieDifference);

        if (entry != null) {
            textViewDate.setText(entry.getDate()); //tarihi alır ve gösterir
            textViewCalorieDifference.setText("Calorie Difference: " + entry.getCalorieDifference()); //kalori farkı bilgisin alır ve gösteriri
        }

        return convertView;
    }
}

