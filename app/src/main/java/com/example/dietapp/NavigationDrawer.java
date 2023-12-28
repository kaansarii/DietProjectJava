package com.example.dietapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        // Toolbar'ı bul ve ayarla
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout ve NavigationView'ı bul
        drawerLayout =findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ActionBarDrawerToggle'ı oluştur ve DrawerLayout ile bağla
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Uygulama ilk başladığında HomeFragment'ı yükle
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    //Burada ise seçilen butona göre sayfaya gitme işlemi var
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // NavigationView'dan seçilen öğenin id'sini al
        int itemId = item.getItemId();

        // Seçilen öğeye göre fragment değiştirme işlemleri
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_nutrients) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NutrientsFragment()).addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_bmi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BmiFragment()).addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_counter) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CounterFragment()).addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_social_facebook){
            // Doğrudan twitter sayfasını bir URI kullanarak aç
            Uri facebookUri = Uri.parse("https://www.facebook.com/ahievranedutr/?locale=tr_TR");

            // Tarayıcıyı açmak için bir Intent oluştur
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, facebookUri);
            startActivity(browserIntent);


            //ekrana bir toast mesajı göster
            Toast.makeText(NavigationDrawer.this, "Facebook Sayfasına Yönlendiriliyor", Toast.LENGTH_LONG).show();
        }

        else if (itemId == R.id.nav_social_twitter){
            // Doğrudan twitter sayfasını bir URI kullanarak aç
            Uri twitterUri = Uri.parse("https://twitter.com/ahievranedutr?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");

            // Tarayıcıyı açmak için bir Intent oluştur
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, twitterUri);
            startActivity(browserIntent);

            //ekrana bir toast mesajı göster
            Toast.makeText(NavigationDrawer.this, "Twitter Sayfasına Yönlendiriliyor", Toast.LENGTH_LONG).show();
        }

        else if (itemId == R.id.nav_social_instagram) {
            // Doğrudan Instagram sayfasını bir URI kullanarak aç
            Uri instagramUri = Uri.parse("https://www.instagram.com/ahievran.kampus/");

            // Tarayıcıyı açmak için bir Intent oluştur
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, instagramUri);
            startActivity(browserIntent);


            //ekrana bir toast mesajı göster
            Toast.makeText(NavigationDrawer.this, "Instagram Sayfasına Yönlendiriliyor", Toast.LENGTH_LONG).show();
        }


        // Eğer Çıkış yaparsa MainActivity'ye geçiş yap
        else if (itemId == R.id.nav_logout) {
            // Çıkış işlemi için onay mesajı gösterir
            new AlertDialog.Builder(this)
                    .setTitle("Çıkış") //başlığın ismi
                    .setMessage("Çıkış yapmak istediğinizden emin misiniz?") //kullanıcıya sorguyor
                    //evet derse
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Kullanıcı "Evet" dediğinde çıkış işlemini gerçekleştir
                            Intent intent = new Intent(NavigationDrawer.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(NavigationDrawer.this, "Çıkış yapıldı", Toast.LENGTH_LONG).show();
                        }
                    })
                    //hayır derse
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Kullanıcı "Hayır" dediğinde hiçbir şey yapma veya isteğe bağlı olarak işlem yapabilirsin
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


        // Navigation Drawer'ı kapat
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        // Eğer Navigation Drawer açıksa, geri tuşuna basınca kapat
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            // Açık değilse, normal geri tuşu davranışını sürdür
            super.onBackPressed();
        }
    }
}