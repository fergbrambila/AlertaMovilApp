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
import android.widget.Toast;

import java.util.ArrayList;

public class MisReportesFragment extends Fragment {
    private ListView oLst;
    private View rootView;
    private static String usuario;
    private SharedPreferences editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mis_reportes,container,false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Mis Reportes");
        editor = getActivity().getSharedPreferences("alerta_mobile", Context.MODE_PRIVATE);

        oLst = (ListView)rootView.findViewById(R.id.listView3);
        ArrayList<Reporte> arreglo = null;

        usuario = editor.getString("usuario", "a@a.com");

        new ReporteUserTask(new ReporteUserTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                oLst = (ListView) rootView.findViewById(R.id.listView3);

                Adaptador oAdapter = new Adaptador(getActivity(), s);

                oLst.setAdapter(oAdapter);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Mis Reportes", Toast.LENGTH_LONG).show();
            }
        }).execute(usuario);

        return rootView;
    }


}
