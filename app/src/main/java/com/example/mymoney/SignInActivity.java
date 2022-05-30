package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    Button signInButton;
    EditText login, pswrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        signInButton = findViewById(R.id.signInButton);
        login = findViewById(R.id.loginField);
        pswrd = findViewById(R.id.pswrdField);

        Context context = this;
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!login.getText().toString().equals("")){
                    if (BD.isUserExist(login.getText().toString(),pswrd.getText().toString(),context)){
                        Log.d("money777", "вход выполнен");
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Ошибка входа", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    public void signUp(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public void info(View view) {

        startActivity(new Intent(SignInActivity.this, InfoActivity.class));
    }
}