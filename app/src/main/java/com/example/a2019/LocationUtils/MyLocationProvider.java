package com.example.a2019.LocationUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.security.Provider;
import java.util.List;

public class MyLocationProvider {

    LocationManager locationManager;
    Location location;
    boolean canGetLocation;
    Context context;
    LocationListener locationListener;
    public final int MIN_TIME_BETWEEN_UPDATES=5*1000;
    public final int MIN_DIS_UPDATES=5;


    public MyLocationProvider(Context context,LocationListener locationListener) {
        this.context = context;
        this.locationListener=locationListener;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        location = null;
    }

    @SuppressLint("MissingPermission")
    public Location getCurrentLocation() {
        String provider = null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = locationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = locationManager.NETWORK_PROVIDER;
        }

        if (provider == null) {
            canGetLocation = false;
            return null;
        }
        canGetLocation=true;

      location=  locationManager.getLastKnownLocation(provider);
      if (locationListener!=null){
          locationManager.
                  requestLocationUpdates(provider
                          ,MIN_TIME_BETWEEN_UPDATES,
                          MIN_DIS_UPDATES
                          ,locationListener);
      }
      if (location==null){
        location=getBestLastKnewnLocataion();
      }
      return location;
    }
    @SuppressLint("MissingPermission")
   Location getBestLastKnewnLocataion(){
        Location bestLocation=null;
        List<String>Providers=
        locationManager.getProviders(true);
        for (String provider:Providers) {
            Location l=locationManager.getLastKnownLocation(provider);

            if (bestLocation==null&&l!=null){
                bestLocation=l;
                continue;
            }else if (l!=null&&bestLocation!=null &&bestLocation.getAccuracy()<l.getAccuracy()){
                bestLocation=l;
            }

        }
        return bestLocation;
    }
}
