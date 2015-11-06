package com.example.moviles.alertamovilapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private static TextView _link_login;
    private static Button _btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        layout.setOnTouchListener(new View.OnTouchListener()//esconder tecleado
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.ciudad_spinner);

        String[] items = new String[] { "Santiago", "Concepcion", "Valparaiso/Vina del Mar", "Coquimbo", "Valdivia", "Ranagua", "Temuco", "Iquique" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        onLinkListener();
        onButtonListener();
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onLinkListener(){
        _link_login = (TextView)findViewById(R.id.link_login);
        _link_login.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public void onButtonListener(){
        _btn_signup = (Button)findViewById(R.id.btn_signup);
        _btn_signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //login();
                    }
                }
        );
    }

    public void login () {

    }
}
