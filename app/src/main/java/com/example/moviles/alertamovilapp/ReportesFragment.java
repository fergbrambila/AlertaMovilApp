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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ReportesFragment extends Fragment {
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reportes,container,false);//false is dont want to attatch to root

        ((MainActivity) getActivity()).setActionBarTitle("Reportes");

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

        Button btnRepLeves = (Button) rootView.findViewById(R.id.btnReportesLeves);

        btnRepLeves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","Reportes Leves");

                FragmentActivity activity = (FragmentActivity) getActivity();

                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                ReporteLeveFragment reportDialog = ReporteLeveFragment.newInstance();

                reportDialog.show(fm,"fragmentalert");
            }
        });

        return rootView;
    }
}
