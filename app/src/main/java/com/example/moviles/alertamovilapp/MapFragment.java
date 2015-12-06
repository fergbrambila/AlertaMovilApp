package com.example.moviles.alertamovilapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviles.alertamovilapp.gps.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase MapFragment que extiende de un fragmento e implementa onmapclicklistener y on maplongclicklistener
 * de google maps, que son accinoes que suceden caundo presionas en el mapa
 */
public class MapFragment extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    //declaraciones de variables que se usaran en toda la clase como privados
    private static final LatLng Chile = new LatLng(23, 70);
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

    /**
     * metodo que crea una nueva instancia de la clase
     *
     * @return
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Constructor de la clase
     */
    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * metodo onCreate que llama al super
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * metodo onCreateView que dice lo que ocurre cuando se genera el fragmento y se infla el mapa
     * se llena el mapa de markers usando latitudes y longitudes d elos reportes
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Mapa");

        DateFormat df = new SimpleDateFormat("dd/MM/yy"); // HH:mm:ss
        Date dateobj = new Date();
        fecha = df.format(dateobj);//crea fecha con el formato indicado

        marker = null;

        mMapView = (MapView) view.findViewById(R.id.mapView);//crea el mapview
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap = mMapView.getMap();

        Log.e("MAPA", "INFLAR");

        ImageView mImageView = (ImageView) view.findViewById(R.id.imageBtnPanico);

        //cuando se presiona el boton de panico manda a llamar la alerta
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
        longitud = gps.getLongitude();//inicializa con tu posicion

        onMapReady(googleMap);

        LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(latitud, longitud), new LatLng(latitud, longitud));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 15));//pone la pantalla en tu ubicacion
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMapClickListener(this);//implementa metodos
        googleMap.setOnMapLongClickListener(this);

        return view;
    }

    /**
     * @param uri
     */
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Metodo que se llama cuando se genera el mapa, lo llena de marcadores el mapa
     *
     * @param map
     */
    public void onMapReady(final GoogleMap map) {
        /*map.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("hola"));*/

        //pide el request usando la fecha para poblar el mapa
        new ReporteFiltrarFechaTask(new ReporteFiltrarFechaTask.ReporteFiltrarFechaCallback() {
            @Override
            public void onSuccess(ArrayList<Reporte> s) {
                for (int i = 0; i < s.size(); i++) {//recibe una array y los pinta de los colores indicados por tipo
                    if (s.get(i).getTipo().equalsIgnoreCase("policia")) {
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(s.get(i).getLatitud(), s.get(i).getLongitud()))
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .title(s.get(i).getSubTipo()));
                    } else if (s.get(i).getTipo().equalsIgnoreCase("bombero")) {
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(s.get(i).getLatitud(), s.get(i).getLongitud()))
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                .title(s.get(i).getSubTipo()));
                    } else if (s.get(i).getTipo().equalsIgnoreCase("medico")) {
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(s.get(i).getLatitud(), s.get(i).getLongitud()))
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                .title(s.get(i).getSubTipo()));
                    } else {
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(s.get(i).getLatitud(), s.get(i).getLongitud()))
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .title(s.get(i).getSubTipo()));
                    }
                }
            }

            @Override
            public void onFail() {
                Log.e("Mapa", "fallo");
                //Toast.makeText(getActivity().getBaseContext(), "Fallo traida de Reportes", Toast.LENGTH_LONG).show();
            }
        }).execute(fecha);
    }

    /**
     * cuando se da click en el mapa, pone un marker con cierto mensaje y anima la camara para posicionarlo
     * en el centro del mapa
     *
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) { //si el marcador no es nulo, lo remueve para posicionar el siguiente
            marker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Mantener presionado para Generar Reporte");

        //googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        marker = googleMap.addMarker(markerOptions);
    }

    /**
     * Cuando se le deja click por un largo tiempo, se puede poner un reporte llamando al dialogo
     * reporte leve mandando en el new instance las lat y long seleccionadas
     *
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("PRESIONADO"); //pone mensaje
        //googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        marker = googleMap.addMarker(markerOptions);//agrega al mapa

        Double lat = latLng.latitude;
        Double lng = latLng.longitude;//separan valores de latitud y longitud

        FragmentActivity activity = (FragmentActivity) getActivity();
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        ReporteLeveFragment alertDialog = ReporteLeveFragment.newInstance(lat, lng);//se llama al reporte leve fragment mandando las variables lat y long
        alertDialog.show(fm, "fragment_reporte_leve");
    }

    /**
     * metodo onFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * metodo que sucede cuando se resume el mapa
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * metodo que se llama cuando se pone en pausa
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * metodo que se llama cuando se destruye la app
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * metodo que se llama cuando hay poca memoria
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
