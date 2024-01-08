package com.example.dietapp;

import android.content.Intent;
import android.os.Bundle;  // aktiviteler arasında veya bileşenler arasında veri taşımak için kullanılır.
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dietapp.dtos.RegisterDto;
import com.example.dietapp.interfaces.IRegister;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonRegister, buttonComeBack;
    private EditText editTextFirstName,editTextLastName, editTextEmail,editTextPassword,editTextRepeatPassword,editTextUserName;
    private  IRegister iRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextFirstName =(EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextSurname);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRepeatPassword = (EditText) findViewById(R.id.editTextRepeatPassword);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonComeBack = (Button) findViewById(R.id.buttonComeBack);



        buttonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giriş ekranına geri dön
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(intent);
                SignUpActivity.this.finish();
            }
        });

        //kayıt ol butonu gerekli bilgiler girince kayıtola basıp kaydoluyoruz
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String userName = editTextUserName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String repeatPassword = editTextRepeatPassword.getText().toString();
                String userRole = "User";



                //aşağıda sifre kontrolü ve eposta kontölü yapılıyor
                if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()
                || repeatPassword.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Lütfen Tüm Bilgilerinizi Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repeatPassword)) {
                    Toast.makeText(SignUpActivity.this, "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email.contains("@")){
                    Toast.makeText(SignUpActivity.this, "E Posta Adresinizi Doğru Giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }
                IRegister iRegister = RetrofitClient.getRetrofitInstance().create(IRegister.class); //Retrofit client sınıfından static getRetrofitInstance fonksiyonunu çağırıyor.
                RegisterDto registerDto = new RegisterDto(firstName,lastName,userName,email,password,userRole); //register dto'ya kayıt olmak için ihtiyacımız olan nesneleri tanımlıyoruz
                Call<Void> call = iRegister.registerUser(registerDto);



                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Başarılı bir şekilde cevap alındı, başarı mesajını göster
                            Toast.makeText(SignUpActivity.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            SignUpActivity.this.startActivity(intent);
                            SignUpActivity.this.finish();
                        } else {
                            // Başarısız cevap alındı, hata durumunu göster
                            String errorMessage = "Error: " + response.code() + " " + response.message();
                            Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(SignUpActivity.this, t.getMessage() , Toast.LENGTH_LONG).show();
                    }
                });



            };


        });
    }
}