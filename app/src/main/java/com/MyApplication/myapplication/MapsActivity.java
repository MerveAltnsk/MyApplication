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

    // below are the latitude and longitude
    // of 4 different locations.
    LatLng sydney = new LatLng(-34, 151);
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    PetModel pet;
    // creating array list for adding all our locations.
    private ArrayList<LatLangPet> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pet = getIntent().getParcelableExtra("pet");
        ArrayList<PetModel> pets = getIntent().getParcelableArrayListExtra("list");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // inside on map ready method
        // we will be displaying all our markers.
        // for adding markers we are running for loop and
        // inside that we are drawing marker on our map.
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            MarkerOptions options = new MarkerOptions().position(locationArrayList.get(i).latLng).title(locationArrayList.get(i).name);
            System.out.println("@@@@@LLLL "+locationArrayList.get(i).address + " = " + pet.address);
            if (locationArrayList.get(i).address.equals(pet.address)){
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }else{
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            mMap.addMarker(options);

            // below line is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i).latLng));


        }
    }
}