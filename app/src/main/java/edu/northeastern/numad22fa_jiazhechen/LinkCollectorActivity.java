package edu.northeastern.numad22fa_jiazhechen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {
    RecyclerView linkRecyclerView;
    List<Link> linkList;
    Bundle userInputBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        linkList = new ArrayList<>();

        userInputBundle = getIntent().getExtras();
        linkList.add(new Link(userInputBundle.getString("NAME")
                , userInputBundle.getString("URL")));

        linkList.add(new Link("Google", "www.google.com"));

        linkRecyclerView = findViewById(R.id.linkRecyclerView);
        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkRecyclerView.setAdapter(new LinkAdapter(linkList, this));
    }
}
