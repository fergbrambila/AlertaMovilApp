package com.example.moviles.alertamovilapp;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by Edgardo on 27/11/2015.
 */
public class ReporteFiltrarFechaTask extends AsyncTask<String, Void, ArrayList<Reporte>> {
    private static final String MAIN_REQUEST_URL = "http://Edgardo-PC:8080/Prueba1Web/PruebaWS";

    private ReporteFiltrarFechaCallback oCallback;
    private SoapObject resultsObject;
    private ArrayList<Reporte> reportes;
    private String data;

    public ReporteFiltrarFechaTask(ReporteFiltrarFechaCallback oCallback) {
        this.oCallback = oCallback;
    }

    private ArrayList<Reporte> consumirReporteFiltrarFecha(String fValue1) {
        Log.i("ReporteFiltrarFechaTask", "consumirReporteFiltrarFecha");
        data = null;
        String methodname = "filtrarFecha";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("dato", fValue1);


        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);
            resultsObject = (SoapObject) envelope.getResponse();
            reportes = new ArrayList<Reporte>();
            for (int i = 0; i < resultsObject.getPropertyCount(); i++) {
                Reporte reporte = new Reporte();
                SoapObject s_deals_1 = (SoapObject) resultsObject.getProperty(i);
                reporte.setComentario(s_deals_1.getProperty("comentario").toString());
                reporte.setEmail(s_deals_1.getProperty("email").toString());
                reporte.setFecha(s_deals_1.getProperty("fecha").toString());
                reporte.setSubTipo(s_deals_1.getProperty("subTipo").toString());
                reporte.setTipo(s_deals_1.getProperty("tipo").toString());
                reporte.setLatitud(new Double(s_deals_1.getProperty("latitud").toString()));
                reporte.setLongitud(new Double(s_deals_1.getProperty("longitud").toString()));
                reporte.setCiudad(s_deals_1.getProperty("ciudad").toString());

                reportes.add(reporte);
            }
            //return reportes;
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
        return reportes;
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
    protected ArrayList<Reporte> doInBackground(String... params) {
        Log.i("RegistrarTask", "doInBackground");
        return consumirReporteFiltrarFecha(params[0]);
        //return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Reporte> s) {
        if (!data.equals("Error"))
            oCallback.onSuccess(s);
        else
            oCallback.onFail();
    }

    public interface ReporteFiltrarFechaCallback {
        void onSuccess(ArrayList<Reporte> s);

        void onFail();
    }
}
