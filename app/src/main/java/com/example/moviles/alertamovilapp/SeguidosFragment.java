package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SeguidosFragment extends Fragment {
    private ListView oLst;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_seguidos, container, false);
                ((MainActivity) getActivity()).setActionBarTitle("Reportes Seguidos");

        oLst = (ListView)rootView.findViewById(R.id.listView2);
        Reporte[] aReportes = new Reporte[10];
        for (int i = 0; i < aReportes.length; i++) {
            Reporte oReporte = new Reporte();
            oReporte.setTitulo("askjasd");
            oReporte.setDescripcion("sdkasdafh");
            oReporte.setImg(R.drawable.calle_santiago);

            aReportes[i] = oReporte;
        }
        Adaptador oAdapter = new Adaptador(getActivity(), aReportes);

        oLst.setAdapter(oAdapter);


        return rootView;
    }
}
