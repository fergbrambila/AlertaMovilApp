package com.example.moviles.alertamovilapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Clase EmergenciasFragment que extiende a Fragmento y maneja los numeros de emergencia que se pueden
 * marcar desde el celular de forma directa
 */
public class EmergenciasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //AGREGADA PARA EL RETURN FINAL.
        final View rootView = inflater.inflate(R.layout.fragment_emergencias, container, false);//false is dont want to attatch to root
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Numeros de Emergencia");

        //MODIFICAR
        Button btnForestal = (Button) rootView.findViewById(R.id.btn_forestal);
        btnForestal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "130";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                startActivity(surf);
            }
        });

        Button btnAmbulancia = (Button) rootView.findViewById(R.id.btn_ambulancias);
        btnAmbulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "131";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                startActivity(surf);
            }
        });

        Button btnBombero = (Button) rootView.findViewById(R.id.btn_bomberos);
        btnBombero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "132";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                startActivity(surf);
            }
        });

        Button btnCarabineros = (Button) rootView.findViewById(R.id.btn_carabineros);
        btnCarabineros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "133";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                startActivity(surf);
            }
        });

        Button btnPdi = (Button) rootView.findViewById(R.id.btn_pdi);
        btnPdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "134";
                Uri call = Uri.parse("tel:" + number);
                Intent surf = new Intent(Intent.ACTION_CALL, call);
                startActivity(surf);
            }
        });

        return rootView;
    }
}
