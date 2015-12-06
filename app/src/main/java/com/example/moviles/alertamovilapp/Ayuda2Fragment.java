package com.example.moviles.alertamovilapp;

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

/**
 * clase Ayuda1Fragement extiende DialogFragment que actua sobre el layout fragment_ayuda1
 * explican los pasos que se deben de hacer para cierta ocasion
 */
public class Ayuda2Fragment extends DialogFragment {
    Context mContext;

    /**
     * Constructor de la clase donde se guarda la actividad en el contexto
     */
    public Ayuda2Fragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo"); //guarda ayuda1 en el log

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //ImageButton a = (ImageButton) a
        View oDialogView = inflater.inflate(R.layout.fragment_ayuda2, null);

//        .setTitle("Selecciona tipo de Alerta")
        alertDialogBuilder.setView(oDialogView)
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        return alertDialogBuilder.create();
    }

    /**
     * Nueva instancia de Ayuda2
     *
     * @return
     */
    public static Ayuda2Fragment newInstance() {
        Ayuda2Fragment frag = new Ayuda2Fragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
