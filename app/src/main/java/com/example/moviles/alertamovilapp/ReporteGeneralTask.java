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
public class ReporteGeneralTask extends AsyncTask<String, Void, String> {
    private static final String MAIN_REQUEST_URL = "http://Edgardo-PC:8080/Prueba1Web/PruebaWS";

    private ReporteGeneralCallback oCallback;
    public ReporteGeneralTask(ReporteGeneralCallback oCallback) {
        this.oCallback = oCallback;
    }
    private String consumirReporteGeneral(String fValue1,String fValue2,String fValue3) {
        Log.i("ReporteGeneralTask", "consumirReporteGeneral");
        String data = null;
        String methodname = "filtrar";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("fecha", fValue1);
        request.addProperty("tipo", fValue2);
        request.addProperty("subTipo", fValue3);


        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);
            SoapPrimitive resultsString = (SoapPrimitive)envelope.getResponse();
            data = resultsString.toString();

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
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY,MAIN_REQUEST_URL,60000);
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
        return consumirReporteGeneral(params[0], params[1], params[2]);
        //return null;
    }

    @Override
    protected void onPostExecute(String s) {
        oCallback.onSuccess();
        oCallback.onFail();
    }

    public interface ReporteGeneralCallback {
        void onSuccess();
        void onFail();
    }
}