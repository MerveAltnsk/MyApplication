package com.MyApplication.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.provider.MediaStore;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MyApplication.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class AnimalProfiles extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int GALLERY_REQUEST_CODE = 123;
    private Bitmap selectedImageBitmap;
    private ImageView uploadİmage;


    EditText animalName, type, age, gender, address, picture;
    Button CreateProfileAnimal, backButton4;


    ProgressBar progressBar2;
    DatabaseReference databaseRef;

    DatabaseReference mDatabase;

    String res;



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

        uploadİmage = findViewById(R.id.imageView5);


        backButton4 = findViewById(R.id.backButton5);

        databaseRef = FirebaseDatabase.getInstance().getReference("animal_profiles");

        mDatabase = FirebaseDatabase.getInstance("https://myapplicationanimalproject-default-rtdb.europe-west1.firebasedatabase.app").getReference();


        uploadİmage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });




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
            } // OnClickListener kapanış parantezi
        }); // CreateProfileAnimal.setOnClickListener kapanış parantezi
    } // onCreate kapanış parantezi

    // AnimalProfiles sınıfınızın içine bu yöntemi ekleyin




    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                uploadİmage.setImageBitmap(selectedImageBitmap);

                saveProfilePicture(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfilePicture(Bitmap imageBitmap) {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String imageBase64 = convertBitmapToBase64(imageBitmap);

        editor.putString("profile_image", imageBase64);
        editor.apply();
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
    }

    private Bitmap convertBase64ToBitmap(String base64String) {
        byte[] decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    }