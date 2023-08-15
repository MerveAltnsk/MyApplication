package com.MyApplication.myapplication;

import static com.MyApplication.myapplication.R.id;
import static com.MyApplication.myapplication.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

class Animal {
    public String name;
    public String age;
    public String gender;
    public String type;
    public String address;
    public String picture_name;

    public String user;
    public String id;
    Animal() {

    }

    public Animal(String name, String age, String gender, String type, String address, String picture_name, String user, String id) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.address = address;
        this.picture_name = picture_name;
        this.user = user;
        this.id = id;
    }


}

public class MainActivity extends AppCompatActivity{

    FirebaseAuth auth;
    Button button;                                                      //Firebase kullanıcısı için bir button
    FirebaseUser user;
    DatabaseReference mDatabase;

    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        auth = FirebaseAuth.getInstance();
        button = findViewById(id.logout);

        user = auth.getCurrentUser();
        if (auth.getCurrentUser() ==  null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });





    }
}