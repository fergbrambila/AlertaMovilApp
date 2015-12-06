package com.example.moviles.alertamovilapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * clase AlertaFragement es un DialogFragment que actua sobre el layout fragment_alerta
 */
public class AlertaFragment extends DialogFragment {
    Context mContext;

    /**
     * Constructor de la clase
     */
    public AlertaFragment() {
        mContext = getActivity();
    }

    /**
     * OnCreateDialog es llamado cuando se crea la instancia de la clase e infla la vista y agrega
     * todos sus elementos y se ponen listeners para la accion de cada boton
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogoAlerta"); // pone en el log la creacion de la alerta

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //ImageButton a = (ImageButton) a
        View oDialogView = inflater.inflate(R.layout.fragment_alerta, null);

        ImageButton oBtnBom = (ImageButton) oDialogView.findViewById(R.id.imageButtonBombero); // guarda en variable todos los ids que se implementaran en el listener
        ImageButton oBtnPol = (ImageButton) oDialogView.findViewById(R.id.imageButtonPolicia);
        ImageButton oBtnMed = (ImageButton) oDialogView.findViewById(R.id.imageButtonMedico);

//        .setTitle("Selecciona tipo de Alerta")
        alertDialogBuilder.setView(oDialogView)
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        //listeners de todos los botones, se crearan toasts y crean dialogos de alerta correspondientes
        oBtnBom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Bombero", Toast.LENGTH_SHORT).show();
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                AlertaBomberoFragment alertDialog = AlertaBomberoFragment.newInstance();
                alertDialog.show(fm, "fragment_reporte_leve");
            }
        });

        oBtnPol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Policia", Toast.LENGTH_SHORT).show();
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                AlertaPoliciaFragment alertDialog = AlertaPoliciaFragment.newInstance();
                alertDialog.show(fm, "fragment_reporte_leve");
            }
        });

        oBtnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getBaseContext(), "Medico", Toast.LENGTH_SHORT).show();
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                AlertaMedicoFragment alertDialog = AlertaMedicoFragment.newInstance();
                alertDialog.show(fm, "fragment_reporte_leve");
            }
        });
        return alertDialogBuilder.create();
    }

    /**
     * Nueva instancia de Alerta
     *
     * @return
     */
    public static AlertaFragment newInstance() {
        AlertaFragment frag = new AlertaFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
