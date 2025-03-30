package com.example.laundrip;

import android.content.Context;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GuideDetailsFragment extends Fragment {
    private static final String ARG_ITEM = "item";

    private String fabricName;

    public static GuideDetailsFragment newInstance(String item) {
        GuideDetailsFragment fragment = new GuideDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fabricName = getArguments().getString(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_details, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<Fabric> fabricList = getFabricDetails(requireContext(), fabricName);

        FabricAdapter adapter = new FabricAdapter(fabricList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.recyclerView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        return view;
    }

    private List<Fabric> getFabricDetails(Context context, String fabricName) {
        List<Fabric> fabricList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open("Laundry_Guide.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return fabricList;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray fabricsArray = jsonObject.getJSONArray("fabrics");

            for (int i = 0; i < fabricsArray.length(); i++) {
                JSONObject fabricJson = fabricsArray.getJSONObject(i);
                if (fabricJson.getString("name").equalsIgnoreCase(fabricName)) {
                    Fabric fabric = new Fabric();
                    fabric.setName(fabricJson.getString("name"));
                    // Set other properties of Fabric from JSON
                    JSONObject washingInstructionsJson = fabricJson.getJSONObject("washing_instructions");
                    WashingInstructions washingInstructions = new WashingInstructions();
                    washingInstructions.setMachine_washable(washingInstructionsJson.getString("machine_washable"));
                    washingInstructions.setWater_temperature(washingInstructionsJson.getString("water_temperature"));
                    washingInstructions.setCycle_type(washingInstructionsJson.getString("cycle_type"));
                    washingInstructions.setRecommended_detergent(washingInstructionsJson.getString("recommended_detergent"));
                    washingInstructions.setUse_fabric_softener(washingInstructionsJson.getString("use_fabric_softener"));
                    washingInstructions.setCan_be_bleached(washingInstructionsJson.getString("can_be_bleached"));
                    fabric.setWashing_instructions(washingInstructions);

                    JSONObject dryingInstructionsJson = fabricJson.getJSONObject("drying_instructions");
                    DryingInstructions dryingInstructions = new DryingInstructions();
                    dryingInstructions.setAir_dry_or_machine_dry(dryingInstructionsJson.getString("air_dry_or_machine_dry"));
                    dryingInstructions.setHeat_settings(dryingInstructionsJson.getString("heat_settings"));
                    dryingInstructions.setSpecial_handling(dryingInstructionsJson.getString("special_handling"));
                    fabric.setDrying_instructions(dryingInstructions);

                    JSONObject ironingMaintenanceJson = fabricJson.getJSONObject("ironing_maintenance");
                    IroningMaintenance ironingMaintenance = new IroningMaintenance();
                    ironingMaintenance.setIron_temperature(ironingMaintenanceJson.getString("iron_temperature"));
                    ironingMaintenance.setSteam(ironingMaintenanceJson.getString("steam"));
                    ironingMaintenance.setPrevent_shrinking_fading(ironingMaintenanceJson.getString("prevent_shrinking_fading"));
                    fabric.setIroning_maintenance(ironingMaintenance);

                    JSONObject stainRemovalTipsJson = fabricJson.getJSONObject("stain_removal_tips");
                    StainRemovalTips stainRemovalTips = new StainRemovalTips();
                    JSONArray commonStainsArray = stainRemovalTipsJson.getJSONArray("common_stains");
                    List<String> commonStains = new ArrayList<>();
                    for (int j = 0; j < commonStainsArray.length(); j++) {
                        commonStains.add(commonStainsArray.getString(j));
                    }
                    stainRemovalTips.setCommon_stains(String.valueOf(commonStains));
                    stainRemovalTips.setBest_removal_techniques(stainRemovalTipsJson.getString("best_removal_techniques"));
                    fabric.setStain_removal_tips(stainRemovalTips);

                    JSONObject storageTipsJson = fabricJson.getJSONObject("storage_tips");
                    StorageTips storageTips = new StorageTips();
                    storageTips.setSpecial_storage_tips(storageTipsJson.getString("fold_or_hang"));
                    storageTips.setSpecial_storage_tips(storageTipsJson.getString("special_storage_tips"));
                    fabric.setStorage_tips(storageTips);

                    fabricList.add(fabric);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fabricList;
    }
}