package com.MyApplication.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PetModel implements Parcelable {

    private String latitude;
    private String longitude;



    String name;
    String age;
    String gender;
    String type;
    String address;
    String pictureUrl;
    String adoptedUser;
    String id;

    public PetModel(String name, String age, String gender, String type, String address, String pictureUrl, String adoptedUser, String id, String latitude,String longitude) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.address = address;
        this.pictureUrl = pictureUrl;
        this.adoptedUser = adoptedUser;
        this.id = id;

        this.latitude = latitude;
        this.longitude = longitude;
    }


    protected PetModel(Parcel in) {
        name = in.readString();
        age = in.readString();
        gender = in.readString();
        type = in.readString();
        address = in.readString();
        pictureUrl = in.readString();
        adoptedUser = in.readString();
        id = in.readString();

        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<PetModel> CREATOR = new Creator<PetModel>() {
        @Override
        public PetModel createFromParcel(Parcel in) {
            return new PetModel(in);
        }

        @Override
        public PetModel[] newArray(int size) {
            return new PetModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(type);
        parcel.writeString(address);
        parcel.writeString(pictureUrl);
        parcel.writeString(adoptedUser);
        parcel.writeString(id);

        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }
}
