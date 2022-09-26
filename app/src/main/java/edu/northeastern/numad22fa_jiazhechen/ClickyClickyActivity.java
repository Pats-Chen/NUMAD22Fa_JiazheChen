package edu.northeastern.numad22fa_jiazhechen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClickyClickyActivity extends AppCompatActivity implements View.OnClickListener {
    TextView pressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        pressTextView = findViewById(R.id.pressTextView);
    }

    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.aButton) {
            pressTextView.setText(R.string.press_a);
        } else if (theId == R.id.bButton) {
            pressTextView.setText(R.string.press_b);
        } else if (theId == R.id.cButton) {
            pressTextView.setText(R.string.press_c);
        } else if (theId == R.id.dButton) {
            pressTextView.setText(R.string.press_d);
        } else if (theId == R.id.eButton) {
            pressTextView.setText(R.string.press_e);
        } else if (theId == R.id.fButton) {
            pressTextView.setText(R.string.press_f);
        }
    }
}