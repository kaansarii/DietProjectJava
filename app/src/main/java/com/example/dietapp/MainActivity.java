package com.example.dietapp;

import android.content.Intent; //intent kullanmak için
import android.net.Uri;
import android.os.Bundle; // iki bileşen arasında veri taşımak ve saklamak için kullanılan bir veri yapısıdır
import android.util.Log; //log kullanmka için
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; //toast kullanmka için


import androidx.appcompat.app.AppCompatActivity;

import com.example.dietapp.dtos.SharedId;
import com.example.dietapp.dtos.LoginDto;
import com.example.dietapp.dtos.LoginResponse;
import com.example.dietapp.interfaces.ILogin;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonLoginRegister;
    private TextView textViewEmail,textViewPassword;
    private ImageView profileImageView;
    BmiFragment bmiFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        TextView textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        ImageView profileImageView = findViewById(R.id.profileImageView);




        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                ILogin iLogin = RetrofitClient.getRetrofitInstance().create(ILogin.class);
                LoginDto loginDto = new LoginDto(email,password);
                Call<LoginResponse> call = iLogin.loginUser(loginDto);
                if(email.contains("@")) //E postada @ işareti olup olmadığını kontrol ediyor
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                // Başarılı bir şekilde cevap alındı, başarı mesajını göster
                                Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_LONG).show();
                                try {

                                    //İd verisini api'dan almak için ilgili veriyi LoginResponse Modeline attım
                                    LoginResponse loginResponse = response.body();
                                    int id = loginResponse.getUserId(); //ilgili veriyi id değişkenine attım
                                    String role = loginResponse.getRole(); //Rol Bilgisini atadım
                                    SharedId sharedId = SharedId.getInstance();
                                    sharedId.setSharedData(id);
                                    //Role bilgisinde user varsa Navigation Drawer'a yönlendirecek, admin varsa admin panaline yönlendirecek

                                    if(role.contains("User")){
                                        Intent intent = new Intent(MainActivity.this,NavigationDrawer.class);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(MainActivity.this,AdminPanelActivity.class);
                                        startActivity(intent);
                                    }



                                }catch (Exception e){
                                    e.printStackTrace();
                                    Log.e("TAG", "Exception: " + e.getMessage());
                                }

                            } else {
                                // Başarısız cevap alındı, hata durumunu göster
                                String errorMessage = "Error: " + response.code() + " " + response.message();
                                Toast.makeText(MainActivity.this, "Kullanıcı Adı veya Şifreniz Yanlış", Toast.LENGTH_LONG).show();

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
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                else
                    Toast.makeText(MainActivity.this,"Lütfen Geçerlİ E Posta Adresi Giriniz",Toast.LENGTH_LONG).show();


            }
        });





        Button buttonLoginRegister = (Button) findViewById(R.id.buttonLoginRegister);
        buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kayıt ol ekranına geçiş yap
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }

    }

