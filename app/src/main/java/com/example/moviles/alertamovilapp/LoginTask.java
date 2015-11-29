package com.example.moviles.alertamovilapp;

import android.os.AsyncTask;
import android.util.Log;

import com.example.moviles.alertamovilapp.clases.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;

/**
 * Created by Edgardo on 27/11/2015.
 */
public class LoginTask extends AsyncTask<String, Void, Usuario> {
    private static final String MAIN_REQUEST_URL = "http://Edgardo-PC:8080/Prueba1Web/PruebaWS";

    private LoginCallback oCallback;
    private SoapObject resultsString;
    private String data;

    public LoginTask(LoginCallback oCallback) {
        this.oCallback = oCallback;
    }

    private Usuario consumirLogin(String fValue1, String fValue2) {
        Log.i("LoginTask", "consumirLogin");
        data = null;
        String methodname = "login";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("email", fValue1);
        request.addProperty("password", fValue2);

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);

            resultsString = (SoapObject) envelope.getResponse();
            Usuario user = new Usuario();
            user.setEmail(resultsString.getProperty("email").toString());
            user.setNombre(resultsString.getProperty("nombre").toString());
            //   Usuario data2 = ()resultsString.getValue();
            Log.e("data", resultsString.getProperty("email").toString());
            return user;
        } catch (SocketTimeoutException t) {
            t.printStackTrace();
            data = "Error";
        } catch (IOException i) {
            data = "Error";
            i.printStackTrace();
        } catch (Exception q) {
            data = "Error";
            q.printStackTrace();
        }
        return null;
    }

    private final HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, MAIN_REQUEST_URL, 60000);
        ht.debug = true;
        ht.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
        return ht;
    }

    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; //colocar false ya que no se trabaja con .net
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }

    @Override
    protected Usuario doInBackground(String... params) {
        Log.i("RegistrarTask", "doInBackground");
        return consumirLogin(params[0], params[1]);
        //return null;
    }

    @Override
    protected void onPostExecute(Usuario s) {
        if (!data.equals("Error"))
            oCallback.onSuccess(s);
        else
            oCallback.onFail();
    }

    public interface LoginCallback {
        void onSuccess(Usuario s);

        void onFail();
    }
}