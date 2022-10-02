package edu.northeastern.numad22fa_jiazhechen;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.aboutMeButton) {
            Intent aboutMeIntent = new Intent(this, AboutMeActivity.class);
            startActivity(aboutMeIntent);
            //import android.widget.Toast;
            // Toast.makeText(getApplicationContext(),
            // R.string.aboutmeText,
            // Toast.LENGTH_SHORT).show();
        } else if (theId == R.id.clickyClickyButton) {
            Intent clickyIntent = new Intent(this, ClickyClickyActivity.class);
            startActivity(clickyIntent);
        } else if (theId == R.id.linkCollectorButton) {
            Intent linkCollectorIntent = new Intent(this, LinkCollectorActivity.class);
            startActivity(linkCollectorIntent);
        } else if (theId == R.id.fab) {
            Intent linkUserInputIntent = new Intent(this, LinkUserInputActivity.class);
            startActivity(linkUserInputIntent);
        }
    }
}