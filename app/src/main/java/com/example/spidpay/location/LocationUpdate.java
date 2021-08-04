package com.example.spidpay.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class LocationUpdate extends LiveData<LocationModel> {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    Context context;

    public LocationUpdate(Context context) {
        this.context = context;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        createLocationRequest();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NotNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                getAddress(location);
            }
        }
    };




    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        super.onActive();
        startLocationUpdate();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        stoplocationUpdate();
    }

    public void stoplocationUpdate() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdate() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    protected LocationRequest createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        return locationRequest;
    }

    public void getAddress(Location location) {
        Geocoder geocoder;
        String city = "";
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            city = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();

        }


        LocationModel locationModel = new LocationModel();
        locationModel.latitude = location.getLatitude();
        locationModel.longitude = location.getLongitude();
        locationModel.city = city;
        setValue(locationModel);
    }


}
