package edu.northeastern.numad22fa_jiazhechen;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView aboutmeText;

    public void proc() {
        Button aboutmeButton = (Button) findViewById(R.id.aboutmeButton);
        aboutmeText = (TextView) findViewById(R.id.aboutmeText);

        aboutmeButton.setOnClickListener(view -> aboutmeText.setText(R.string.aboutmeText));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        proc();
    }
}