package com.example.moviles.alertamovilapp;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * clase que trae todos los reportes del dia a la pantalla de inicio, donde se puede llamar a filtrar
 * o generar un reporte leve
 */
public class ReportesFragment extends Fragment implements ReporteGeneralTask.ReporteGeneralCallback {
    private ListView oLst;
    private String fecha;
    private View rootView;
    private ProgressBar spinner;
    private TextView txtRepVacio;
    private Button btnRefresh;
    //private ArrayList<Reporte> arregloReportes;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_reportes, container, false);//false is dont want to attatch to root

        ((MainActivity) getActivity()).setActionBarTitle("Reportes");//pone el titulo en el appbar

        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);//bisible el spinner de loading

        txtRepVacio = (TextView) rootView.findViewById(R.id.txtViewReporteVacio);
        txtRepVacio.setVisibility(View.GONE);//sin texto

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        //envia a webservice con los deatos de la fecha para regresar un arreglo con reportes
        new ReporteFiltrarFechaTask(new ReporteFiltrarFechaTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                oLst = (ListView) rootView.findViewById(R.id.listView);
                Adaptador oAdapter = new Adaptador(getActivity(), s);
                oLst.setAdapter(oAdapter);//pone a la lista del view, el adaptador con los reportes
                spinner.setVisibility(View.GONE);//desaparece el spinner
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes", Toast.LENGTH_LONG).show();
                txtRepVacio.setVisibility(View.VISIBLE);//pone texto de vacio
                spinner.setVisibility(View.GONE);
            }
        }).execute(fecha);

        ImageView mImageView = (ImageView) rootView.findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getBaseContext(),"Alerta Seleccionada",Toast.LENGTH_SHORT).show();
                // manda a llamar una alerta del boton de panico
                Log.d("test", "clic imagen");
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                AlertaFragment alertDialog = AlertaFragment.newInstance();
                alertDialog.show(fm, "fragment_alert");

            }
        });

        final Button btnReporteFiltro = (Button) rootView.findViewById(R.id.btnReportesFiltrar);
        final Button btnReporteLeve = (Button) rootView.findViewById(R.id.btnReportesLeves);

        //abre el dialogo de reporte leve
        btnReporteLeve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReporteFiltro.setEnabled(false);
                btnReporteLeve.setEnabled(false);
                Log.d("test", "clic Reporte Leve");
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                Double a = null;
                Double b = null;
                ReporteLeveFragment alertDialog = ReporteLeveFragment.newInstance(a, b);
                alertDialog.show(fm, "fragment_reporte_leve");
            }
        });

        btnReporteFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReporteFiltro.setEnabled(false);
                btnReporteLeve.setEnabled(false);
                Log.d("test", "clic Reporte Filtro");
                FragmentActivity activity = (FragmentActivity) getActivity();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                ReporteFiltroFragment alertDialog = ReporteFiltroFragment.newInstance(ReportesFragment.this);
                alertDialog.show(fm, "fragment_reporte_filtro");
            }
        });

        //vuelve a cargar los reportes actualizados
        btnRefresh = (Button) rootView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, new ReportesFragment()).commit();
            }
        });
        return rootView;
    }

    /**
     * metodos que ocurren dependiendo de la infromacion que traega el filtro para volver a cargar la info de los reportes
     * @param s
     */
    @Override
    public void onSuccess(ArrayList<Reporte> s) {

        oLst = (ListView) rootView.findViewById(R.id.listView);
        Adaptador oAdapter = new Adaptador(getActivity(), s);
        oLst.setAdapter(oAdapter);
        Toast.makeText(getActivity().getBaseContext(), "Reportes Actualizado", Toast.LENGTH_LONG).show();
        Button btn = (Button) getActivity().findViewById(R.id.btnReportesFiltrar);
        Button btn2 = (Button) getActivity().findViewById(R.id.btnReportesLeves);
        btn.setEnabled(true);
        btn2.setEnabled(true);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes - Filtro", Toast.LENGTH_LONG).show();
        Button btn = (Button) getActivity().findViewById(R.id.btnReportesFiltrar);
        Button btn2 = (Button) getActivity().findViewById(R.id.btnReportesLeves);
        btn.setEnabled(true);
        btn2.setEnabled(true);
        spinner.setVisibility(View.GONE);
        txtRepVacio.setVisibility(View.VISIBLE);
    }
}

