package com.example.laundrip;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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

        TextView textView = view.findViewById(R.id.textView);
        String fabricDetails = getFabricDetails(requireContext(), fabricName);
        textView.setText(fabricDetails);

        return view;
    }

    private String getFabricDetails(Context context, String fabricName) {
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
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray fabricsArray = jsonObject.getJSONArray("fabrics");

            for (int i = 0; i < fabricsArray.length(); i++) {
                JSONObject fabric = fabricsArray.getJSONObject(i);
                if (fabric.getString("name").equalsIgnoreCase(fabricName)) {
                    return fabric.toString(4); // Pretty print JSON
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "No details found for " + fabricName;
    }
}