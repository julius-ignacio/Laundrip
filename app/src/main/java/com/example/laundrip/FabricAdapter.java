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

        holder.machineWashableTextView.setText(fabric.getWashingInstructions().getMachine_washable());
        holder.waterTemperatureTextView.setText(fabric.getWashingInstructions().getWaterTemperature());
        holder.cycleTypeTextView.setText(fabric.getWashingInstructions().getCycleType());
        holder.recommendedDetergentTextView.setText(fabric.getWashingInstructions().getRecommendedDetergent());
        holder.useFabricSoftenerTextView.setText(fabric.getWashingInstructions().getUse_fabric_softener());
        holder.canBeBleachedTextView.setText(fabric.getWashingInstructions().getCan_be_bleached());

        holder.airDryorMachineDryTextView.setText(fabric.getDryingInstructions().getAir_dry_or_machine_dry());
        holder.heatSettingsTextView.setText(fabric.getDryingInstructions().getHeatSettings());
        holder.specialHandlingTextView.setText(fabric.getDryingInstructions().getSpecialHandling());


        holder.ironTemperatureTextView.setText(fabric.getIroningMaintenance().getIronTemperature());
        holder.steamTextView.setText(fabric.getIroningMaintenance().getSteam());
        holder.preventShrinkingTextView.setText(fabric.getIroningMaintenance().getPrevent_shrinking_fading());


        holder.commonStainsTextView.setText(fabric.getStainRemovalTips().getCommonStains());
        holder.bestRemovalTechniquesTextView.setText(fabric.getStainRemovalTips().getBest_removal_techniques());

        holder.specialStorageTipsTextView.setText(fabric.getStorageTips().getSpecial_storage_tips());
        holder.foldOrHangTextView.setText(fabric.getStorageTips().getFoldOrHang());

    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, machineWashableTextView,
        waterTemperatureTextView, cycleTypeTextView, recommendedDetergentTextView,
        useFabricSoftenerTextView, canBeBleachedTextView, airDryorMachineDryTextView, heatSettingsTextView,
        specialHandlingTextView,
        ironTemperatureTextView, steamTextView, preventShrinkingTextView, commonStainsTextView,
        bestRemovalTechniquesTextView, foldOrHangTextView, specialStorageTipsTextView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            machineWashableTextView = itemView.findViewById(R.id.machineWashableTextView);

            nameTextView = itemView.findViewById(R.id.nameTextView);

            machineWashableTextView = itemView.findViewById(R.id.machineWashableTextView);
            waterTemperatureTextView = itemView.findViewById(R.id.waterTemperatureTextView);
            cycleTypeTextView = itemView.findViewById(R.id.cycleTypeTextView);
            recommendedDetergentTextView = itemView.findViewById(R.id.recommendedDetergentTextView);
            useFabricSoftenerTextView = itemView.findViewById(R.id.useFabricSoftenerTextView);
            canBeBleachedTextView = itemView.findViewById(R.id.canBeBleachedTextView);



            airDryorMachineDryTextView = itemView.findViewById(R.id.airDryOrMachineDryTextView);
            heatSettingsTextView = itemView.findViewById(R.id.heatSettingsTextView);
            specialHandlingTextView = itemView.findViewById(R.id.specialHandlingTextView);




            ironTemperatureTextView = itemView.findViewById(R.id.ironTemperatureTextView);
            steamTextView = itemView.findViewById(R.id.steamTextView);
            preventShrinkingTextView = itemView.findViewById(R.id.preventShrinkingTextView);


            commonStainsTextView = itemView.findViewById(R.id.commonStainsTextView);
            bestRemovalTechniquesTextView = itemView.findViewById(R.id.bestRemovalTechniquesTextView);


            foldOrHangTextView = itemView.findViewById(R.id.foldOrHangTextView);
            specialStorageTipsTextView = itemView.findViewById(R.id.specialStorageTipsTextView);
        }
    }
}