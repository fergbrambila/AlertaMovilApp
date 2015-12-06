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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * clase que esta relacionada con la informacion del registro donde se llama a otra clase para enviar
 * la informacion del registro y guardarla en la base de datos
 */
public class RegisterActivity extends AppCompatActivity {
    //declaran las variables para la clase
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
    private static String spinnerCiudad;
    private static String nom;
    private static String ape;
    private static String mai;
    private static String pas;
    private static String pasC;
    private static String num;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (ProgressBar) findViewById(R.id.progressBar1);//declara el spinner y lo desaparece
        spinner.setVisibility(View.GONE);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);

        layout.getBackground().setAlpha(60);//pone el fondo de un alpha de 60

        layout.setOnTouchListener(new View.OnTouchListener()//esconder tecleado
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.ciudad_spinner);

        //declara el spinner de santiago
        String[] items = new String[]{"Santiago", "Concepción", "Valparaíso", "Viña del Mar", "Coquimbo", "Valdivia", "Rancagua", "Temuco", "Iquique"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,// cuando se elige alfo del spinner
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                spinnerCiudad = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //se llama los metodos correspondientes
        onLinkListener();
        onButtonListener();
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //se le da click al link regresa al login
    public void onLinkListener() {
        _link_login = (TextView) findViewById(R.id.link_login);
        _link_login.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public void onButtonListener() {
        _btn_signup = (Button) findViewById(R.id.btn_signup);
        _btn_signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        registrar();
                    }
                }
        );
    }

    public void registrar() {
        Log.d("register", "registrar");

        _nombre = (EditText) findViewById(R.id.name);
        _apellido = (EditText) findViewById(R.id.apellido);
        _mail = (EditText) findViewById(R.id.email);
        _password = (EditText) findViewById(R.id.password);
        _passwordConfirmar = (EditText) findViewById(R.id.confirmpassword);
        _numcelular = (EditText) findViewById(R.id.numerocelular);

        nom = _nombre.getText().toString();
        ape = _apellido.getText().toString();
        mai = _mail.getText().toString();
        pas = _password.getText().toString();
        pasC = _passwordConfirmar.getText().toString();
        num = _numcelular.getText().toString();

        if (!validate()) {//valida que los datos sean correctos
            onLoginFailed();
            return;
        }

        _btn_signup.setEnabled(false);
        spinner.setVisibility(View.VISIBLE);//activa el spinner antes de enviar la informacion

        //se envia la informacion del mail, password, nombre, apellido, numero y ciudad
        new RegistrarTask(new RegistrarTask.RegistrarCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getBaseContext(), "Registro Creado", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Inicia Sesion con tu cuenta", Toast.LENGTH_LONG).show();
                _btn_signup.setEnabled(true);
                spinner.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFail() {
                spinner.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(), "Error durante el proceso de registro", Toast.LENGTH_LONG).show();
                finish();
            }
        }).execute(mai, pas, nom, ape, num, spinnerCiudad);
    }

    //si falla, pone un toast diciendo qeu rellene los campos de forma adecuada
    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Rellenar los campos adecuadamente", Toast.LENGTH_LONG).show();
    }

    //validan todos los datos y si no se pone un mensaje de error
    private boolean validate() {
        boolean valid = true;

        if (mai.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mai).matches()) {
            _mail.setError("Ingresar un correo correcto");
            valid = false;
        }

        if (pas.isEmpty()) {
            _password.setError("Ingresar Contrasena");
            valid = false;
        }

        if (!pas.equals(pasC)) {//coincidan las contrasenas
            _passwordConfirmar.setError("La contrasena no concuerda");
            valid = false;
        }

        if (nom.isEmpty()) {
            _nombre.setError("Ingresar Nombre");
            valid = false;
        }

        if (ape.isEmpty()) {
            _apellido.setError("Ingresar Apellido");
            valid = false;
        }

        if (valid == true && num.isEmpty()) {
            _numcelular.setText("");
        }

        return valid;
    }
}
