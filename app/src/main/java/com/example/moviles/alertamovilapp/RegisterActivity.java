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
    private static EditText _passwordConfirmar;
    private static EditText _numcelular;
    private static EditText _dia;
    private static EditText _mes;
    private static EditText _anio;
    private static String spinnerCiudad;
    private static String nom;
    private static String ape;
    private static String mai;
    private static String pas;
    private static String pasC;
    private static String num;
    private static String fec;


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
                        registrar();
                    }
                }
        );
    }

    public void registrar () {
        Log.d("register", "registrar");

        _nombre = (EditText)findViewById(R.id.name);
        _apellido = (EditText)findViewById(R.id.apellido);
        _mail = (EditText)findViewById(R.id.email);
        _password = (EditText)findViewById(R.id.password);
        _passwordConfirmar = (EditText)findViewById(R.id.confirmpassword);
        _numcelular = (EditText)findViewById(R.id.numerocelular);
        _anio =(EditText)findViewById(R.id.fechaAnio);

        nom = _nombre.getText().toString();
        ape = _apellido.getText().toString();
        mai = _mail.getText().toString();
        pas = _password.getText().toString();
        pasC = _passwordConfirmar.getText().toString();
        num = _numcelular.getText().toString();
        fec = _anio.getText().toString();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        new RegistrarTask(new RegistrarTask.RegistrarCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getBaseContext(), "Registro Creado", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Inicia Sesion con tu cuenta", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFail() {
                Toast.makeText(getBaseContext(), "Error durante el proceso de registro", Toast.LENGTH_LONG).show();
                finish();
            }
        }).execute(mai, pas, nom, ape, num, spinnerCiudad);
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Rellenar los campos adecuadamente", Toast.LENGTH_LONG).show();
    }

    private boolean validate() {
        boolean valid = true;

        if (mai.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mai).matches()){
            _mail.setError("Ingresar un correo correcto");
            valid = false;
        }

        if (pas.isEmpty()){
            _password.setError("Ingresar Contrasena");
            valid = false;
        }

        if(!pas.equals(pasC)){
            _passwordConfirmar.setError("La contrasena no concuerda");
            valid = false;
        }

        if (nom.isEmpty()){
            _nombre.setError("Ingresar Nombre");
            valid = false;
        }

        if (ape.isEmpty()){
            _apellido.setError("Ingresar Apellido");
            valid = false;
        }

        if(valid == true && fec.isEmpty()){
            _anio.setText("");
        }

        if(!fec.isEmpty()){
            int iFecha = Integer.parseInt("fec");
            if(!(iFecha > 1900 && iFecha <2020)){
                _anio.setError("Año invalido");
            }
        }

        if (valid == true && num.isEmpty()){
            _numcelular.setText("");
        }

        return valid;
    }


}
