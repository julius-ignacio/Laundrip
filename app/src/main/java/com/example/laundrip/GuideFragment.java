package com.example.laundrip;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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




    Button cotton, linen, wool, silk, denim, synetheticFiber,
            delicates, athletic, heavyfab, mixedfab;


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

        cotton = view.findViewById(R.id.btn_cotton);
        linen = view.findViewById(R.id.btn_linen);
        wool = view.findViewById(R.id.btn_wool);
        silk = view.findViewById(R.id.btn_silk);
        denim = view.findViewById(R.id.btn_denim);
        synetheticFiber = view.findViewById(R.id.btn_synthetic);
        delicates = view.findViewById(R.id.btn_delicates);
        athletic = view.findViewById(R.id.btn_athletic);
        heavyfab = view.findViewById(R.id.btn_heavy);
        mixedfab = view.findViewById(R.id.btn_mixed);

        cotton.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to cotton page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("Cotton");
                });

        linen.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to linen page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("linen");
        });

        wool.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to wool page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("wool");
        });

        silk.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to silk page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("silk");
        });

        denim.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to denim page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("denim");
        });

        synetheticFiber.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to synetheticFiber page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("synetheticFiber");
        });

        delicates.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to delicates page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("delicates");
        });

        athletic.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to athletic page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("athletic");
        });

        heavyfab.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to heavyfab page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("heavyfab");
        });

        mixedfab.setOnClickListener(v ->{
            Toast.makeText(requireContext(), "Directing to mixedfab page", Toast.LENGTH_SHORT).show();
            openDetailsFragment("mixedfab");
        });


        return view;
    }

private void openDetailsFragment(String item) {
    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
    transaction.replace(R.id.frameLayout, GuideDetailsFragment.newInstance(item));
    transaction.addToBackStack(null); // Allow going back
    transaction.commit();
}



}