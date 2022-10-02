package edu.northeastern.numad22fa_jiazhechen;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutMeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView aboutMeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        aboutMeTextView = findViewById(R.id.aboutMeTextView);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.aboutMeButton) {
            aboutMeTextView.setText(R.string.about_me_text);
        }
    }
}
