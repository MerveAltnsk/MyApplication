package com.MyApplication.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewProfile;
    private Bitmap selectedImageBitmap;
    Button buttonBackPetProfile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String usernameFromShared = prefs.getString("username", "");
        String emailFromShared = prefs.getString("email", "");

        Log.d("UserProfileActivity", "usernameFromShared: " + usernameFromShared);
        Log.d("UserProfileActivity", "emailFromShared: " + emailFromShared);

        EditText name = findViewById(R.id.editTextLoginUserName);
        Button save = findViewById(R.id.saveProfile);

        Button updatePictureButton = findViewById(R.id.updateProfilePicture);

        buttonBackPetProfile2 = findViewById(R.id.buttonBackPetProfile2);
        name.setText(usernameFromShared);
        imageViewProfile = findViewById(R.id.imageViewProfile);

        buttonBackPetProfile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updatePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nname = name.getText().toString().trim();
                if (!nname.isEmpty()) {
                    prefs.edit().putString("username", nname).apply();
                }
            }
        });

        loadProfilePicture();
    }

    private void deleteProfilePicture() {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("profile_image");
        editor.apply();

        imageViewProfile.setImageResource(R.drawable.beyaz);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageViewProfile.setImageBitmap(selectedImageBitmap);

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

    private void loadProfilePicture() {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String imageBase64 = prefs.getString("profile_image", null);

        if (imageBase64 != null) {
            selectedImageBitmap = convertBase64ToBitmap(imageBase64);
            imageViewProfile.setImageBitmap(selectedImageBitmap);
        }
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