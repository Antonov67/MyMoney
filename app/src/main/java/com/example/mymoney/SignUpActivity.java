package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText loginRegField, pswrdRegField, nameField;
    Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        buttonReg = findViewById(R.id.buttonReg);
        loginRegField = findViewById(R.id.loginRegField);
        pswrdRegField = findViewById(R.id.pswrdRegField);
        nameField = findViewById(R.id.nameField);


        Context context = this;
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(loginRegField.getText().toString(),pswrdRegField.getText().toString(), nameField.getText().toString());
                if (!loginRegField.getText().toString().equals("")
                        && !pswrdRegField.getText().toString().equals("")){
                    if (BD.isUserUniq(loginRegField.getText().toString(),context)){
                        BD.addUser(user,context);
                        Toast.makeText(getApplicationContext(),"Пользователь успешно добавлен",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Такой логин уже существует",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Заполните правильно поля",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}