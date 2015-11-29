package com.example.moviles.alertamovilapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.clases.Usuario;
import com.example.moviles.alertamovilapp.gps.GPSTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReporteLeveFragment extends DialogFragment {
    Context mContext;
    private static View oDialogView;
    private static EditText edTxDescripcion;
    private static String spinCiudad;
    private static String spinTipo;
    private static String spinSubtipo;
    private static String sDescripcion;
    private static double latitud;
    private static double longitud;
    private static String usuario;
    private SharedPreferences editor;
    private String fecha;

    public ReporteLeveFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        editor = getActivity().getSharedPreferences("alerta_mobile", MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        oDialogView = inflater.inflate(R.layout.fragment_reporte_leve, null);

        Spinner tipoSpinner = (Spinner) oDialogView.findViewById(R.id.tipospinner);
        final Spinner subtipoSpinner = (Spinner) oDialogView.findViewById(R.id.subtipospinner);
        Spinner ciudadSpinner = (Spinner) oDialogView.findViewById(R.id.ciudadspinner);

        String[] tipos = new String[]{"Policia", "Bomberos", "Medicos", "Servicios"};
        String[] ciudades = new String[]{"Santiago", "Concepcion", "Valparaiso/Vina del Mar", "Coquimbo", "Valdivia", "Ranagua", "Temuco", "Iquique"};

        final Map<String, String[]> oMapSubTipos = new HashMap<>();

        oMapSubTipos.put("Policia", new String[]{"Choque Auto", "Robo Casa Habitacion", "Robo Casa Deshabitad", "Asalto", "Pelea de Personas", "Vehiculo/Persona Sospechosa", "Peligro en la Via / Obras Publicas"});
        oMapSubTipos.put("Bomberos", new String[]{"Incendio Casa", "Incendio Forestal", "Gato sobre un arbol"});
        oMapSubTipos.put("Servicios", new String[]{"Luminaria Apagada/Rota", "Semaforo Apagado", "Eventos en Pavimento", "Sin Luz Sector", "Sin Agua Sector", "Basura en Sector"});
        oMapSubTipos.put("Medicos", new String[]{"Emergencia Medica"});

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tipos);

        tipoSpinner.setAdapter(adapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinTipo = (String) parent.getItemAtPosition(position);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, oMapSubTipos.get((String) parent.getItemAtPosition(position)));
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
                .setPositiveButton("Enviar Reporte", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        edTxDescripcion = (EditText) oDialogView.findViewById(R.id.descripcion);
                        sDescripcion = edTxDescripcion.toString();

                        usuario = editor.getString("usuario","a@a.com");

                        GPSTracker gps = new GPSTracker(getActivity().getBaseContext());
                        latitud = gps.getLatitude();
                        longitud = gps.getLongitude();
                        Toast.makeText(getActivity().getBaseContext(), latitud + " " + longitud, Toast.LENGTH_LONG).show();//realm


                        new ReporteLeveTask(new ReporteLeveTask() {
                            @Override
                            public void onSuccess(Usuario s) {
                                Toast.makeText(getActivity().getBaseContext(),"BUEENA", Toast.LENGTH_LONG).show();//realm
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                editor.edit().putBoolean("login", true).commit();
                                editor.edit().putString("usuario", s.getEmail()).commit();
                                finish();
                            }

                            @Override
                            public void onFail() {
                                Toast.makeText(getBaseContext(), "Fallo el Login", Toast.LENGTH_LONG).show();
                                _btn_login.setEnabled(true);
                            }
                        }).execute(sDescripcion, usuario, fecha, latitud,longitud, spinSubtipo,spinTipo);


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

    private void onEnviarFailed() {
        Toast.makeText(getActivity().getBaseContext(), "Llenar Datos Correctamente", Toast.LENGTH_SHORT).show();
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
