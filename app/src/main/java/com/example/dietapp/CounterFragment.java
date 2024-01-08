package com.example.dietapp;


import androidx.fragment.app.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils; //Bu sınıf, metinle ilgili çeşitli işlemleri kolaylaştırmak ve kontrol etmek için statik metotlar içerir.
import android.view.LayoutInflater; //Android uygulamalarında XML tabanlı layout dosyalarını (*.xml) Java kodunda kullanılabilir View nesnelerine dönüştürmek için kullanılan bir sınıftır
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView; //kullanıcının öğeler arasında gezinmesine ve bir öğe seçmesine izin veren listview için
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.dietapp.interfaces.CalorieEntry;

import java.util.ArrayList;
import java.util.List;

public class CounterFragment extends Fragment {

    private DatePicker datePicker;
    private EditText editTextBurnedCalories;
    private EditText editTextConsumedCalories;
    private CalorieEntryAdapter adapter;
    private final List<CalorieEntry> calorieEntries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter, container, false);

        //fragment_counter.xml deki şeyler
        datePicker = view.findViewById(R.id.datePicker);
        editTextBurnedCalories = view.findViewById(R.id.editTextBurnedCalories);
        editTextConsumedCalories = view.findViewById(R.id.editTextConsumedCalories);
        Button saveButton = view.findViewById(R.id.saveButton);
        ListView listView = view.findViewById(R.id.listView);

        // CalorieEntryAdapter adlı bir adapter oluşturulup ve bu adapteri ListView ile ilişkilendirildi. Adapter, ListView'ın içeriğini yönetir ve veri değişikliklerini güncellemek için kullanılır.
        adapter = new CalorieEntryAdapter(requireContext(), calorieEntries);
        listView.setAdapter(adapter);


        //ListView üzerinde bir öğeye uzun tıklandığında çağrılacak onItemLongClickAction metodu. Bu öğenin silinip silinmeyeceğini kullanıcıya sorar
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onItemLongClickAction(position);
                return true;
            }
        });


        //Kaydet butonuna tıklandığında çağrılacak onSaveButtonClick metodu. Bu metod DatePicker, EditText alanlarından alınan verilerle yeni bir CalorieEntry oluşturur ve bu girişi calorieEntries listesine ekler.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick(v);
            }
        });

        return view;
    }

    //kullanıcıdan verileri alıyor,
    public void onSaveButtonClick(View view) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        String date = day + "/" + month + "/" + year;
        String burnedCaloriesStr = editTextBurnedCalories.getText().toString();
        String consumedCaloriesStr = editTextConsumedCalories.getText().toString();

        if (TextUtils.isEmpty(burnedCaloriesStr) || TextUtils.isEmpty(consumedCaloriesStr)) {
            Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }

        int burnedCalories = Integer.parseInt(burnedCaloriesStr);
        int consumedCalories = Integer.parseInt(consumedCaloriesStr);

        //aynı tarihte tek veri girişini sağlar
        if (isEntryExistsForDate(date)) {
            Toast.makeText(requireContext(), "Bu tarihe zaten bir giriş yapılmış.", Toast.LENGTH_SHORT).show();
        } else {
            CalorieEntry entry = new CalorieEntry(date, burnedCalories, consumedCalories);
            calorieEntries.add(entry);
            adapter.notifyDataSetChanged();
        }
    }

    //aynı tarih ve günde bir girişin zaten olup olmadığını kontrol eder.
    private boolean isEntryExistsForDate(String date) {
        for (CalorieEntry entry : calorieEntries) {
            if (entry.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    //kullanıcı basılı tutunca mesaj veririr ve listview'deki yer silinir
    private void onItemLongClickAction(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bu girişi silmek istediğinizden emin misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteEntry(position);
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Kullanıcı iletişim kutusunu iptal etti
                    }
                });
        builder.create().show();
    }

    //listview'deki metni kaldırır, listview'i güncellerve mesaj gönderir
    private void deleteEntry(int position) {
        calorieEntries.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(requireContext(), "Giriş başarıyla silindi.", Toast.LENGTH_SHORT).show();
    }
}



