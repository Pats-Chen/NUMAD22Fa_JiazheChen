package edu.northeastern.numad22fa_jiazhechen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMeButton = findViewById(R.id.aboutMeButton);
        aboutMeButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AboutMeActivity.class)));

        Button clickyClickyButton = findViewById(R.id.clickyClickyButton);
        clickyClickyButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ClickyClickyActivity.class)));

        Button linkCollectorButton = findViewById(R.id.linkCollectorButton);
        linkCollectorButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LinkCollectorActivity.class)));

        Button primeFinderButton = findViewById(R.id.primeFinderButton);
        primeFinderButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PrimeFinderActivity.class)));
    }
}