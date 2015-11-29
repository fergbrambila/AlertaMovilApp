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

public class ReporteFiltroFragment extends DialogFragment {
    Context mContext;
    private static Spinner tipoSpinner;
    private static Spinner subtipoSpinner;
    private static Spinner ciudadSpinner;
    private static String spinCiudad;

    public ReporteFiltroFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "Filtro");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View oDialogView = inflater.inflate(R.layout.fragment_reporte_filtro, null);


        tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);

        String[] tipos = new String[]{"Elegir Tipo", "Policia", "Bomberos", "Medicos", "Servicios"};

        final Map<String, String[]> oMapSubTipos = new HashMap<>();

        oMapSubTipos.put("Elegir Tipo", new String[]{"Elegir Tipo Primero"});
        oMapSubTipos.put("Policia", new String[]{"Elegir Opcion", "Choque Auto", "Robo Casa Habitacion", "Robo Casa Deshabitad", "Asalto", "Pelea de Personas", "Vehiculo/Persona Sospechosa", "Peligro en la Via / Obras Publicas"});
        oMapSubTipos.put("Bomberos", new String[]{"Elegir Opcion", "Incendio Casa", "Incendio Forestal", "Gato sobre un arbol"});
        oMapSubTipos.put("Servicios", new String[]{"Elegir Opcion", "Luminaria Apagada/Rota", "Semaforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medicos", new String[]{"Elegir Opcion", "Emergencia Medica"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));
                subtipoSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ciudadSpinner = (Spinner) oDialogView.findViewById(R.id.ciudadspinner);

        String[] ciudades = new String[]{"Elegir Ciudad", "Santiago", "Concepcion", "Valparaiso/Vina del Mar", "Coquimbo", "Valdivia", "Ranagua", "Temuco", "Iquique"};

        ArrayAdapter<String> adapterCiudad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ciudades);

        ciudadSpinner.setAdapter(adapterCiudad);

        ciudadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinCiudad = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alertDialogBuilder.setView(oDialogView)
                .setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {
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

        return alertDialogBuilder.create();
    }

    public static ReporteFiltroFragment newInstance() {
        ReporteFiltroFragment frag = new ReporteFiltroFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
