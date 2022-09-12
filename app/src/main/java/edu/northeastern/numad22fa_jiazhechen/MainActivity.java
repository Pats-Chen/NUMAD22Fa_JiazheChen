package edu.northeastern.numad22fa_jiazhechen;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button aboutmeButton;
    TextView aboutmeText;
    String hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutmeButton = new Button(this);
        aboutmeText =new TextView(this);

        aboutmeButton = (Button)findViewById(R.id.aboutmeButton);
        aboutmeText=(TextView)findViewById(R.id.aboutmeText);

        hello = "Jiazhe Chen, chen.jiazhe@northeastern.edu";

        aboutmeButton.setOnClickListener((View.OnClickListener) this);
    }
    public void onClick(View view){
        aboutmeText.setText(hello);
    }
}