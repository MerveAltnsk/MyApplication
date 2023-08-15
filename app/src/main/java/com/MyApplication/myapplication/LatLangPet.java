package com.MyApplication.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class LatLangPet {
    LatLng latLng;
    String address;
    String name;

    public LatLangPet(LatLng latLng, String address, String name) {
        this.latLng = latLng;
        this.address = address;
        this.name = name;
    }
}
