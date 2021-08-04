package com.example.spidpay.location;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LocationViewModel extends AndroidViewModel {
    LocationUpdate locationUpdate;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationUpdate = new LocationUpdate(application);
    }

    public LiveData<LocationModel> getLocationUpdate() {
        return locationUpdate;
    }

    public void removeLocationUpdate() {
        locationUpdate.stoplocationUpdate();
    }

}
