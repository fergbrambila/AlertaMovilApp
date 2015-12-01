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
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Edgardo on 27/11/2015.
 */
public class ReporteGeneralTask extends AsyncTask<String, Void, ArrayList<Reporte>>{
    private static final String MAIN_REQUEST_URL = Constantes.ENDPOINT;

    private ReporteGeneralCallback oCallback;
    private SoapObject resultsObject;
    private ArrayList<Reporte> reportes;
    private String data;

    public ReporteGeneralTask(ReporteGeneralCallback oCallback) {
        this.oCallback = oCallback;
    }

    private ArrayList<Reporte> consumirReporteGeneral(String fValue1,String fValue2,String fValue3,String fValue4) {
        Log.i("ReporteGeneralTask", "consumirReporteGeneral");
        data = "";
        String methodname = "filtrar";
        String sNamespace = "http://ws.pruebas.cl/";

        SoapObject request = new SoapObject(sNamespace, methodname);
        request.addProperty("fecha", fValue1);
        request.addProperty("tipo", fValue2);
        request.addProperty("subTipo", fValue3);
        request.addProperty("ciudad",fValue4);


        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(sNamespace + methodname, envelope);
            //testHttpResponse(ht);
            Vector resultsObject = (Vector) envelope.getResponse();
            reportes = new ArrayList<Reporte>();

            //for (int i = 0; i < s_deals.getPropertyCount(); i++) {
            for(Object obj : resultsObject){
                Reporte reporte = new Reporte();
                //SoapObject s_deals_1 = (SoapObject) s_deals.getProperty(i);
                SoapObject s_deals_1 = (SoapObject) obj;
                if(s_deals_1.getProperty("comentario").toString().equalsIgnoreCase("anyType{}") || s_deals_1.getProperty("comentario").toString().isEmpty()){
                    reporte.setComentario("");
                }
                else
                    reporte.setComentario(s_deals_1.getProperty("comentario").toString());
                reporte.setEmail(s_deals_1.getProperty("email").toString());
                //reporte.setFecha(s_deals_1.getProperty("fecha").toString()); Traer fecha de webservice
                reporte.setSubTipo(s_deals_1.getProperty("subTipo").toString());
                reporte.setTipo(s_deals_1.getProperty("tipo").toString());
                reporte.setLatitud(Double.parseDouble(s_deals_1.getProperty("latitud").toString()));
                reporte.setLongitud(Double.parseDouble(s_deals_1.getProperty("longitud").toString()));
                reporte.setCiudad(s_deals_1.getProperty("ciudad").toString());

                if(s_deals_1.getProperty("tipo").toString().equalsIgnoreCase("Policia"))
                    reporte.setImg(R.mipmap.policia);
                else if (s_deals_1.getProperty("tipo").toString().equalsIgnoreCase("Bombero"))
                    reporte.setImg(R.mipmap.bombero);
                else if (s_deals_1.getProperty("tipo").toString().equalsIgnoreCase("Medico"))
                    reporte.setImg(R.mipmap.doctor);
                else if (s_deals_1.getProperty("tipo").toString().equalsIgnoreCase("Servicios"))
                    reporte.setImg(R.mipmap.servicios);
                reportes.add(reporte);
            }
            //return reportes;
            data = "ok";
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
        return consumirReporteGeneral(params[0], params[1], params[2],params[3]);
        //return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Reporte> s) {
        if(!data.equals("Error")){
            oCallback.onSuccess(s);
        }
        else
            oCallback.onFail();
    }

    public interface ReporteGeneralCallback {
        void onSuccess(ArrayList<Reporte> s);

        void onFail();
    }
}