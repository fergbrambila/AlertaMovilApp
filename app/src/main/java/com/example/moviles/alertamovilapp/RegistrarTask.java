package com.example.moviles.alertamovilapp;

import android.os.AsyncTask;
import android.util.Log;

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
public class RegistrarTask extends AsyncTask<String, Void, String> {
    private static final String MAIN_REQUEST_URL = "http://192.168.96.71:8080/Prueba1Web/PruebaWS";
    private static String data;

    private RegistrarCallback oCallback;

    public RegistrarTask(RegistrarCallback oCallback) {
        this.oCallback = oCallback;
    }

    private String consumirRegistrar(String fValue1, String fValue2, String fValue3, String fValue4, String fValue5, String fValue6) {
        Log.i("RegistrarTask", "consumirRegistrar");
        data = null;
        String methodname = "registrar";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("sEmail", fValue1);
        request.addProperty("sPassword", fValue2);
        request.addProperty("sNombre", fValue3);
        request.addProperty("sApellido", fValue4);
        request.addProperty("sCelular", fValue5);
        request.addProperty("sCiudad", fValue6);

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);
            SoapPrimitive resultsString = (SoapPrimitive) envelope.getResponse();
            data = resultsString.toString();
            Log.e("data", data);

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
        return data;
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
    protected String doInBackground(String... params) {
        Log.i("RegistrarTask", "doInBackground");
        return consumirRegistrar(params[0], params[1], params[2], params[3], params[4], params[5]);
        //return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (data.equalsIgnoreCase("true") || !data.equals("Error")) {
            oCallback.onSuccess();
        } else {
            oCallback.onFail();
        }
    }

    public interface RegistrarCallback {
        void onSuccess();

        void onFail();
    }
}