package edu.northeastern.numad22fa_jiazhechen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class LinkUserInputActivity extends AppCompatActivity implements View.OnClickListener {
    String name;
    String url;
    EditText nameUserInput;
    EditText urlUserInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_user_input);

        name = nameUserInput.getText().toString();
        url = urlUserInput.getText().toString();

        nameUserInput = findViewById(R.id.nameUserInput);
        urlUserInput = findViewById(R.id.urlUserInput);

    }

    @Override
    public void onClick(View view) {
        int theId = view.getId();
        if (theId == R.id.submitButton) {
            Bundle userInputBundle = new Bundle();
            userInputBundle.putString("NAME", name);
            userInputBundle.putString("URL", url);
            Intent linkCollectorIntent = new Intent(this, LinkCollectorActivity.class);
            linkCollectorIntent.putExtras(userInputBundle);
            Snackbar.make(view, "User Input submitted successfully.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            startActivity(linkCollectorIntent);
        }
    }
}
