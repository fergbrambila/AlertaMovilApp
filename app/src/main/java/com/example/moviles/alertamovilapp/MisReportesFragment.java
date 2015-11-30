package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MisReportesFragment extends Fragment {
    private ListView oLst;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mis_reportes,container,false);//false is dont want to attatch to root
        ((MainActivity) getActivity()).setActionBarTitle("Mis Reportes");

        oLst = (ListView)rootView.findViewById(R.id.listView3);
        ArrayList<Reporte> arreglo = null;
        Reporte[] aReportes = new Reporte[10];
        for (int i = 0; i < aReportes.length; i++) {
            Reporte oReporte = new Reporte();
            oReporte.setTitulo("askjasd");
            oReporte.setComentario("sdkasdafh");
            oReporte.setImg(R.drawable.calle_santiago);

            aReportes[i] = oReporte;
        }
        Adaptador oAdapter = new Adaptador(getActivity(), arreglo);

        //oLst.setAdapter(oAdapter);

        return rootView;
    }
}
