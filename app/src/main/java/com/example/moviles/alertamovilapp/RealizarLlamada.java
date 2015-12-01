package com.example.moviles.alertamovilapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.moviles.alertamovilapp.R;

/**
 * Created by ThexCrack10 on 30-11-2015.
 */
public class RealizarLlamada extends Activity {/*
    public Button makeCall;
    public Button endCall;
    public Button volver;
    public Intent callIntent;

    /** Called when the activity is first created. */
  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llamar_activity);

        makeCall = (Button)findViewById(R.id.llamar);
        endCall = (Button)findViewById(R.id.cortar);
        volver = (Button)findViewById(R.id.volver);

        makeCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                call();
            }
        });

        endCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                end();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // ();
            }
        });
    }
    private void call() {
            try {
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:133"));
                startActivity(callIntent);
            } catch (ActivityNotFoundException activityException) {
                Log.e("dialing-example", "Call failed", activityException);
            }
        }
     private void end() {
            try{
                this.finish();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }*/
}
