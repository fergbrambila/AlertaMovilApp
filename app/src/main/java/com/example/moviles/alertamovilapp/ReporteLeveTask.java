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
public class ReporteLeveTask extends AsyncTask<String, Void, String> {
    private static final String MAIN_REQUEST_URL = "http://Edgardo-PC:8080/Prueba1Web/PruebaWS";

    private ReporteLeveCallback oCallback;
    private String data;

    public ReporteLeveTask(ReporteLeveCallback oCallback) {
        this.oCallback = oCallback;
    }

    private String consumirReporteLeve(String fValue1, String fValue2, String fValue3, String fValue4, String fValue5, String fValue6, String fValue7, String fValue8) {
        Log.i("ReporteLeveTask", "consumirReporteLeve");
        data = null;
        String methodname = "generarReporte";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("comentario", fValue1);
        request.addProperty("email", fValue2);
        request.addProperty("fecha", fValue3);
        request.addProperty("latitud", fValue4);
        request.addProperty("longitud", fValue5);
        request.addProperty("subTipo", fValue6);
        request.addProperty("tipo", fValue7);
        request.addProperty("ciudad", fValue8);

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);
            SoapPrimitive resultsString = (SoapPrimitive) envelope.getResponse();
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
        Log.i("ReporteLeveTask", "doInBackground");
        return consumirReporteLeve(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
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

    public interface ReporteLeveCallback {
        void onSuccess();

        void onFail();
    }
}