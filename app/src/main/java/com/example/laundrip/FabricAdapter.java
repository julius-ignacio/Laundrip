package com.example.laundrip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FabricAdapter extends RecyclerView.Adapter<FabricAdapter.ViewHolder> {
    private List<Fabric> fabricList;

    public FabricAdapter(List<Fabric> fabricList) {
        this.fabricList = fabricList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fabric, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fabric fabric = fabricList.get(position);
        holder.nameTextView.setText(fabric.getName());
        holder.washingTextView.setText(fabric.getDryingInstructions().getHeatSettings());
        holder.dryingTextView.setText("Dry: " + fabric.getDryingInstructions().getHeatSettings());
    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, washingTextView, dryingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            washingTextView = itemView.findViewById(R.id.washingTextView);
            dryingTextView = itemView.findViewById(R.id.dryingTextView);
        }
    }
}