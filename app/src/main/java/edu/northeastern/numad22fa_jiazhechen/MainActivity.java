package edu.northeastern.numad22fa_jiazhechen;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView helloTextView = findViewById(R.id.hello);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.aboutmeButton) {
            Toast.makeText(getApplicationContext(),
                    "Jiazhe Chen\nchen.jiazhe@northeastern.edu",
                    Toast.LENGTH_SHORT).show();
        } else if (theId == R.id.clickyClickyButton) {
            Intent clickyIntent = new Intent(this, ClickyClickyActivity.class);
            startActivity(clickyIntent);
        }
    }
}