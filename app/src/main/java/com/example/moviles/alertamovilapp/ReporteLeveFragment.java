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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ReporteLeveFragment extends DialogFragment {
    Context mContext;

    public ReporteLeveFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //ImageButton a = (ImageButton) a
        View oDialogView = inflater.inflate(R.layout.fragment_reporte_leve, null);
        alertDialogBuilder.setView(oDialogView)
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

        Spinner tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        final Spinner subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);
        Spinner ciudadSpinner = (Spinner) oDialogView.findViewById(R.id.ciudadspinner);

        String[] tipos = new String[] { "Policia", "Bomberos", "Medicos", "Servicios"};
        String[] ciudades = new String[] { "Santiago", "Concepcion", "Valparaiso/Vina del Mar", "Coquimbo", "Valdivia", "Ranagua", "Temuco", "Iquique" };

        final Map<String, String[]> oMapSubTipos = new HashMap<>();

        oMapSubTipos.put("Policia", new String[] { "Choque Auto", "Robo Casa Habitacion", "Robo Casa Deshabitad", "Asalto", "Pelea de Personas", "Vehiculo/Persona Sospechosa", "Peligro en la Via / Obras Publicas"});
        oMapSubTipos.put("Bomberos", new String[] {  "Incendio Casa", "Incendio Forestal", "Gato sobre un arbol"});
        oMapSubTipos.put("Servicios", new String[] {  "Luminaria Apagada/Rota", "Semaforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medicos", new String[] { "Emergencia Medica"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));;
                subtipoSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterCiudad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ciudades);

        ciudadSpinner.setAdapter(adapterCiudad);

        ciudadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public static ReporteLeveFragment newInstance() {
        ReporteLeveFragment frag = new ReporteLeveFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}