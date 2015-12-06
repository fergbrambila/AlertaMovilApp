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
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.gps.GPSTracker;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * clase AlertaBomberoFragement es un DialogFragment que actua sobre el layout fragment_alerta_bombero
 */
public class AlertaBomberoFragment extends DialogFragment {
    //Declaracion de todas las variables para poder ser usado en toda la clase
    Context mContext;
    private static ImageButton oBtnCasa;
    private static ImageButton oBtnForestal;
    private static ImageButton oBtnEdificio;
    private static ImageButton oBtnGato;
    private String sDescripcion;
    private String usuario;
    private SharedPreferences editor;
    private String fecha;
    private double latitud;
    private double longitud;
    private String cityName;
    private String stateName;
    private String streetName;
    private String countryName;
    private List<Address> addresses;
    private Geocoder geocoder;
    private View oDialogView;
    private AlertDialog alert;
    private Activity activity;
    private String sSubtipo;

    /**
     * Constructor de la clase
     */
    public AlertaBomberoFragment() {
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
        Log.d("test", "dialogo de Bomberos"); //Log para Bomberos

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE); //inicializar SharedPreferences

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss establece formato de fecha
        Date dateobj = new Date();
        fecha = df.format(dateobj); //crea la fecha con dicho formato

        oDialogView = inflater.inflate(R.layout.fragment_alerta_bombero, null); //infla la vista del fragmento

        oBtnCasa = (ImageButton) oDialogView.findViewById(R.id.imageButtonCasa); //obtiene todos los view por id de la vista
        oBtnForestal = (ImageButton) oDialogView.findViewById(R.id.imageButtonForestal);
        oBtnEdificio = (ImageButton) oDialogView.findViewById(R.id.imageButtonEdificio);
        oBtnGato = (ImageButton) oDialogView.findViewById(R.id.imageButtonGato);

        alertDialogBuilder.setView(oDialogView) // crea un dialogo de alerta usando el fragmento con un boton negativo de Salir
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        //Listeners de los Botones donde cada uno genera la direccion al momento de presionar el boton
        //y mandan a llamar enviaralerta enviando el subtipo correspondiente
        oBtnCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Incendio Casa");
            }
        });

        oBtnForestal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Incendio Forestal");
            }
        });

        oBtnEdificio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Incendio Edificio");
            }
        });

        oBtnGato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Gato sobre un árbol");
            }
        });
        alert = alertDialogBuilder.create(); //crea la alerta
        return alert;
    }

    /**
     * Genera la direccion de la ubicacion actual inicializando la latitud y longitud y consigue la
     * direcciones de ciudad calle area y pais
     */
    public void generarDireccion() {
        Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());//INFORMACION GPS
        List<Address> addresses = null;

        GPSTracker gps = new GPSTracker(getActivity().getBaseContext());
        latitud = gps.getLatitude();
        longitud = gps.getLongitude();

        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1);//addresses.get(0).getAddressLine(0) 0->Calle 1->Ciudad 2->Region 3->Chile
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", "MAPA");
        }

        streetName = addresses.get(0).getAddressLine(0); //Calle
        cityName = addresses.get(0).getLocality(); //Ciudad
        stateName = addresses.get(0).getAdminArea(); //Area
        countryName = addresses.get(0).getCountryName(); //Pais
    }

    /**
     * Recibe como parametro el subtipo para enviar al task toda la informacion necesaria para
     * generar una alerta
     *
     * @param subTipo
     */
    public void enviarAlerta(String subTipo) {
        sDescripcion = "Alerta creada por Botón de Pánico"; //poner descripcion del reporte
        usuario = editor.getString("usuario", "a@a.com"); //consigue el usuario que envia el reporte
        sSubtipo = subTipo;

        oBtnCasa.setEnabled(false);
        oBtnForestal.setEnabled(false);
        oBtnEdificio.setEnabled(false);
        oBtnGato.setEnabled(false);

        //llama a reportelevetask que manda la informacion al webservice
        new ReporteLeveTask(new ReporteLeveTask.ReporteLeveCallback() {
            @Override
            public void onSuccess() {

                if (activity != null) {
                    Toast.makeText(activity, "ALERTA ENVIADA - " + sSubtipo, Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, "Reporte Bombero Enviado", Toast.LENGTH_LONG).show();
                }
                alert.dismiss();
            }

            @Override
            public void onFail() {
                if (activity != null) {
                    Toast.makeText(activity, "Error! Reporte No Enviado", Toast.LENGTH_LONG).show();
                }
            }
        }).execute(sDescripcion, usuario, fecha, String.valueOf(latitud), String.valueOf(longitud), subTipo, "Bombero", cityName);
        alert.dismiss();
    }

    /**
     * Nueva instancia de AlertaBomberoFragment
     *
     * @return
     */
    public static AlertaBomberoFragment newInstance() {
        AlertaBomberoFragment frag = new AlertaBomberoFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void onAttach(Activity attachedActivity) {
        super.onAttach(attachedActivity);
        activity = attachedActivity;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
