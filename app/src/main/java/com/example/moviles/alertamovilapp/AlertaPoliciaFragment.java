package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AlertaPoliciaFragment extends DialogFragment {
    Context mContext;
    private static ImageButton oBtnChoque;
    private static ImageButton oBtnAsalto;
    private static ImageButton oBtnRobo;
    private static ImageButton oBtnPelea;

    public AlertaPoliciaFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //ImageButton a = (ImageButton) a
        View oDialogView = inflater.inflate(R.layout.fragment_alerta_policia, null);

        oBtnChoque = (ImageButton) oDialogView.findViewById(R.id.imageButtonChoque);
        oBtnAsalto = (ImageButton) oDialogView.findViewById(R.id.imageButtonAsalto);
        oBtnRobo = (ImageButton) oDialogView.findViewById(R.id.imageButtonRobo);
        oBtnPelea = (ImageButton) oDialogView.findViewById(R.id.imageButtonPelea);

//        .setTitle("Selecciona tipo de Alerta")
        alertDialogBuilder.setView(oDialogView)
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        oBtnChoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Choque", Toast.LENGTH_SHORT).show();
            }
        });

        oBtnAsalto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Asalto", Toast.LENGTH_SHORT).show();
            }
        });

        oBtnRobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Robo", Toast.LENGTH_SHORT).show();
            }
        });

        oBtnPelea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Pelea", Toast.LENGTH_SHORT).show();
            }
        });
        return alertDialogBuilder.create();
    }

    public static AlertaPoliciaFragment newInstance() {
        AlertaPoliciaFragment frag = new AlertaPoliciaFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
