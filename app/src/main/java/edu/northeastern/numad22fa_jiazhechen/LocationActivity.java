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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int PRIORITY_HIGH_ACCURACY = 100;

    Handler mainHandler;
    TextView locationTextView;
    LocationRequest.Builder locationRequestBuilder;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location lastLocation;
    Location currentLocation;
    float totalDistance;
    private long backPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mainHandler = new Handler(Looper.getMainLooper());
        lastLocation = null;
        currentLocation = null;
        totalDistance = 0.0f;

        locationTextView = findViewById(R.id.locationTextView);

        Button restDistanceButton = findViewById(R.id.resetDistanceButton);
        restDistanceButton.setOnClickListener(view -> {
            lastLocation = null;
            currentLocation = null;
            totalDistance = 0.0f;
            mainHandler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                locationTextView.setText("Latitude: -\nLongitude: -\nTotal Distance: " + totalDistance);
            }
        });
        });

        locationRequestBuilder = new LocationRequest.Builder(10 * 1000);
        locationRequestBuilder.setMinUpdateIntervalMillis(2 * 1000);
        locationRequestBuilder.setPriority(PRIORITY_HIGH_ACCURACY);
        locationRequest = locationRequestBuilder.build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (lastLocation == null) {
                    lastLocation = locationResult.getLastLocation();
                    currentLocation = locationResult.getLastLocation();
                    totalDistance = 0.0f;
                    if (currentLocation != null) {
                        updateUI(currentLocation.getLatitude(), currentLocation.getLongitude(), totalDistance);
                    } else {
                        mainHandler.post(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                locationTextView.setText("Latitude: -\nLongitude: -\nTotal Distance: " + totalDistance);
                            }
                        });
                    }

                } else {
                    lastLocation = currentLocation;
                    currentLocation = locationResult.getLastLocation();
                    float[] distance = new float[1];
                    Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                            currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
                    totalDistance += distance[0];
                    updateUI(currentLocation.getLatitude(), currentLocation.getLongitude(), totalDistance);
                }
            }
        };

        updateLocation();
        startLocationUpdates();
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
        updateUI(currentLocation.getLatitude(), currentLocation.getLongitude(), totalDistance);
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            lastLocation = null;
            currentLocation = null;
            totalDistance = 0.0f;
            stopLocationUpdates();
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press Back Again to Reset Total Distance and Leave!", Toast.LENGTH_SHORT).show();
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

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        updateLocation();
    }

    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public void updateLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
                Log.d(TAG, "updateLocation: latitude = " + location.getLatitude());
                Log.d(TAG, "updateLocation: longitude = " + location.getLongitude());
                if (lastLocation == null) {
                    lastLocation = location;
                    currentLocation = location;
                    totalDistance = 0.0f;
                    updateUI(location.getLatitude(), location.getLongitude(), totalDistance);
                } else {
                    lastLocation = currentLocation;
                    currentLocation = location;
                    float[] distance = new float[1];
                    Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(),
                            currentLocation.getLatitude(), currentLocation.getLongitude(), distance);
                    totalDistance += distance[0];
                    updateUI(location.getLatitude(), location.getLongitude(), totalDistance);
                }
            });
        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
        }
    }

    public void updateUI(double latitude, double longitude, float totalDistance) {
        mainHandler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                locationTextView.setText("Latitude: " + latitude + "\nLongitude: " + longitude + "\nTotal Distance: " + totalDistance);
            }
        });
    }
}
