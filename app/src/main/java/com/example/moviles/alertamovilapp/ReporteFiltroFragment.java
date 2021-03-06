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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Filtros para poder filtrar los reportes dependiendo de la fecha, tipo subtipo y ciudad
 * esta es un dialogo
 */
public class ReporteFiltroFragment extends DialogFragment {
    //declaran las variables privadas que se usaran en la clase
    Context mContext;
    private static Spinner tipoSpinner;
    private static Spinner subtipoSpinner;
    private static Spinner ciudadSpinner;
    private static String spinTipo;
    private static String spinSubtipo;
    private static String spinCiudad;
    private static DatePicker datePicker;
    private static String fecha;
    private ListView oLst;
    private View oDialogView;
    ReporteGeneralTask.ReporteGeneralCallback oCallback;
    private Activity activity;

    public ReporteFiltroFragment(ReporteGeneralTask.ReporteGeneralCallback oCallback) {
        mContext = getActivity();
        this.oCallback = oCallback;
    }

    /**
     * metodo que se llama cuando se crea el fragmento dialogo del filtro
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "Filtro");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        oDialogView = inflater.inflate(R.layout.fragment_reporte_filtro, null);

        tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);
        datePicker = (DatePicker) oDialogView.findViewById(R.id.datePicker);

        String[] tipos = new String[]{"Elegir Tipo", "Policia", "Bombero", "Medico", "Servicios"};

        final Map<String, String[]> oMapSubTipos = new HashMap<>();//declara un map para subtipos y luego se llena

        oMapSubTipos.put("Elegir Tipo", new String[]{"Elegir Tipo Primero"});
        oMapSubTipos.put("Policia", new String[]{"Elegir Opción", "Choque Auto", "Robo Casa Habitación", "Robo Casa Deshabitada", "Asalto", "Pelea de Personas", "Vehículo/Persona Sospechosa", "Peligro en la Vía / Obras Públicas"});
        oMapSubTipos.put("Bombero", new String[]{"Elegir Opción", "Incendio Casa", "Incendio Forestal", "Incendio Edificio", "Gato sobre un árbol"});
        oMapSubTipos.put("Servicios", new String[]{"Elegir Opción", "Luminaria Apagada/Rota", "Semáforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medico", new String[]{"Elegir Opción", "Emergencia Médica", "Choque Auto"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoSpinner.setAdapter(adapter);

        //cuando se elige una opcion del spinner se llena el otro spinner
        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ArrayAdapter<String> adapterSubtipo = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));
                adapterSubtipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subtipoSpinner.setAdapter(adapterSubtipo);//se llena el subtipo dependiendo del tipo
                spinTipo = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ciudadSpinner = (Spinner) oDialogView.findViewById(R.id.ciudadspinner);

        String[] ciudades = new String[]{"Elegir Ciudad", "Santiago", "Concepción", "Valparaíso", "Viña del Mar", "Coquimbo", "Valdivia", "Rancagua", "Temuco", "Iquique"};

        ArrayAdapter<String> adapterCiudad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ciudades);

        adapterCiudad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ciudadSpinner.setAdapter(adapterCiudad);

        ciudadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinCiudad = (String) parent.getItemAtPosition(position);//opcion que es seleccionada
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

        //crea la alerta del dialogo
        alertDialogBuilder.setView(oDialogView)
                .setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        fecha = day + "/" + month + "/" + year;//consigue la fecha del date picker

                        if (fecha.isEmpty())
                            fecha = "";

                        //sie tienen estas opciones, las pongo como vacios
                        if (spinTipo.equalsIgnoreCase("Elegir Tipo"))
                            spinTipo = "";

                        if (spinSubtipo.equalsIgnoreCase("Elegir Tipo Primero") || spinSubtipo.equalsIgnoreCase("Elegir Opción"))
                            spinSubtipo = "";

                        if (spinCiudad.equalsIgnoreCase("Elegir Ciudad"))
                            spinCiudad = "";

                        ProgressBar spinner = (ProgressBar) activity.findViewById(R.id.progressBar1);
                        spinner.setVisibility(View.VISIBLE); //empieza el spinner de loading

                        TextView txtRepVacio = (TextView) activity.findViewById(R.id.txtViewReporteVacio);
                        txtRepVacio.setVisibility(View.GONE);

                        //llama a general task mandando fecha spintipo, subtip y ciudad en el metodo padre
                        new ReporteGeneralTask(oCallback/*new ReporteGeneralTask.ReporteGeneralCallback() {
                            @Override
                            public void onSuccess(ArrayList<Reporte> s) {
                                oLst = (ListView) oDialogView.findViewById(R.id.listView);

                                Adaptador oAdapter = new Adaptador(getActivity(), s);

                                oLst.setAdapter(oAdapter);
                                //.stop()
                            }

                            @Override
                            public void onFail() {
                                //Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes", Toast.LENGTH_LONG).show();
                                //.stop()
                            }
                        }*/).execute(fecha, spinTipo, spinSubtipo, spinCiudad);

                        Toast.makeText(getActivity().getBaseContext(), fecha, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Button btn = (Button) activity.findViewById(R.id.btnReportesFiltrar);
                        btn.setEnabled(true);
                        Button btn2 = (Button) activity.findViewById(R.id.btnReportesLeves);
                        btn2.setEnabled(true);
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        return alertDialogBuilder.create();//crea alerta
    }

    /**
     * Nueva instancia de reporte filtro usando callback para llamar a la funcion
     *
     * @param oCallback
     * @return
     */
    public static ReporteFiltroFragment newInstance(ReporteGeneralTask.ReporteGeneralCallback oCallback) {
        ReporteFiltroFragment frag = new ReporteFiltroFragment(oCallback);
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    //ocnsigue la actividad padre para poder poner toast en la actividad padre
    public void onAttach(Activity attachedActivity) {
        super.onAttach(attachedActivity);
        activity = attachedActivity;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
