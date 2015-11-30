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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class ReporteFiltroFragment extends DialogFragment {
    Context mContext;
    private static Spinner tipoSpinner;
    private static Spinner subtipoSpinner;
    private static Spinner ciudadSpinner;
    private static String spinTipo;
    private static String spinSubtipo;
    private static String spinCiudad;
    private static DatePicker datePicker;
    private static String fecha;

    public ReporteFiltroFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "Filtro");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View oDialogView = inflater.inflate(R.layout.fragment_reporte_filtro, null);

        tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);
        datePicker = (DatePicker) oDialogView.findViewById(R.id.datePicker);

        String[] tipos = new String[]{"Elegir Tipo", "Policia", "Bomberos", "Medicos", "Servicios"};

        final Map<String, String[]> oMapSubTipos = new HashMap<>();

        oMapSubTipos.put("Elegir Tipo", new String[]{"Elegir Tipo Primero"});
        oMapSubTipos.put("Policia", new String[]{"Elegir Opción", "Choque Auto", "Robo Casa Habitación", "Robo Casa Deshabitada", "Asalto", "Pelea de Personas", "Vehículo/Persona Sospechosa", "Peligro en la Vía / Obras Públicas"});
        oMapSubTipos.put("Bomberos", new String[]{"Elegir Opción", "Incendio Casa", "Incendio Forestal","Incendio Edificio", "Gato sobre un árbol"});
        oMapSubTipos.put("Servicios", new String[]{"Elegir Opción", "Luminaria Apagada/Rota", "Semáforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medicos", new String[]{"Elegir Opción", "Emergencia Médica", "Choque Auto"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));
                subtipoSpinner.setAdapter(adapter);
                spinTipo = (String) parent.getItemAtPosition(position);
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

        subtipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinSubtipo = (String) parent.getItemAtPosition(position);
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
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        fecha = day + "/" + month + "/" + year;

                        if (fecha.isEmpty())
                            fecha = "";

                        if (spinTipo.equalsIgnoreCase("Elegir Tipo"))
                            spinTipo = "";

                        if (spinSubtipo.equalsIgnoreCase("Elegir Tipo Primero")||spinSubtipo.equalsIgnoreCase("Elegir Opción"))
                            spinTipo = "";

                        if(spinCiudad.equalsIgnoreCase("Elegir Ciudad"))
                            spinCiudad = "";

                        Toast.makeText(getActivity().getBaseContext(), fecha, Toast.LENGTH_LONG).show();
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
