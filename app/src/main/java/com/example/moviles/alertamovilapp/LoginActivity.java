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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static MultiAutoCompleteTextView _email;
    private static EditText _password;
    private static Button _btn_login;
    private static TextView _link_signup;
    private SharedPreferences editor;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editor = getSharedPreferences("alerta_mobile", MODE_PRIVATE);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        if (editor.getBoolean("login", false)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.getBackground().setAlpha(80);

        layout.setOnTouchListener(new View.OnTouchListener()//esconder tecleado
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                hideKeyboard(view);
                return false;
            }
        });

        onLinkListener();
        onButtonListener();
    }

    protected void hideKeyboard(View view) {
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
                }
        );
    }

    public void onLinkListener() {
        _link_signup = (TextView) findViewById(R.id.link_signup);
        _link_signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

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

        final String email = _email.getText().toString();
        final String password = _password.getText().toString();

        new LoginTask(new LoginTask.LoginCallback() {
            @Override
            public void onSuccess(Usuario s) {
                Toast.makeText(getBaseContext(), "Bienvenido " + s.getNombre(), Toast.LENGTH_LONG).show();//realm
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                editor.edit().putBoolean("login", true).commit();
                editor.edit().putString("usuario", s.getEmail()).commit();
                spinner.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFail() {
                Toast.makeText(getBaseContext(), "Fallo el Login", Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
                _btn_login.setEnabled(true);
            }
        }).execute(email, password);

    }

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
