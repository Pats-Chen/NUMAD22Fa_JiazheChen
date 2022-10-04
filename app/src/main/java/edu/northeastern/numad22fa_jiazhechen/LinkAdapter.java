package edu.northeastern.numad22fa_jiazhechen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {
    private Context context;
    private ArrayList<String> linkNameList;
    private ArrayList<String> linkStringList;

    LinkAdapter(Context context, ArrayList<String> linkNameList, ArrayList<String> linkStringList) {
        this.context = context;
        this.linkNameList = linkNameList;
        this.linkStringList = linkStringList;
    }


    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        holder.linkName.setText(String.valueOf(linkNameList.get(position)));
        holder.linkString.setText(String.valueOf(linkStringList.get(position)));


    }

    @Override
    public int getItemCount() {
        return linkNameList.size();
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView linkName;
        TextView linkString;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            linkName = itemView.findViewById(R.id.linkName);
            linkString = itemView.findViewById(R.id.linkString);
        }
    }
}
