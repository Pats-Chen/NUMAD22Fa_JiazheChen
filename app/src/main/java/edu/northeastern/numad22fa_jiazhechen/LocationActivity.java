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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.locationTextView);
        lastLocation = null;
        totalDistance = 0.0f;

        mainHandler = new Handler(Looper.getMainLooper());

        Button restDistanceButton = findViewById(R.id.resetDistanceButton);
        restDistanceButton.setOnClickListener(view -> mainHandler.post(() -> {
            totalDistance = 0.0f;
            locationTextView.setText("Latitude: -\nLongitude: -\nTotal Distance: -");
        }));

        locationRequestBuilder = new LocationRequest.Builder(60 * 1000);
        locationRequestBuilder.setMinUpdateIntervalMillis(2 * 1000);
        locationRequestBuilder.setPriority(PRIORITY_BALANCED_POWER_ACCURACY);

        updateLocation();
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
