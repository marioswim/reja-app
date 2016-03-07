package com.quesada.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.quesada.objects.Restaurante;
import com.quesada.reja.R;
import com.quesada.utils.Params;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sobremsa on 07/03/2016.
 */
public class Ratings extends Fragment {

    private ListView lista_ratings;
    private View rootView;
    public Ratings(){};



    public static Ratings newInstance(Bundle args) {



        Ratings fragment = new Ratings();
        if(args != null)
            fragment.setArguments(args);
        
        return fragment;
    }
    
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_lista_ratings, container, false);
        lista_ratings= (ListView) rootView.findViewById(R.id.lista_ratings);

        if(savedInstanceState==null)
        {
            Params request  =   new Params("request","ratings");
            Params iduser   =   new Params("iduser", Login.iduser);


            new postRequest(rootView).execute(request,iduser);
            lista_ratings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getActivity(), Detalles.class);
                    Bundle b = new Bundle();
                    Restaurante aux = (Restaurante) lista_ratings.getAdapter().getItem(position);
                    b.putString("idItem", Integer.toString(aux.getId()));
                    b.putString("idUser", Login.iduser);
                    b.putString("origen", "Búsqueda");
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Params request  =   new Params("request","ratings");
        Params iduser   =   new Params("iduser", Login.iduser);


        new postRequest(rootView).execute(request, iduser);
    }

    private class postRequest extends AsyncTask<Params, Void, Void> {


        JSONObject obj=null;
        ProgressDialog dialog;

        public postRequest(View root){


            this.dialog=new ProgressDialog(rootView.getContext());
        }
        protected  void onPreExecute()
        {
            this.dialog.setMessage("Cargando mis puntuaciones");
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
            this.obj= utils.postRequest(params[0].value, postParams);
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

            ArrayList<Restaurante> recomendacion=new ArrayList<Restaurante>();
            JSONArray json= null;
            try {
                json = obj.getJSONArray("ratings");

                for(int i=0;i<json.length();i++)
                {

                    Restaurante aux=new Restaurante();

                    aux.setNombre(json.getJSONObject(i).getString("name"));
                    aux.setId(json.getJSONObject(i).getInt("id"));
                    aux.setRating(json.getJSONObject(i).getDouble("rating"));
                    recomendacion.add(aux);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            AdapterRecomendacion adapter= new AdapterRecomendacion(getActivity(), recomendacion);
            lista_ratings.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }

    }

}
