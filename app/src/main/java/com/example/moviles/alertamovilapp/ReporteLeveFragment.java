package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.gps.GPSTracker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReporteLeveFragment extends DialogFragment {
    Context mContext;
    private static View oDialogView;
    private static EditText edTxDescripcion;
    private static String spinTipo;
    private static String spinSubtipo;
    private static String sDescripcion;
    private static Double latitud;
    private static Double longitud;
    private static String usuario;
    private SharedPreferences editor;
    private String fecha;
    private Activity activity;
    private String streetName;
    private String cityName;
    private String stateName;
    private String countryName;

    public ReporteLeveFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        oDialogView = inflater.inflate(R.layout.fragment_reporte_leve, null);

        Spinner tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        final Spinner subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);

        String[] tipos = new String[]{"Policia", "Bombero", "Medico", "Servicios"};

        final Map<String, String[]> oMapSubTipos = new HashMap<>();

        oMapSubTipos.put("Policia", new String[]{"Choque Auto", "Robo Casa Habitación", "Robo Casa Deshabitada", "Asalto", "Pelea de Personas", "Vehículo/Persona Sospechosa", "Peligro en la Vía / Obras Públicas"});
        oMapSubTipos.put("Bombero", new String[]{"Incendio Casa", "Incendio Forestal","Incendio Edificio", "Gato sobre un árbol"});
        oMapSubTipos.put("Servicios", new String[]{"Luminaria Apagada/Rota", "Semáforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medico", new String[]{"Emergencia Médica", "Choque Auto"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinTipo = (String) parent.getItemAtPosition(position);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subtipoSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subtipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinSubtipo = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alertDialogBuilder.setView(oDialogView)
                .setPositiveButton("Enviar Reporte", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        edTxDescripcion = (EditText) oDialogView.findViewById(R.id.descripcion);
                        sDescripcion = edTxDescripcion.getText().toString();
                        if (sDescripcion.isEmpty())
                            sDescripcion = "";

                        usuario = editor.getString("usuario", "a@a.com");

                        generarDireccion();
                        //Toast.makeText(getActivity().getBaseContext(), latitud + " " + longitud, Toast.LENGTH_LONG).show();//realm

                        final ProgressBar spinner = (ProgressBar) activity.findViewById(R.id.progressBar1);
                        if (spinner != null)
                            spinner.setVisibility(View.VISIBLE);

                        final TextView txtRepVacio = (TextView) activity.findViewById(R.id.txtViewReporteVacio);
                        if(txtRepVacio != null)
                            txtRepVacio.setVisibility(View.GONE);

                        new ReporteLeveTask(new ReporteLeveTask.ReporteLeveCallback() {
                            @Override
                            public void onSuccess() {
                                if (activity != null) {
                                    Toast.makeText(activity, "Reporte Enviado", Toast.LENGTH_LONG).show();
                                    Button btn = (Button) activity.findViewById(R.id.btnReportesFiltrar);
                                    if(btn != null)
                                    btn.setEnabled(true);
                                    Button btn2 = (Button) activity.findViewById(R.id.btnReportesLeves);
                                    if(btn2 != null)
                                    btn2.setEnabled(true);
                                    if(spinner != null)
                                    spinner.setVisibility(View.GONE);
                                }
                                //Tiene que traer la actividad dentro del AsyncTask para que Toast funcione
                                //http://stackoverflow.com/questions/17625857/toast-inside-of-asynctask-inside-of-fragment-causes-crashes
                                Log.d("test", "FUNCIONO");
                            }

                            @Override
                            public void onFail() {
                                Log.d("test", "FALLO");
                                if (activity != null) {
                                    Toast.makeText(activity, "Error en la Red - Reporte Enviado", Toast.LENGTH_LONG).show();
                                    Button btn = (Button) activity.findViewById(R.id.btnReportesFiltrar);
                                    if(btn != null)
                                    btn.setEnabled(true);
                                    Button btn2 = (Button) activity.findViewById(R.id.btnReportesLeves);
                                    if(btn2 != null)
                                    btn2.setEnabled(true);
                                    if(spinner != null)
                                    spinner.setVisibility(View.GONE);
                                    if(txtRepVacio != null)
                                    txtRepVacio.setVisibility(View.VISIBLE);
                                }
                            }
                        }).execute(sDescripcion, usuario, fecha, String.valueOf(latitud), String.valueOf(longitud), spinSubtipo, spinTipo, cityName);

                    }
                })
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Button btn = (Button) activity.findViewById(R.id.btnReportesFiltrar);
                        if(btn != null)
                        btn.setEnabled(true);
                        Button btn2 = (Button) activity.findViewById(R.id.btnReportesLeves);
                        if(btn2 != null)
                        btn2.setEnabled(true);
                        dialog.dismiss();
                    }
                });
        return alertDialogBuilder.create();
    }

    private void onEnviarFailed() {
        Toast.makeText(getActivity().getBaseContext(), "Llenar Datos Correctamente", Toast.LENGTH_SHORT).show();
    }

    public static ReporteLeveFragment newInstance(Double lat, Double lng) {
        latitud = lat;
        longitud = lng;
        ReporteLeveFragment frag = new ReporteLeveFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void generarDireccion(){
        Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());//INFORMACION GPS
        List<Address> addresses = null;

        GPSTracker gps = new GPSTracker(getActivity().getBaseContext());
        if(latitud == null)
            latitud = gps.getLatitude();

        if(longitud == null)
            longitud = gps.getLongitude();

        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1);//addresses.get(0).getAddressLine(0) 0->Calle 1->Ciudad 2->Region 3->Chile
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", "MAPA");
        }

        if(addresses.get(0).getAddressLine(0)!=null)
            streetName = addresses.get(0).getAddressLine(0); //Calle
        if(addresses.get(0).getLocality()!=null)
            cityName = addresses.get(0).getLocality(); //Ciudad
        if(addresses.get(0).getAdminArea()!=null)
            stateName = addresses.get(0).getAdminArea(); //Area
        if(addresses.get(0).getCountryName()!=null)
            countryName = addresses.get(0).getCountryName(); //Pais
    }


    public void onAttach (Activity attachedActivity) {
        super.onAttach(attachedActivity);
        activity = attachedActivity;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
