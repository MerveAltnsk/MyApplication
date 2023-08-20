package com.MyApplication.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PetProfileActivity extends AppCompatActivity {

    Button adoptButton,buttonBackPetProfile1;
    Button locationButton;
    ImageView profileImage;
    TextView textViewName;
    TextView textViewAge;
    TextView textViewGender;
    TextView textViewType;
    TextView textViewAddress;
   // TextView adoptedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        adoptButton = findViewById(R.id.adoptButton);
        locationButton = findViewById(R.id.locationButton);
        profileImage = findViewById(R.id.profileImage);
        textViewName = findViewById(R.id.textViewName);
        textViewAge = findViewById(R.id.textViewAge);
        textViewGender = findViewById(R.id.textViewGender);
        textViewType = findViewById(R.id.textViewType);
        textViewAddress = findViewById(R.id.textViewAddress);
        //adoptedUser = findViewById(R.id.adoptedUser);
        buttonBackPetProfile1 = findViewById(R.id.buttonBackPetProfile1);



        PetModel petModel = getIntent().getParcelableExtra("pet");
        ArrayList<PetModel> pets = getIntent().getParcelableArrayListExtra("list");

        Glide.with(getApplicationContext())
                .load(petModel.pictureUrl)
                .centerCrop()
                .placeholder(R.drawable.dog)
                .into(profileImage);


        textViewName.setText("Name: " +petModel.name);
        textViewAge.setText("Age: " + petModel.age);
        textViewGender.setText("Gender: " + petModel.gender);
        textViewType.setText("Type: " + petModel.type);
        textViewAddress.setText("Address: " + petModel.address);
        textViewAddress.setText("Address: " + petModel.address);
        //adoptedUser.setText("adopted User: " + petModel.adoptedUser);


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PetProfileActivity.this, MapsActivity.class);
                intent.putParcelableArrayListExtra("list", pets);
                intent.putExtra("pet", petModel);
                startActivity(intent);
            }
        });
    }
}