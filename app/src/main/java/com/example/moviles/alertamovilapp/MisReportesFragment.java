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

/**
 * Ensena Tus reportes, los que has generado, este es un fragment, se infla la vista y muestran tus
 * reportes
 */
public class MisReportesFragment extends Fragment {
    //declaran las variables privadas para la clase
    private ListView oLst;
    private View rootView;
    private static String usuario;
    private SharedPreferences editor;
    private ProgressBar spinner;
    private TextView txtRepVacio;

    /**
     * lo que sucede caundo se llama al fragmento, se mandan a llamar los reportes del webservice
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mis_reportes, container, false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Mis Reportes");//pone el titulo de Mis Reportes

        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE);//inicizalia variable SharedPreferences

        oLst = (ListView) rootView.findViewById(R.id.listView3);
        ArrayList<Reporte> arreglo = null;

        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);//inicializa el spinner

        txtRepVacio = (TextView) rootView.findViewById(R.id.txtViewReporteVacio);
        txtRepVacio.setVisibility(View.GONE);

        usuario = editor.getString("usuario", "a@a.com");//consigue el alor del usuario para traer sus reportes

//se traen todos los valores de tus reportes como un array
        new ReporteUserTask(new ReporteUserTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                spinner.setVisibility(View.GONE);//desaparece el spinner
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
