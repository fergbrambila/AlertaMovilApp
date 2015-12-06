package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * abre el fragmento infla de quienes somos
 */
public class SomosFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("Quienes Somos");//poner en el titulo del app

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_somos, container, false);
    }
}
