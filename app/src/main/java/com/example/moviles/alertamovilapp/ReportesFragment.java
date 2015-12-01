package com.example.moviles.alertamovilapp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.clases.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportesFragment extends Fragment implements  ReporteGeneralTask.ReporteGeneralCallback {
    private ListView oLst;
    private String fecha;
    private View rootView;
    private ProgressBar spinner;
    private TextView txtRepVacio;
    //private ArrayList<Reporte> arregloReportes;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reportes, container, false);//false is dont want to attatch to root

        ((MainActivity) getActivity()).setActionBarTitle("Reportes");

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        txtRepVacio = (TextView)rootView.findViewById(R.id.txtViewReporteVacio);
        txtRepVacio.setVisibility(View.GONE);

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        new ReporteFiltrarFechaTask(new ReporteFiltrarFechaTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                oLst = (ListView) rootView.findViewById(R.id.listView);
                Adaptador oAdapter = new Adaptador(getActivity(), s);
                oLst.setAdapter(oAdapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes", Toast.LENGTH_LONG).show();
                txtRepVacio.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }
        }).execute(fecha);

        ImageView mImageView = (ImageView) rootView.findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getBaseContext(),"Alerta Seleccionada",Toast.LENGTH_SHORT).show();
                Log.d("test", "clic imagen");
                FragmentActivity activity = (FragmentActivity) getActivity();

                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                AlertaFragment alertDialog = AlertaFragment.newInstance();
                alertDialog.show(fm, "fragment_alert");

            }
        });

        Button btnReporteLeve = (Button) rootView.findViewById(R.id.btnReportesLeves);
        btnReporteLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "clic Reporte Leve");
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                ReporteLeveFragment alertDialog = ReporteLeveFragment.newInstance();
                alertDialog.show(fm, "fragment_reporte_leve");
            }
        });

        Button btnReporteFiltro = (Button) rootView.findViewById(R.id.btnReportesFiltrar);
        btnReporteFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "clic Reporte Filtro");
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                ReporteFiltroFragment alertDialog = ReporteFiltroFragment.newInstance(ReportesFragment.this);
                alertDialog.show(fm, "fragment_reporte_filtro");
            }
        });

        return rootView;
    }


    @Override
    public void onSuccess(ArrayList<Reporte> s) {
        oLst = (ListView) rootView.findViewById(R.id.listView);
        Adaptador oAdapter = new Adaptador(getActivity(), s);
        oLst.setAdapter(oAdapter);
        Toast.makeText(getActivity().getBaseContext(), "Reportes Actualizado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes - Filtro", Toast.LENGTH_LONG).show();

    }
}

