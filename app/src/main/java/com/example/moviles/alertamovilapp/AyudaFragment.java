package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * clase AyudaFragement extiende a Fragment donde se ponen los listeners dentro del createView de dos
 * botones para la diferentes ayudas que ofrece el fragmento
 */
public class AyudaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ayuda, container, false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Ayuda");//establecer titulo en el appbar

        Button btnPanico = (Button) rootView.findViewById(R.id.btn_Panico); //guardar los botones del layout
        Button btnCrear = (Button) rootView.findViewById(R.id.btn_Crear);

        //listeners de los dos botones que te llevan a crear un dialogo de tipo Ayuda1 y Ayuda2
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "clic ayuda");
                FragmentActivity activity = (FragmentActivity) getActivity();

                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                Ayuda1Fragment alertDialog = Ayuda1Fragment.newInstance();
                alertDialog.show(fm, "fragment_alert");
            }
        });

        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "clic ayuda");
                FragmentActivity activity = (FragmentActivity) getActivity();

                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                Ayuda2Fragment alertDialog = Ayuda2Fragment.newInstance();
                alertDialog.show(fm, "fragment_alert");
            }
        });

        return rootView;
    }
}
