package com.example.laundrip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WasherAdapter extends RecyclerView.Adapter<WasherAdapter.ViewHolder> {

    private List<users> washerList;

    public WasherAdapter(List<users> washerList) {
        this.washerList = washerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_washer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        users washer = washerList.get(position);
        holder.nameTextView.setText(washer.getName());
        holder.addressTextView.setText(washer.getAddress());
    }

    @Override
    public int getItemCount() {
        return washerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, addressTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
        }
    }
}