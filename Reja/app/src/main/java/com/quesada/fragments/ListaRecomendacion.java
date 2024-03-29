package com.quesada.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.quesada.activities.Detalles;
import com.quesada.activities.Login;
import com.quesada.activities.MapsActivity;
import com.quesada.adapters.AdapterRecomendacion;
import com.quesada.listeners.ListenerPositionChanged;
import com.quesada.reja.R;
import com.quesada.services.Gps;
import com.quesada.objects.Restaurante;
import com.quesada.utils.Params;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uni on 21/04/15.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class ListaRecomendacion extends Fragment implements ListenerPositionChanged{

    private ListView lista_rec;
    private View rootView;
    public  static ArrayList<Restaurante> listaRecomendacion;
    Gps gps;
    ListenerPositionChanged listener;
    public ListaRecomendacion(){};


    public static ListaRecomendacion newInstance(Bundle args)
    {
        ListaRecomendacion f=new ListaRecomendacion();
        if (args!=null)
        {
            f.setArguments(args);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_lista_recomendacion, container, false);

        lista_rec= (ListView) rootView.findViewById(R.id.recomendacion);
        listener=this;
        final TextView lista =(TextView) rootView.findViewById(R.id.boton_lista_recomendacion);
        final TextView   contexto=(TextView) rootView.findViewById(R.id.boton_activar_contexto);
        final TextView   geolocalizar =(TextView) rootView.findViewById(R.id.boton_geolocalizar);

        boolean context=getArguments().getBoolean("context");

        String request=getArguments().getString("request");


        request(request);



        lista_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getActivity(),Detalles.class);
                Bundle b=new Bundle();
                Restaurante aux= (Restaurante) lista_rec.getAdapter().getItem(position);
                b.putString("idItem",Integer.toString(aux.getId()));
                b.putString("idUser", Login.iduser);
                b.putString("origen","Recomendación");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        lista.setBackgroundColor(getResources().getColor(R.color.blue_enable));


        geolocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: mirar xq no funciona la recomendacion contextualizada geolocalizada
                ListView recomendacion = (ListView) rootView.findViewById(R.id.recomendacion);
                AdapterRecomendacion lista = (AdapterRecomendacion) ((ListView) rootView.findViewById(R.id.recomendacion)).getAdapter();
                listaRecomendacion = lista.getList();
                Intent intent = new Intent(rootView.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });


        contexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new Gps(rootView.getContext(), listener);

                if (!gps.canGetLocation()) {

                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }


            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String request=getArguments().getString("request");


        request(request);
    }

    /**
     *
     */
    private class HiloEnSegundoPlano extends AsyncTask<String, Void, Void> {


        JSONObject obj=null;
        ProgressDialog dialog;

        public HiloEnSegundoPlano(View root){

            this.dialog=new ProgressDialog(rootView.getContext());
        }
        protected  void onPreExecute()
        {
            this.dialog.setMessage("Cargando recomendaciones");
            this.dialog.show();
        }
        /**
         *
         * @param urls
         * @return
         */
        @Override
        protected Void doInBackground(String... urls) {

            try {

                this.obj=utils.getRequest(urls[0]);
            } catch (IOException e) {
                //return "Unable to retrieve web page. URL may be invalid.";
            }
            //return null;
            return null;
        }

        /**
         *
         * @param v
         */
        protected void onPostExecute(Void v)
        {
            this.dialog.dismiss();
            Log.d("TAG", "p execute");

            result(this.obj);
        }

    }

    private class postRequest extends AsyncTask<Params, Void, Void> {


        JSONObject obj=null;
        ProgressDialog dialog;

        public postRequest(View root){


            this.dialog=new ProgressDialog(rootView.getContext());
        }
        protected  void onPreExecute()
        {
            this.dialog.setMessage("Cargando recomendaciones");
            this.dialog.show();
        }
        /**
         *
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Params... params) {

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();

            for(int i=1;i<params.length;i++)
            {
                postParams.add(new BasicNameValuePair(params[i].key,params[i].value));
            }
            this.obj=utils.postRequest(params[0].value, postParams);
            //return null;
            return null;
        }

        /**
         *
         * @param v
         */
        protected void onPostExecute(Void v)
        {
            this.dialog.dismiss();
            Log.d("TAG", "p execute");

            result(this.obj);
        }

    }

    private void request(String request)
    {
        String nombre;
        Params idUser;
        Params req;
        Params lat;
        Params lon;
        Params dist;
        Params groupId;
        SharedPreferences pref=this.getActivity().getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);
        float distancia=pref.getFloat("distancia", 0);
        switch (request)
        {
            default:
            case "recommendations":

                req=new Params("request","recommendation");
                idUser=new Params("idUser",Login.iduser);
                new postRequest(rootView).execute(req,idUser);
                break;
            case "context":


                req=new Params("request","context");
                lat=new Params("my_lat",Double.toString(Gps.latitude));
                lon=new Params("my_long",Double.toString(Gps.longitude));
                dist=new Params("maxDist",Float.toString(distancia));
                idUser=new Params("idUser",Login.iduser);

                new postRequest(this.rootView).execute(req, lat, lon, dist, idUser);
                break;
            case "groupRecommendation":
                req=new Params("request","groupRecommendation");

                nombre= getArguments().getString("nombre","");
                idUser=new Params("adminId",Login.iduser);
                groupId= new Params("groupId",nombre);
                new postRequest(this.rootView).execute(req,idUser,groupId);

                break;

            case "contextGroupRecommendation":

                nombre= getArguments().getString("nombre","");
                req=new Params("request","contextGroup");
                lat=new Params("my_lat",Double.toString(Gps.latitude));
                lon=new Params("my_long",Double.toString(Gps.longitude));
                dist=new Params("maxDist",Float.toString(distancia));
                idUser=new Params("idUser",Login.iduser);
                groupId= new Params("groupId",nombre);
                new postRequest(this.rootView).execute(req, lat, lon, dist, idUser,groupId);
                break;

        }
    }
    @Override
    public void refresh() {

        request("context");
        TextView   contexto=(TextView) rootView.findViewById(R.id.boton_activar_contexto);
        contexto.setTextColor(getResources().getColor(R.color.enable));


    }
    private void result(JSONObject obj)
    {
        ArrayList<Restaurante> recomendacion=new ArrayList<Restaurante>();
        JSONArray json= null;
        try {
            json = obj.getJSONArray("recommendation");

            for(int i=0;i<json.length();i++)
            {
                Restaurante aux=new Restaurante(
                        json.getJSONObject(i).getString("Name"),
                        json.getJSONObject(i).getString("address"),
                        json.getJSONObject(i).getInt("id"));
                aux.setRating(json.getJSONObject(i).getDouble("preference"));
                aux.setLongitud(json.getJSONObject(i).getDouble("longitude"));
                aux.setLatitud(json.getJSONObject(i).getDouble("latitude"));
                recomendacion.add(aux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AdapterRecomendacion adapter= new AdapterRecomendacion(getActivity(), recomendacion);
        lista_rec.setAdapter(adapter);
        listaRecomendacion=adapter.getList();
        adapter.notifyDataSetChanged();
    }
}
