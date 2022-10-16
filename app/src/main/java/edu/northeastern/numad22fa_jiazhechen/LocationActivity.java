package edu.northeastern.numad22fa_jiazhechen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;

public class LocationActivity extends AppCompatActivity {
    TextView locationTextView;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.locationTextView);

        Button restDistanceButton = findViewById(R.id.resetDistanceButton);
//        restDistanceButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AboutMeActivity.class)));
    }
}
