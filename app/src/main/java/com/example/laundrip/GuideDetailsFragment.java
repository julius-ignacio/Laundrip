package com.example.laundrip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideDetailsFragment extends Fragment {
    private static final String ARG_ITEM = "item";

    public static GuideDetailsFragment newInstance(String item) {
        GuideDetailsFragment fragment = new GuideDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_details, container, false);

        TextView textView = view.findViewById(R.id.textView);
        if (getArguments() != null) {
            String item = getArguments().getString(ARG_ITEM);
            textView.setText("Details about " + item);
        }


        return view;
    }
}