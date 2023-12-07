package com.example.dietapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
        }
        else if (itemId == R.id.nav_bmi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BmiFragment()).commit();
        }
        else if (itemId == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
        }


        // Eğer Çıkış yaparsa MainActivity'ye geçiş yap
        else if (itemId == R.id.nav_logout) {
            Intent intent = new Intent(NavigationDrawer.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();        }

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