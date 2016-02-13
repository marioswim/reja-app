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

import com.quesada.activities.Detalles;
import com.quesada.activities.Login;
import com.quesada.adapters.AdapterRecomendacion;
import com.quesada.reja.R;
import com.quesada.services.Gps;
import com.quesada.objects.Restaurante;
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
public class ListaRecomendacion extends Fragment {

    private ListView lista_rec;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_lista_recomendacion, container, false);

        lista_rec= (ListView) rootView.findViewById(R.id.recomendacion);

        boolean context=getArguments().getBoolean("context");


        new HiloEnSegundoPlano(rootView).execute("recommendations/"+Login.iduser);



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




        return rootView;
    }

    public  void requestContextRecomendation()
    {
        //Toast.makeText(rootView.getContext(), Double.toString(Gps.latitude)+", "+Double.toString(Gps.longitude)+", "+Integer.toString(100), Toast.LENGTH_LONG).show();



        SharedPreferences pref=this.getActivity().getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);
        float distancia=pref.getFloat("distancia",0);
        new contextRecommedation(this.rootView).execute(Double.toString(Gps.latitude), Double.toString(Gps.longitude), Float.toString(distancia),Login.iduser);
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
    private class contextRecommedation extends AsyncTask<String, Void, Void> {


        JSONObject obj=null;
        ProgressDialog dialog;

        public contextRecommedation(View root){


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
        protected Void doInBackground(String... params) {

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("my_lat",  params[0]));
            postParams.add(new BasicNameValuePair("my_long",  params[1]));
            postParams.add(new BasicNameValuePair("maxDist", params[2]));
            postParams.add(new BasicNameValuePair("idUser", params[3]));
            this.obj=utils.postRequest("context", postParams);

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
        adapter.notifyDataSetChanged();
    }
}
