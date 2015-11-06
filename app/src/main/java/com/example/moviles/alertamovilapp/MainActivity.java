package com.example.moviles.alertamovilapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Prueba de funcionamiento", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        android.support.v4.app.FragmentManager oFragMag = getSupportFragmentManager();
        android.support.v4.app.Fragment oFrag = null;
        android.support.v4.app.FragmentTransaction oFragTx = oFragMag.beginTransaction();
        String sFragTag = null;

        boolean bIsHome = false;

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
            InicioFragment fragment = new InicioFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
            /*Toast.makeText(getApplicationContext(),"Inbox Selected", Toast.LENGTH_SHORT).show();
            oFrag = new InicioFragment();
            oFragTx.replace(R.id.container,oFrag);
            oFragTx.commit();

            bIsHome = true;
            sFragTag = "Inicio";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = InicioFragment.newInstance("", "");
            }*/
        } else if (id == R.id.nav_mapa) {
            Toast.makeText(getApplicationContext(),"map Selected",Toast.LENGTH_SHORT).show();
            sFragTag = "Mapa";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = MisReportesFragment.newInstance("","");
            }
        } else if (id == R.id.nav_misreportes) {
            sFragTag = "MisReportes";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = MisReportesFragment.newInstance("", "");
                //oFragTx.add(R.id.container, oFrag, sFragTag);
            }
        } else if (id == R.id.nav_seguidos) {
            sFragTag = "Seguidos";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = SeguidosFragment.newInstance("", "");
            }
        } else if (id == R.id.nav_emergencias) {
            sFragTag = "Emergencias";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = EmergenciasFragment.newInstance("", "");
            }
        } else if (id == R.id.nav_ayuda) {
            sFragTag = "Ayuda";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = AyudaFragment.newInstance("", "");
            }
        } else if (id == R.id.nav_somos) {
            sFragTag = "Somos";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = SomosFragment.newInstance("", "");
            }
        } else if (id == R.id.nav_donar) {
            sFragTag = "Donar";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = DonarFragment.newInstance("", "");
            }
        } else if (id == R.id.nav_cerrar) {
            sFragTag = "Cerrar";
            oFrag = oFragMag.findFragmentByTag(sFragTag);
            if(oFrag == null) {
                oFrag = InicioFragment.newInstance("", "");
            }
        }

        if(oFrag != null) {
            //android.support.v4.app.FragmentTransaction oTx = oFragMag.beginTransaction().replace(R.id.container, oFrag, sFragTag);
        }

        //oFragTx.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
