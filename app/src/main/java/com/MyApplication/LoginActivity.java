package com.MyApplication;

import androidx.appcompat.app.AppCompatActivity;

import com.MyApplication.myapplication.Database;
import com.MyApplication.myapplication.HomeActivity;
import com.MyApplication.myapplication.R;
import com.MyApplication.myapplication.RegisterActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.editTextLoginUserName);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textView5);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = edUsername.getText().toString();
                        String password = edPassword.getText().toString();
                        tv.setText(username);
                        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT);
                    }
                }
        );


    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.editTextLoginUserName);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                edUsername.setText("fasd");
                Database db = new Database(getApplicationContext(), "PawTime",null,1);                           //Yeni nesne oluşturuyoruz, ilk parametre getApplicationContext, ikinci parametre veritabanımızın ismi, üçüncü parametre aslında faktördür her hangi bir p yazarız
                if (username.length()==0 || password.length()==0){
                    Toast.makeText(LoginActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();           //Login yapıldığında Success yazısını ortaya çıkartır
                }else{
                    if (db.login(username, password) == 1){                             //username, şifre 1 e eşitse login başarılı demeketir
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();                   //Login yapıldığında Success yazısını ortaya çıkartır
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);       //Bazı giriş bilgilerini kaydetmemiz gerekiyor

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("username", username);                           //editor e kullanıcı adlarını kaydediyoruz
                        //datamızı key ve value ile kaydetmek için
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();                   //Login yapıldığında Invalid Username and Password,Geçersiz kullanıcı adı ve şifre yazısını ortaya çıkartır
                    }
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        }
    */


}
