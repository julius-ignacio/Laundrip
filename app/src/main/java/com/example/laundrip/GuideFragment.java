package com.example.laundrip;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        view.findViewById(R.id.btn_cotton).setOnClickListener(v -> loadFabricData("Cotton"));
        view.findViewById(R.id.btn_linen).setOnClickListener(v -> loadFabricData("Linen"));
        view.findViewById(R.id.btn_wool).setOnClickListener(v -> loadFabricData("Wool"));
        view.findViewById(R.id.btn_silk).setOnClickListener(v -> loadFabricData("Silk"));
        view.findViewById(R.id.btn_denim).setOnClickListener(v -> loadFabricData("Denim"));
        view.findViewById(R.id.btn_synthetic).setOnClickListener(v -> loadFabricData("Synthetic Fabrics"));
        view.findViewById(R.id.btn_delicates).setOnClickListener(v -> loadFabricData("Delicates & Lingerie"));
        view.findViewById(R.id.btn_athletic).setOnClickListener(v -> loadFabricData("Athletic Wear"));
        view.findViewById(R.id.btn_heavy).setOnClickListener(v -> loadFabricData("Heavy Fabrics"));
        view.findViewById(R.id.btn_mixed).setOnClickListener(v -> loadFabricData("Mixed Fabrics & Blends"));

        return view;
    }


    private String loadJsonFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("Laundry_Guide.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private JSONObject getFabricData(String fabricName) {
        String jsonString = loadJsonFromAssets();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray fabricsArray = jsonObject.getJSONArray("fabrics");

            for (int i = 0; i < fabricsArray.length(); i++) {
                JSONObject fabric = fabricsArray.getJSONObject(i);
                if (fabric.getString("name").equalsIgnoreCase(fabricName)) {
                    return fabric;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void loadFabricData(String fabricName) {
        JSONObject fabricData = getFabricData(fabricName);
        if (fabricData != null) {
            // Update your UI here with the fabricData
            // For example, you can update TextViews with washing instructions, drying instructions, etc.
            try {
                String washingInstructions = fabricData.getJSONObject("washing_instructions").toString();
                String dryingInstructions = fabricData.getJSONObject("drying_instructions").toString();
                // Update your TextViews or other UI elements with these instructions
                Log.d(TAG, "Washing Instructions: " + washingInstructions);
                Log.d(TAG, "Drying Instructions: " + dryingInstructions);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}