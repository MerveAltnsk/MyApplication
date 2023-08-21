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
    TextView adoptedUser;

    View separatorView;

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
        adoptedUser = findViewById(R.id.adoptedUser);
        buttonBackPetProfile1 = findViewById(R.id.buttonBackPetProfile1);
        separatorView = findViewById(R.id.separatorView);


        buttonBackPetProfile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                if (textViewAddress.getText().length() > 50) {
                    separatorView.setVisibility(View.VISIBLE);
                } else {
                    separatorView.setVisibility(View.GONE);
                }
            }
        });

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
        adoptedUser.setText("adopted User: " + petModel.adoptedUser);

        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                String username = sharedPref.getString("username", "test");
                if(!petModel.adoptedUser.equals("No one")){
                    Toast.makeText(PetProfileActivity.this, "This pet already adopted!", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://myapplicationanimalproject-default-rtdb.europe-west1.firebasedatabase.app").getReference();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("username", username);
                    System.out.println("@@@@???? "+petModel.id);
                    mDatabase.child("pets").child(petModel.id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PetProfileActivity.this, "This pet adopted by you", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(PetProfileActivity.this, "Error happens!", Toast.LENGTH_SHORT).show();

                            finish();

                        }
                    });
                }
            }
        });

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