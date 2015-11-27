package com.example.moviles.alertamovilapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private static TextView _link_login;
    private static Button _btn_signup;
    private static EditText _nombre;
    private static EditText _apellido;
    private static EditText _mail;
    private static EditText _password;
    private static EditText _numcelular;
    private static EditText _dia;
    private static EditText _mes;
    private static EditText _anio;
    private static String spinnerCiudad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);

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
                spinnerCiudad = (String) parent.getItemAtPosition(position);
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
                        _nombre = (EditText)findViewById(R.id.nombre);
                        _apellido = (EditText)findViewById(R.id.apellido);
                        _mail = (EditText)findViewById(R.id.email);
                        _password = (EditText)findViewById(R.id.password);
                        _numcelular = (EditText)findViewById(R.id.numerocelular);
                        _dia = (EditText)findViewById(R.id.fechaDia);
                        _mes =(EditText)findViewById(R.id.fechaMes);
                        _anio =(EditText)findViewById(R.id.fechaAnio);

                        final String nom = _nombre.getText().toString();
                        final String ape = _apellido.getText().toString();
                        final String mai = _mail.getText().toString();
                        final String pas = _password.getText().toString();
                        final String num = _numcelular.getText().toString();
                        String fec = _dia.getText().toString() +  "/" + _mes.getText().toString() +  "/" + _anio.getText().toString();
                        fec = "02/05/2012";


                        new RegistrarTask(new RegistrarTask.RegistrarCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getBaseContext(), "Registro Creado", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFail() {
                                Toast.makeText(getBaseContext(), "Fallo el Registro - vuelvalo a intentar", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        }).execute(mai, pas, nom, ape, num, fec, spinnerCiudad);

                    }
                }
        );
    }

    public void login () {

    }
}
