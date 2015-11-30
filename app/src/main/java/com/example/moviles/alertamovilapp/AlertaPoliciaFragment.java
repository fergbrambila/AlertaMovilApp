package com.example.moviles.alertamovilapp;

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

public class AlertaPoliciaFragment extends DialogFragment {
    Context mContext;
    private static ImageButton oBtnChoque;
    private static ImageButton oBtnAsalto;
    private static ImageButton oBtnRobo;
    private static ImageButton oBtnPelea;
    private String sDescripcion;
    private String usuario;
    private SharedPreferences editor;
    private String fecha;
    private double latitud ;
    private double longitud;
    private String cityName;
    private String stateName;
    private String streetName;
    private String countryName;
    private List<Address> addresses;
    private Geocoder geocoder;
    private View oDialogView;
    private AlertDialog alert;

    public AlertaPoliciaFragment() {
        mContext = getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test", "dialogo");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE);

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        oDialogView = inflater.inflate(R.layout.fragment_alerta_policia, null);

        oBtnChoque = (ImageButton) oDialogView.findViewById(R.id.imageButtonChoque);
        oBtnAsalto = (ImageButton) oDialogView.findViewById(R.id.imageButtonAsalto);
        oBtnRobo = (ImageButton) oDialogView.findViewById(R.id.imageButtonRobo);
        oBtnPelea = (ImageButton) oDialogView.findViewById(R.id.imageButtonPelea);

        alertDialogBuilder.setView(oDialogView)
                .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });

        oBtnChoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Choque Auto");
            }
        });

        oBtnAsalto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Asalto");
            }
        });

        oBtnRobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Robo");
            }
        });

        oBtnPelea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarDireccion();
                enviarAlerta("Pelea de Personas");
            }
        });
        alert = alertDialogBuilder.create();
        return alert;
    }

    public void generarDireccion(){
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

    public void enviarAlerta(String subTipo){
        sDescripcion = "Alerta creada por Botón de Pánico";
        usuario = editor.getString("usuario", "a@a.com");

        new ReporteLeveTask(new ReporteLeveTask.ReporteLeveCallback() {
            @Override
            public void onSuccess() {
                oBtnRobo.setEnabled(false);
                oBtnAsalto.setEnabled(false);
                oBtnPelea.setEnabled(false);
                oBtnChoque.setEnabled(false);
                alert.dismiss();
            }

            @Override
            public void onFail() {
            }
        }).execute(sDescripcion, usuario, fecha, String.valueOf(latitud), String.valueOf(longitud), subTipo, "Policia", cityName);
        Toast.makeText(getActivity().getBaseContext(), "ALERTA ENVIADA - " + subTipo, Toast.LENGTH_SHORT).show();
        alert.dismiss();
    }

    public static AlertaPoliciaFragment newInstance() {
        AlertaPoliciaFragment frag = new AlertaPoliciaFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void show(FragmentManager fragmentManager, String fragmentDialog) {
    }
}
