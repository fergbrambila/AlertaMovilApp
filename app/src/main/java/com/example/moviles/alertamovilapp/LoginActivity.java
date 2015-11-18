package com.example.moviles.alertamovilapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private static MultiAutoCompleteTextView _email;
    private static EditText _password;
    private static Button _btn_login;
    private static TextView _link_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        /*MANITO :3
        Button l2a;
        l2a = (Button)findViewById(R.id.btn_login);
        l2a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        //FIN MANITO <3*/
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onButtonListener(){
        _btn_login = (Button)findViewById(R.id.btn_login);
        _btn_login.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        login();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    public void onLinkListener(){
        _link_signup = (TextView)findViewById(R.id.link_signup);
        _link_signup.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                        startActivity(intent);
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

        _btn_login.setEnabled(false);

        /*final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AlertaMovil);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();*/

        _email = (MultiAutoCompleteTextView)findViewById(R.id.email);
        _password = (EditText)findViewById(R.id.password);

        String email = _email.getText().toString();
        String password = _password.getText().toString();

        // TODO: Implementar forma de autenticacion aqui.

    }

    public boolean validate() {
        boolean valid = true;

        _email = (MultiAutoCompleteTextView)findViewById(R.id.email);
        _password = (EditText)findViewById(R.id.password);

        String email = _email.getText().toString();
        String password = _password.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _email.setError("Ingresar un correo correcto");
            valid = false;
        }
        if (password.isEmpty()){
            _password.setError("Ingresar Contrasena");
            valid = false;
        }
        return valid;
    }

    public void onLoginSuccess() {
        _btn_login.setEnabled(true);
        Toast.makeText(getBaseContext(), "REdireccionando", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Fallo el Login", Toast.LENGTH_LONG).show();

        _btn_login.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

}
