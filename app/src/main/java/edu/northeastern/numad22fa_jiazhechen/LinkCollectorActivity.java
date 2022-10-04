package edu.northeastern.numad22fa_jiazhechen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity implements LinkInputFragment.LinkDialogListener, RecyclerViewInterface {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ArrayList<String> linkName;
    ArrayList<String> linkString;
    LinkAdapter linkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> openDialog());

        linkName = new ArrayList<>();
        linkString = new ArrayList<>();

        linkAdapter = new LinkAdapter(LinkCollectorActivity.this, LinkCollectorActivity.this, linkName, linkString);
        recyclerView.setAdapter(linkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LinkCollectorActivity.this));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        for (int i = 0;i < linkName.size();i++) {
            savedInstanceState.putInt("LIST SIZE", linkName.size());
            savedInstanceState.putString("NAME NO" + i, linkName.get(i));
            savedInstanceState.putString("URL NO" + i, linkString.get(i));
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int listSize = savedInstanceState.getInt("LIST SIZE");
        for (int i = 0;i < listSize;i++) {
            linkName.add(savedInstanceState.getString("NAME NO" + i));
            linkString.add(savedInstanceState.getString("URL NO" + i));
        }
    }

    private void openDialog() {
        LinkInputFragment linkInputFragment = new LinkInputFragment();
        linkInputFragment.show(getSupportFragmentManager(), "USER INPUT FRAGMENT");
    }

    @Override
    public void sendInfo(String urlName, String urlString) {
        linkName.add(urlName);
        linkString.add(urlString);
        showSnackbar();
    }

    public void showSnackbar() {
        Snackbar successSnackbar = Snackbar.make(findViewById(R.id.linkCollectorLayout), "The link was successfully created!", Snackbar.LENGTH_SHORT);
        successSnackbar.show();

    }

    @Override
    public void onLinkClick(int position) {
        String urlString = linkAdapter.getLink(position);
        if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
            urlString = "http://" + urlString;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        startActivity(browserIntent);
        Toast.makeText(getApplicationContext(), "Hello Browser!", Toast.LENGTH_SHORT).show();
    }
}
