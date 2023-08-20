package com.MyApplication.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.android.gms.maps.MapsInitializer;

public class HomeActivity extends AppCompatActivity implements PetsAdapter.OnPetClick {


    private MapView mapView;
    //private Button showAllAnimalsButton;
    private ArrayList<PetModel> petModelArrayList = new ArrayList<>();



    private ViewGroup rootView;           /////////
    private MapView largeMapView;       //////////////




    private LinearLayout profileContainer;
    private DatabaseReference databaseRef;
    Button btn5, buttonAnimalCreate1;
    EditText CreatedAnimalName1, CreatedAnimalType1, CreatedAnimalAge1, CreatedAnimalGender1, CreatedAnimalAddress1, CreatedAnimalPicture1,CreatedAnimallatitude1,CreatedAnimallongitude1;

    DatabaseReference mDatabase;
    ImageView imageView8;

    //   DatabaseReference databaseRef2;
    //  DatabaseReference mDatabase2;


    private static final int SPLASH_SCREEN = 2000; // 2 saniye


    //private SupportMapFragment supportMapFragment;
    //public GoogleMap mMap;

    public HomeActivity() {
        // Boş yapıcı metot gereklidir (Firebase tarafından kullanılır)
    }

    public HomeActivity(EditText animalName, EditText type, EditText age, EditText gender, EditText address, EditText pictureUrl,EditText latitude,EditText longitude) {
        this.CreatedAnimalName1 = animalName;
        this.CreatedAnimalType1 = type;
        this.CreatedAnimalAge1 = age;
        this.CreatedAnimalGender1 = gender;
        this.CreatedAnimalAddress1 = address;
        this.CreatedAnimalPicture1 = pictureUrl;

        this.CreatedAnimallatitude1 = latitude;
        this.CreatedAnimallongitude1 = longitude;



    }

    public HomeActivity(String animalNameText, String typeText, String ageText, String genderText, String addressText, String pictureText,String latitudeText,String longitudeText) {                  //Constructor
    }

    public HomeActivity(EditText createdAnimalName1, EditText createdAnimalType1, EditText createdAnimalAge1, EditText createdAnimalGender1, EditText createdAnimalAddress1) {
    }

    public String getAnimalName() {
        return CreatedAnimalName1.getText().toString();
    }

    public String getType() {
        return CreatedAnimalType1.getText().toString();
    }

    public String getAge() {
        return CreatedAnimalAge1.getText().toString();
    }

    public String getGender() {
        return CreatedAnimalGender1.getText().toString();
    }

    public String getAddress() {
        return CreatedAnimalAddress1.getText().toString();
    }

    public String getPicture() {
        return CreatedAnimalPicture1.getText().toString();
    }


    public String getlatitude() {
        return CreatedAnimallatitude1.getText().toString();
    }

    public String getlongitude() {
        return CreatedAnimallongitude1.getText().toString();
    }




    public void setAnimalName(String text) {
        CreatedAnimalName1.setText(text);
    }

    public void setType(String text) {
        CreatedAnimalType1.setText(text);
    }

    public void setAge(String text) {
        CreatedAnimalAge1.setText(text);
    }

    public void setGender(String text) {
        CreatedAnimalGender1.setText(text);
    }

    public void setAddress(String text) {
        CreatedAnimalAddress1.setText(text);
    }

    public void setPicture(String text) {
        CreatedAnimalPicture1.setText(text);
    }


    public void setlatitude(String text) {
        CreatedAnimallatitude1.setText(text);
    }

    public void setlongitude(String text) {
        CreatedAnimallongitude1.setText(text);
    }


    private RecyclerView rv;

    private ImageView buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rv = findViewById(R.id.petsRV);

        //mDatabase = FirebaseDatabase.getInstance().getReference("animal_profiles");             // Firebase veritabanı referansını almak için

        buttonLogout = findViewById(R.id.buttonLogout);
        imageView8 = findViewById(R.id.imageView8);
        buttonAnimalCreate1 = findViewById(R.id.buttonPlus);
        mapView = findViewById(R.id.smallMapView);
       // showAllAnimalsButton = findViewById(R.id.showAllAnimalsButton);

