package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.gps.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapFragment extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private static final LatLng Chile = new LatLng(23,70);
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleMap googleMap;
    private int vista;
    MapView mMapView;
    private String fecha;
    private ArrayList<Reporte> reportes;
    private OnFragmentInteractionListener mListener;
    private View view;
    private double latitud;
    private double longitud;
    private Marker marker;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Mapa");

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);

        marker = null;

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();

        Log.e("MAPA", "INFLAR");

        ImageView mImageView = (ImageView) view.findViewById(R.id.imageBtnPanico);

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

        GPSTracker gps = new GPSTracker(getActivity().getBaseContext());
        latitud = gps.getLatitude();
        longitud = gps.getLongitude();

        onMapReady(googleMap);

        LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(latitud, longitud), new LatLng(latitud, longitud));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 15));
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);

        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onMapReady(final GoogleMap map) {
        /*map.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("hola"));*/


        new ReporteFiltrarFechaTask(new ReporteFiltrarFechaTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                for (int i = 0; i< s.size();i++){
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(s.get(i).getLatitud(), s.get(i).getLongitud()))
                            .title(s.get(i).getSubTipo()));
                }
            }

            @Override
            public void onFail() {
                Log.e("Mapa", "fallo");
                //Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes", Toast.LENGTH_LONG).show();
            }
        }).execute(fecha);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(marker != null){
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Mantener presionado para Generar Reporte");

        //googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        marker = googleMap.addMarker(markerOptions);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if(marker != null){
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("PRESIONADO");
        //googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        marker = googleMap.addMarker(markerOptions);

        Double lat = latLng.latitude;
        Double lng = latLng.longitude;
        FragmentActivity activity = (FragmentActivity) getActivity();
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        ReporteLeveFragment alertDialog = ReporteLeveFragment.newInstance(lat,lng);
        alertDialog.show(fm, "fragment_reporte_leve");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
