package com.example.moviles.alertamovilapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MisReportesFragment extends Fragment {
    private ListView oLst;
    private View rootView;
    private static String usuario;
    private SharedPreferences editor;
    private ProgressBar spinner;
    private TextView txtRepVacio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mis_reportes,container,false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Mis Reportes");

        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE);

        oLst = (ListView)rootView.findViewById(R.id.listView3);
        ArrayList<Reporte> arreglo = null;

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        txtRepVacio = (TextView)rootView.findViewById(R.id.txtViewReporteVacio);
        txtRepVacio.setVisibility(View.GONE);

        usuario = editor.getString("usuario", "a@a.com");

        new ReporteUserTask(new ReporteUserTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                spinner.setVisibility(View.GONE);
                oLst = (ListView) rootView.findViewById(R.id.listView3);
                Adaptador oAdapter = new Adaptador(getActivity(), s);
                oLst.setAdapter(oAdapter);
                Toast.makeText(getActivity().getBaseContext(), "Bienvenido a Mis Reportes", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail() {
                txtRepVacio.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
                Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Mis Reportes", Toast.LENGTH_LONG).show();
            }
        }).execute(usuario);

        return rootView;
    }


}
