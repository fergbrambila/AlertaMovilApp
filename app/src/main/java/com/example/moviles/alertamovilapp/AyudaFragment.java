package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AyudaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ayuda,container,false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Ayuda");
        ImageView myImage = (ImageView) rootView.findViewById(R.id.ivpanico);

        return rootView;
    }
}
