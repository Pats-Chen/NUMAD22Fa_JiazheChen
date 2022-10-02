package edu.northeastern.numad22fa_jiazhechen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final List<Link> links;
    private final Context context;

    public LinkAdapter(List<Link> links, Context context) {
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_link, null));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        holder.name.setText(links.get(position).getName());
        holder.url.setText(links.get(position).getUrl());
        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(context, links.get(position).getUrl(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }
}
