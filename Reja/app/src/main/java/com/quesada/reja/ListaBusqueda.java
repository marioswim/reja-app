package com.quesada.reja;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quesada.utils.AdapterBusqueda;
import com.quesada.utils.AdapterRecomendacion;
import com.quesada.utils.Restaurante;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uni on 27/04/15.
 */
public class ListaBusqueda extends Fragment {


    private ListView list_bus;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_busqueda_restaurantes, container, false);

        list_bus= (ListView) rootView.findViewById(R.id.lista_busqueda);

        /*Button realizarBusqueda= (Button) rootView.findViewById(R.id.boton_busqueda);

        realizarBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText textBusqueda= (EditText) rootView.findViewById(R.id.texto_buscar);

                String texto= textBusqueda.getText().toString();

                new SearchRequest(rootView).execute(texto);
            }
        });*/
        final EditText cajaBusqueda= (EditText) rootView.findViewById(R.id.texto_buscar);

        cajaBusqueda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId==0) {

                    String texto= cajaBusqueda.getText().toString();

                    new SearchRequest(rootView).execute(texto);
                    return true;
                }
                return false;
            }
        });


        list_bus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getActivity(),Detalles.class);
                Bundle b=new Bundle();
                Restaurante aux= (Restaurante) list_bus.getAdapter().getItem(position);
                b.putString("idItem",Integer.toString(aux.getId()));
                b.putString("idUser",Login.iduser);
                b.putString("origen","Búsqueda");
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        return rootView;

    }



    private class SearchRequest extends AsyncTask<String,Void, Void>
    {
        JSONObject obj;
        ProgressDialog dialog;

        public SearchRequest(View view)
        {

            this.dialog=new ProgressDialog(rootView.getContext());
        }

        protected  void onPreExecute()
        {
            this.dialog.setMessage("Cargando resultados de la busqueda");
            this.dialog.show();
        }


        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> postParams=new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("restaurant",params[0]));
            this.obj= utils.postRequest("search", postParams);


            return null;
        }

        protected void onPostExecute(Void v)
        {
            Log.d("TAG", "p execute");
            this.dialog.dismiss();
            ArrayList<Restaurante> recomendacion=new ArrayList<Restaurante>();
            JSONArray json= null;
            try {
                json = obj.getJSONArray("restaurants");
                if(json.length()>0) {
                    for (int i = 0; i < json.length(); i++) {
                        recomendacion.add(new Restaurante(
                                json.getJSONObject(i).getString("Name"),
                                json.getJSONObject(i).getString("Address"),
                                json.getJSONObject(i).getInt("id")));

                    }
                }
                else
                {
                    Toast.makeText(rootView.getContext(),"No se han encontrado resultados",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            AdapterBusqueda adapter= new AdapterBusqueda(getActivity(), recomendacion);
            list_bus.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
