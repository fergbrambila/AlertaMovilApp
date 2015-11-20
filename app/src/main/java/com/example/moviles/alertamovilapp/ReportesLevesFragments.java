package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class ReportesLevesFragments extends DialogFragment {
    Context mContext;

    public ReportesLevesFragments() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();

        alertDialogBuilder.setView(inflater.inflate(R.layout.fragment_alerta, null))
                .setTitle("Selecciona tipo de Alerta")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        View editLayout = inflater.inflate(R.layout.fragment_alerta, null);
        ImageButton imBBombero = (ImageButton) editLayout.findViewById(R.id.imageButtonBombero);

        imBBombero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Bombero", Toast.LENGTH_SHORT).show();
            }
        });

        return alertDialogBuilder.create();
    }

    public static AlertaFragment newInstance() {
        AlertaFragment frag = new AlertaFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }
}
