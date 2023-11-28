package com.example.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonRegister, buttonComeBack;
    private EditText editTextFirstName,editTextLastName, editTextEmail,editTextPassword,editTextRepeatPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText editTextFirstName =(EditText) findViewById(R.id.editTextFirstName);
        EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        EditText editTextRepeatPassword = (EditText) findViewById(R.id.editTextRepeatPassword);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonComeBack = (Button) findViewById(R.id.buttonComeBack);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String repeatPassword = editTextRepeatPassword.getText().toString();

                if (!password.equals(repeatPassword)) {
                    Toast.makeText(SignUpActivity.this, "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        buttonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giriş ekranına geri dön
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(intent);
                SignUpActivity.this.finish();
            }
        });
    }
}
