package com.MyApplication.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class AnimalProfiles extends AppCompatActivity {
    EditText animalName, type, age, gender, address, picture;
    Button CreateProfileAnimal,backButton4;
    ProgressBar progressBar2;
    DatabaseReference databaseRef;

    DatabaseReference mDatabase;

    String res;



    /*
    public AnimalProfiles() {
        // Boş yapıcı metot gereklidir (Firebase tarafından kullanılır)
    }

    public AnimalProfiles(EditText animalName, EditText type, EditText age, EditText gender, EditText address, EditText pictureUrl) {
        this.animalName = animalName;
        this.type = type;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.picture = pictureUrl;
    }
    public AnimalProfiles(String animalNameText, String typeText, String ageText, String genderText, String addressText, String pictureText) {                  //Constructor
    }

    public String getAnimalName() {
        return animalName.getText().toString();
    }

    public String getType() {
        return type.getText().toString();
    }

    public String getAge() {
        return age.getText().toString();
    }

    public String getGender() {
        return gender.getText().toString();
    }

    public String getAddress() {
        return address.getText().toString();
    }

    public String getPicture() {
        return picture.getText().toString();
    }



    public void setAnimalName(String text) {
        animalName.setText(text);
    }
    public void setType(String text) {
        type.setText(text);
    }

    public void setAge(String text) {
        age.setText(text);
    }

    public void setGender(String text) {
        gender.setText(text);
    }

    public void setAddress(String text) {
        address.setText(text);
    }

    public void setPicture(String text) {
        picture.setText(text);
    }


    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_profiles);

        animalName = findViewById(R.id.TextName);
        type = findViewById(R.id.TextType);
        age = findViewById(R.id.TextAge);
        gender = findViewById(R.id.TextGender);
        address = findViewById(R.id.TextAddress);
        picture = findViewById(R.id.TextPicture);
        CreateProfileAnimal = findViewById(R.id.buttonAnimal);
        //progressBar2 = findViewById(R.id.progressBar2);
        backButton4 = findViewById(R.id.backButton5);

        databaseRef = FirebaseDatabase.getInstance().getReference("animal_profiles");

        mDatabase = FirebaseDatabase.getInstance("https://myapplicationanimalproject-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        backButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalProfiles.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        CreateProfileAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("pets").orderByKey().limitToLast(1).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {             //realtime database den verileri alma
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult()));
                            Object val = task.getResult().getValue();
                            Random r = new Random();
                            int low = 10;
                            int high = 100000;
                            int result = r.nextInt(high-low) + low;
                            if(val == null)
                                val = result;
                            res = val.toString();
                            if(res.contains("=")){
                                res = res.substring(1, res.indexOf('='));
                                res = "" + (Integer.parseInt(res) + 1);
                                Log.d("out", res);
                            }

                            Animal animal = new Animal(animalName.getText().toString(), age.getText().toString(), gender.getText().toString(), type.getText().toString(), address.getText().toString(), picture.getText().toString(), "No one", res);
                            mDatabase.child("pets").child(res).setValue(animal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        System.out.println("@@@@@@IIII SUCC");
                                    }else
                                        System.out.println("@@@@@@IIII ERROR");
                                }
                            });
                        }
                    }
                });

                mDatabase.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Assuming you have TextViews and ImageViews in your layout with the respective IDs
                                TextView textViewName = findViewById(R.id.textViewName);
                                TextView textViewAge = findViewById(R.id.textViewAge);
                                TextView textViewGender = findViewById(R.id.textViewGender);
                                TextView textViewType = findViewById(R.id.textViewType);
                                TextView textViewAddress = findViewById(R.id.textViewAddress);
                                ImageView profileImage = findViewById(R.id.profileImage);

                                // Get the values from the snapshot
                                String name = snapshot.child("name").getValue(String.class);
                                String age = snapshot.child("age").getValue(String.class);
                                String gender = snapshot.child("gender").getValue(String.class);
                                String type = snapshot.child("type").getValue(String.class);
                                String address = snapshot.child("address").getValue(String.class);
                                String pictureUrl = snapshot.child("pictureUrl").getValue(String.class);

                                // Set the values to the TextViews and ImageView
                                textViewName.setText(name);
                                textViewAge.setText(age);
                                textViewGender.setText(gender);
                                textViewType.setText(type);
                                textViewAddress.setText(address);

                                // You can use a library like Picasso or Glide to load the image from the URL into the ImageView
                                // Here's an example using Picasso:
                                Picasso.get().load(pictureUrl).into(profileImage);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
}