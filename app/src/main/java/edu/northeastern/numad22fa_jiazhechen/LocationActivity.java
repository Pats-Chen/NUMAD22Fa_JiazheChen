package edu.northeastern.numad22fa_jiazhechen;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;

    TextView locationTextView;
    LocationRequest.Builder locationRequestBuilder;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location lastLocation;
    Location currentLocation;
    float totalDistance;
    Handler mainHandler;
    private long backPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.locationTextView);
        lastLocation = null;
        currentLocation = null;
        totalDistance = 0.0f;

        mainHandler = new Handler(Looper.getMainLooper());

        Button restDistanceButton = findViewById(R.id.resetDistanceButton);
        restDistanceButton.setOnClickListener(view -> mainHandler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                lastLocation = null;
                totalDistance = 0.0f;
                locationTextView.setText("Latitude: -\nLongitude: -\nTotal Distance: " + totalDistance);
            }
        }));

        locationRequestBuilder = new LocationRequest.Builder(60 * 1000);
        locationRequestBuilder.setMinUpdateIntervalMillis(2 * 1000);
        locationRequestBuilder.setPriority(PRIORITY_BALANCED_POWER_ACCURACY);

        updateLocation();
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Last Location", lastLocation);
        outState.putParcelable("Current Location", currentLocation);
        outState.putFloat("Total Distance", totalDistance);
        Log.d(TAG, "onSaveInstanceState: " + totalDistance);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastLocation = savedInstanceState.getParcelable("Last Location");
        currentLocation = savedInstanceState.getParcelable("Current Location");
        totalDistance = savedInstanceState.getFloat("Total Distance");
        Log.d(TAG, "onRestoreInstanceState: " + totalDistance);
        mainHandler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                locationTextView.setText("Latitude: " + currentLocation.getLatitude() + "\nLongitude: " + currentLocation.getLongitude() + "\nTotal Distance: " + totalDistance);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            lastLocation = null;
            currentLocation = null;
            totalDistance = 0.0f;
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press Back Again to Reset Total Distance & Leave!", Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void updateLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, null).addOnSuccessListener(this, location -> {
                Log.d(TAG, "updateLocation: latitude = " + location.getLatitude());
                Log.d(TAG, "updateLocation: longitude = " + location.getLongitude());
                if (lastLocation == null) {
                    lastLocation = location;
                    currentLocation = location;
                    totalDistance = 0.0f;
                    mainHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            totalDistance = 0.0f;
                            locationTextView.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude() + "\nTotal Distance: " + totalDistance);
                        }
                    });
                } else {
                    lastLocation = currentLocation;
                    currentLocation = location;
                    float[] distance = new float[1];
                    Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                            currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
                    totalDistance += distance[0];
                    mainHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            locationTextView.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude() + "\nTotal Distance: " + totalDistance);
                        }
                    });
                }
            });

        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
        }
    }
}
