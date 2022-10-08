package edu.northeastern.numad22fa_jiazhechen;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrimeFinderActivity extends AppCompatActivity {
    TextView primeNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_finder);
        primeNumberTextView = findViewById(R.id.aboutMeTextView);
    }
}
