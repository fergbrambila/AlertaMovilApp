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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ReportesLevesFragments extends DialogFragment {
    Context mContext;

    public ReportesLevesFragments() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test","DIALOGO Reportes Leves");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();

        alertDialogBuilder.setView(inflater.inflate(R.layout.fragment_reportes_leves_fragments, null))
                .setTitle("Generar Reportes Leves")
                .setPositiveButton("Generar", new DialogInterface.OnClickListener() {
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

        Spinner tipoSpinner = (Spinner) getView().findViewById(R.id.tipospinner);
        Spinner subtipoSpinner = (Spinner) getView().findViewById(R.id.subtipospinner);
        Spinner ciudadSpinner = (Spinner) getView().findViewById(R.id.ciudadspinner);

        String[] tipos = new String[] { "Policia", "Bomberos", "Medicos", "Servicios"};
        String[] ciudades = new String[] { "Santiago", "Concepcion", "Valparaiso/Vina del Mar", "Coquimbo", "Valdivia", "Ranagua", "Temuco", "Iquique" };
        String[] subPolicia = new String[] { "Choque Auto", "Robo Casa Habitacion", "Robo Casa Deshabitad", "Asalto", "Pelea de Personas", "Vehiculo/Persona Sospechosa", "Peligro en la Via / Obras Publicas"};
        String[] subBombero = new String[] { "Incendio Casa", "Incendio Forestal", "Gato sobre un arbol"};
        String[] subServicio = new String[] { "Luminaria Apagada/Rota", "Semaforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"};
        String[] subMedico = new String[] { "Emergencia Medica"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          //      android.R.layout.simple_spinner_dropdown_item, tipos);

        //tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            return alertDialogBuilder.create();
    }

    public static ReportesLevesFragments newInstance() {
        ReportesLevesFragments frag = new ReportesLevesFragments();
        Bundle args = new Bundle();
        frag.setArguments(args);
        Log.d("test", "NewInstance Reportes Leves");

        return frag;
    }

    public void show(android.support.v4.app.FragmentManager fm, String fragmentalert) {

    }
}
