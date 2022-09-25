package edu.northeastern.numad22fa_jiazhechen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClickyClickyActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        TextView pressTextView = findViewById(R.id.pressTextView);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.aButton) {

        } else if (theId == R.id.bButton) {

        } else if (theId == R.id.cButton) {

        } else if (theId == R.id.dButton) {

        } else if (theId == R.id.eButton) {

        } else if (theId == R.id.fButton) {

        }
    }
}