        mapView.onCreate(savedInstanceState);
        rootView = findViewById(android.R.id.content);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mapView.onCreate(mapViewBundle);


        imageView8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });



/*
        Button showProfilesButton = findViewById(R.id.showProfilesButton);
        showProfilesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalProfiles();
            }
        });
*/


        buttonLogout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttonAnimalCreate1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnimalProfiles.class);
                startActivity(intent);
                finish();
            }
        });



        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLargeMapView();
            }
        });


/*
        showAllAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllAnimalLocationsOnMap();
            }
        });
*/

        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLargeMapView();
                showAllAnimalLocationsOnMap(); // Küçük harita tıklandığında işaretleri göster
            }
        });



    }








    private void showLargeMapView() {
        if (largeMapView != null) {
            if (largeMapView.getVisibility() == View.VISIBLE) {
                largeMapView.setVisibility(View.GONE);
            } else {
                largeMapView.setVisibility(View.VISIBLE);
                largeMapView.onResume();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.largeMapView);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            // Büyük harita hazır olduğunda yapılması gereken işlemler
                        }
                    });
                }
                showAllAnimalLocationsOnMap(); // Büyük harita açıldığında işaretleri göster
            }
        }
    }

    private void showAllAnimalLocationsOnMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Tüm hayvanların konumlarını göstermek için gereken kodu burada kullanın.
                for (PetModel pet : petModelArrayList) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(pet.getLatitude()), Double.parseDouble(pet.getLongitude())))
                            .title(pet.getName());
                    googleMap.addMarker(markerOptions);
                }
            }
        });


/*
        showAllAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllAnimalLocationsOnMap();
            }
        });



        showAllAnimalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllAnimalLocationsOnMap();
            }
        });
*/



    }




    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://myapplicationanimalproject-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        mDatabase.child("pets").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) return;
                ArrayList<PetModel> list = new ArrayList<>();
                HashMap<String, Object> mapList = (HashMap<String, Object>) dataSnapshot.getValue();
                System.out.println("@@@??????II $"+mapList.values());

                for (Object animalProfiles : mapList.values()) {
                    if (animalProfiles != null) {
                        HashMap<String, String> map = ((HashMap<String, String>) animalProfiles);
//                        if(map != null) {
                        String name = map.get("name");
                        if (name == null)
                            name = map.get("Aname");

                        String pic = map.get("picture_name");
                        if(pic == null)
                            pic = map.get("pictureUrl");
                        String username = map.get("username");
                        if(username == null)
                            username = "No one";

                        String id = map.get("id");
                        if(id == null)
                            id = "";

                        String latitude = map.get("id");
                        if(latitude == null)
                            latitude = "";

                        String longitude = map.get("id");
                        if(longitude == null)
                            longitude = "";


                        PetModel petModel = new PetModel(
                                name,
                                map.get("age").toString(),
                                map.get("gender").toString(),
                                map.get("type").toString(),
                                map.get("address").toString(),
                                pic,
                                username,
                                id,
                                latitude,
                                longitude
                        );
                        list.add(petModel);
//                        }
                    }

                }

                petModelArrayList = list;

                PetsAdapter petsAdapter = new PetsAdapter(HomeActivity.this);
                petsAdapter.addPets(list);
                rv.setAdapter(petsAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("@@@@@??????ERROR " + e.getLocalizedMessage());
            }
        });




        mapView.onResume();
        if (largeMapView != null) {
            largeMapView.onResume();
        }


    }

    @Override
    public void onPetClickListener(PetModel petModel, ArrayList<PetModel> petModelArrayList) {
        Intent intent = new Intent(this, PetProfileActivity.class);
        intent.putParcelableArrayListExtra("list", petModelArrayList);
        intent.putExtra("pet", petModel);
        startActivity(intent);
    }







    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        if (largeMapView != null) {
            largeMapView.onPause();
        }
        // Diğer onPause işlemleri burada devam eder
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (largeMapView != null) {
            largeMapView.onDestroy();
        }
        // Diğer onDestroy işlemleri burada devam eder
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        if (largeMapView != null) {
            largeMapView.onLowMemory();
        }
        // Diğer onLowMemory işlemleri burada devam eder
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle("MapViewBundleKey");
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle("MapViewBundleKey", mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }







}


