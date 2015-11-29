package com.example.moviles.alertamovilapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.gps.GPSTracker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = getSharedPreferences("alerta_mobile", MODE_PRIVATE);


        GPSTracker gps = new GPSTracker(MainActivity.this);
        //Toast.makeText(getBaseContext(),gps.getLatitude()+" "+gps.getLongitude(), Toast.LENGTH_LONG).show();//realm

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("My message ").append(" ");
                builder.setSpan(new ImageSpan(MainActivity.this, R.drawable.common_signin_btn_icon_light), builder.length() - 1, builder.length(), 0);
                builder.append(" next message");
                Snackbar.make(view, builder, Snackbar.LENGTH_LONG).show();

                //Snackbar.make(view, "Prueba de funcionamiento", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //setContentView(R.layout.fragment_inicio);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new ReportesFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cerrar Aplicación")
                    .setMessage("Esta seguro que desea salir de la aplicación?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fm = getFragmentManager();

        if (id == R.id.nav_inicio) {
            Toast.makeText(getApplicationContext(),"Inicio - Mapa",Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            //startActivity(i);
            fm.beginTransaction().replace(R.id.content_frame, MapFragment.newInstance("","")).commit();
            //fm.beginTransaction().replace(R.id.content_frame,new InicioFragment()).commit();
        } else if (id == R.id.nav_reportes) {
            Toast.makeText(getApplicationContext(), "Reportes", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new ReportesFragment()).commit();
        } else if (id == R.id.nav_misreportes) {
            Toast.makeText(getApplicationContext(),"Mis Reportes",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new MisReportesFragment()).commit();
        } else if (id == R.id.nav_seguidos) {
            Toast.makeText(getApplicationContext(),"Reportes Seguidos",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new SeguidosFragment()).commit();
        } else if (id == R.id.nav_emergencias) {
            Toast.makeText(getApplicationContext(),"Números de Emergencia",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new EmergenciasFragment()).commit();
        } else if (id == R.id.nav_ayuda) {
            Toast.makeText(getApplicationContext(),"Ayuda",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new AyudaFragment()).commit();
        } else if (id == R.id.nav_somos) {
            Toast.makeText(getApplicationContext(),"Quienes Somos",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new SomosFragment()).commit();
        } else if (id == R.id.nav_donar) {
            Toast.makeText(getApplicationContext(),"Donar",Toast.LENGTH_SHORT).show();
            fm.beginTransaction().replace(R.id.content_frame, new DonarFragment()).commit();
        } else if (id == R.id.nav_cerrar) {
            editor.edit().putBoolean("login",false).commit();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
