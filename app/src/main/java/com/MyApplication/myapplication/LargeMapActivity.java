package com.MyApplication.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LargeMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
        }

        mapView = findViewById(R.id.largeMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Büyük haritada işaretçi oluştur
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Selected Location"));

        // Kamerayı seçilen yere hareket ettir
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(location)
                .zoom(15)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Harita yaşam döngüsünü yönetmek için gerekli onResume çağrısını yapın
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Harita yaşam döngüsünü yönetmek için gerekli onPause çağrısını yapın
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // Harita yaşam döngüsünü yönetmek için gerekli onLowMemory çağrısını yapın
        mapView.onLowMemory();
    }
}