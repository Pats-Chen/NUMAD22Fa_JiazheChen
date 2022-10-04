package edu.northeastern.numad22fa_jiazhechen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {
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

//            Intent intent = new Intent(LinkCollectorActivity.this, AddActivity.class);
//            startActivity(intent)

        linkName = new ArrayList<>();
        linkString = new ArrayList<>();
        linkName.add("Google");
        linkString.add("www.google.com");
        linkName.add("Facebook");
        linkString.add("www.facebook.com");

//        storeDataInArrays();

        linkAdapter = new LinkAdapter(LinkCollectorActivity.this, linkName, linkString);
        recyclerView.setAdapter(linkAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(LinkCollectorActivity.this));
    }

    private void openDialog() {
        LinkInputFragment linkInputFragment = new LinkInputFragment();
        linkInputFragment.show(getSupportFragmentManager(), "USER INPUT FRAGMENT");
    }
}
