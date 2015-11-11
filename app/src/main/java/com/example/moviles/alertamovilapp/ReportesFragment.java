package com.example.moviles.alertamovilapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reportes, container, false);
    }
}
