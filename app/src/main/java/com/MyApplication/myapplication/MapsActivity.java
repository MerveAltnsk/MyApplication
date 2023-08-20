package com.MyApplication.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng sydney = new LatLng(-34, 151);
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    PetModel pet;
    private ArrayList<LatLangPet> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pet = getIntent().getParcelableExtra("pet");
        ArrayList<PetModel> pets = getIntent().getParcelableArrayListExtra("list");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationArrayList = new ArrayList<>();

        System.out.println("@@@@@OOOO "+pets.size());

        for (PetModel petModel : pets) {
            try {
                if(petModel.address.contains(",")){
                    String[] parts = petModel.address.split(",");
                    System.out.println("@@@@@@???IIIII "+ parts[0]);
                    String lat = petModel.address.split(",")[0].trim();
                    String lang = petModel.address.split(",")[1].trim();

                    locationArrayList.add(new LatLangPet(new LatLng(Double.parseDouble(lat), Double.parseDouble(lang)), petModel.address, petModel.name));
                }
            } catch (Exception e) {
                System.out.println("@@@@??? can't convert pet: " + petModel.name + " : " + e.getMessage());
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < locationArrayList.size(); i++) {

            MarkerOptions options = new MarkerOptions().position(locationArrayList.get(i).latLng).title(locationArrayList.get(i).name);
            System.out.println("@@@@@LLLL "+locationArrayList.get(i).address + " = " + pet.address);
            if (locationArrayList.get(i).address.equals(pet.address)){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }else{
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            mMap.addMarker(options);

            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i).latLng));


        }
    }
}