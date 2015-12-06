package com.example.moviles.alertamovilapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviles.alertamovilapp.clases.Usuario;

/**
 * clase LoginActivity que extiende de AppCompatActivity y que maneja la pantalla del login
 * verificando los campos y mandando a otras actividades y revision de los datos de usuario y password
 */
public class LoginActivity extends AppCompatActivity {
    //declarar las variables que se usaran de forma privada en la clase
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static MultiAutoCompleteTextView _email;
    private static EditText _password;
    private static Button _btn_login;
    private static TextView _link_signup;
    private SharedPreferences editor;
    private ProgressBar spinner;

    /**
     * Metodo que crea la clase y donde se infla el layout del login
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editor = getSharedPreferences("alerta_mobile", MODE_PRIVATE);//inicializa variable de SharedPreferences

        spinner = (ProgressBar) findViewById(R.id.progressBar1); //jala el spinner de LOADING en una variable
        spinner.setVisibility(View.GONE); //se quita la visibilidad del spinner

        if (editor.getBoolean("login", false)) { //si se esta Loggeado pasa directo al mainactivity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout); //layout del la pantalla
        layout.getBackground().setAlpha(80); // pone el nivel del brillo/Alpha de la imagen del fondo

        layout.setOnTouchListener(new View.OnTouchListener()//esconder tecleado
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev) { // si se da click al layout, se esconde el teclado
                hideKeyboard(view);
                return false;
            }
        });

        onLinkListener(); //metodo listener del LINK
        onButtonListener(); //metodo listener del BOTON
    }

    protected void hideKeyboard(View view) { //esconde el teclado de la pantalla
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onButtonListener() {
        _btn_login = (Button) findViewById(R.id.btn_login);
        _btn_login.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        login();
                    }
                }//manda a llamar funcion del login al momento de presionar el boton
        );
    }

    public void onLinkListener() {
        _link_signup = (TextView) findViewById(R.id.link_signup);//manda el link a la actividad de Registro al darle click
        _link_signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    /**
     * Metodo Login valida que los datos sean correctos y envia informacion al LoginTask
     */
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        spinner.setVisibility(View.VISIBLE);
        _btn_login.setEnabled(false);

        _email = (MultiAutoCompleteTextView) findViewById(R.id.email);
        _password = (EditText) findViewById(R.id.password);

        final String email = _email.getText().toString();//convierte a string el texto dentro de los edittext
        final String password = _password.getText().toString();

        //llama el task enviando los datos necesarios
        new LoginTask(new LoginTask.LoginCallback() {
            @Override
            public void onSuccess(Usuario s) {
                Toast.makeText(getBaseContext(), "Bienvenido " + s.getNombre(), Toast.LENGTH_LONG).show();//realm
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                editor.edit().putBoolean("login", true).commit(); //guarda en el sistema la variable login como true
                editor.edit().putString("usuario", s.getEmail()).commit(); //guarda el usuario que es el email
                spinner.setVisibility(View.GONE);
                finish(); //cierra la actividad del Login
            }

            @Override
            public void onFail() {
                Toast.makeText(getBaseContext(), "Fallo el Login", Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
                _btn_login.setEnabled(true);
            }
        }).execute(email, password);

    }

    //validan los campos
    public boolean validate() {
        boolean valid = true;

        _email = (MultiAutoCompleteTextView) findViewById(R.id.email);
        _password = (EditText) findViewById(R.id.password);

        String email = _email.getText().toString();
        String password = _password.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("Ingresar un correo correcto");
            valid = false;
        }
        if (password.isEmpty()) {
            _password.setError("Ingresar Contrasena");
            valid = false;
        }
        return valid;
    }

    public void onLoginSuccess() {
        _btn_login.setEnabled(true);
        Toast.makeText(getBaseContext(), "Redireccionando", Toast.LENGTH_LONG).show();
        finish();
    }

    //si validar falla se llama aqui
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Completa correctamente los campos", Toast.LENGTH_LONG).show();

        _btn_login.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

}
