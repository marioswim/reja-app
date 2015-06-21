package com.quesada.reja;

import android.app.ProgressDialog;
import android.opengl.ETC1;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quesada.utils.AdapterBusqueda;
import com.quesada.utils.AdapterGrupos;
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
 * Created by SobreMesa on 20/06/2015.
 */
public class UnirseGrupo extends Fragment{


    private View rootView;
    private ListView list_grupos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_unirse_grupo, container, false);
        list_grupos= (ListView) rootView.findViewById(R.id.lista_busqueda_grupos);

        final EditText buscar_grupos= (EditText) rootView.findViewById(R.id.texto_buscar_grupos);
        buscar_grupos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                buscar_grupos.setHint("");
            }
        });
        buscar_grupos.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == 0) {
                    String texto = buscar_grupos.getText().toString();
                    new SearchGroup(rootView).execute(texto);
                    return true;
                }
                return false;
            }

        });
        list_grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String groupId= (String) list_grupos.getAdapter().getItem(position);
                String userId= Login.iduser;
                new JoinToGroup(rootView).execute(groupId,userId);
            }
        });

            return rootView;
        }

    private class SearchGroup extends AsyncTask<String,Void,Void>
    {
        JSONObject obj;
        ProgressDialog dialog;

        public SearchGroup(View view)
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
            postParams.add(new BasicNameValuePair("textGroup",params[0]));
            this.obj= utils.postRequest("searchGroup", postParams);


            return null;
        }
        protected void onPostExecute(Void v)
        {
            Log.d("TAG", "p execute");
            this.dialog.dismiss();
            ArrayList<String> grupos=new ArrayList<String>();
            JSONArray json= null;
            try {
                json = obj.getJSONArray("groups");
                if(json.length()>0) {
                    for (int i = 0; i < json.length(); i++) {
                        grupos.add(json.getJSONObject(i).getString("groupId"));
                    }
                }
                else
                {
                    Toast.makeText(rootView.getContext(), "No se han encontrado resultados", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            AdapterGrupos adapter= new AdapterGrupos(getActivity(),grupos);
            list_grupos.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private class JoinToGroup extends AsyncTask<String,Void,Void>
    {
        JSONObject obj;
        ProgressDialog dialog;
        public JoinToGroup(View view)
        {
            this.dialog=new ProgressDialog(rootView.getContext());
        }
        protected  void onPreExecute()
        {
            this.dialog.setMessage("Procesando peticion");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            List<NameValuePair> postParams=new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("groupId",params[0]));
            postParams.add(new BasicNameValuePair("userId",params[1]));
            this.obj= utils.postRequest("join", postParams);


            return null;
        }
        protected void onPostExecute(Void v)
        {
            Log.d("TAG", "p execute");
            this.dialog.dismiss();
            JSONArray json= null;
            try {
                String message= obj.getString("message");

                switch (obj.getString("code_error"))
                {
                    case "0":
                        Toast.makeText(rootView.getContext(), message, Toast.LENGTH_LONG).show();
                        break;
                    case "1062":
                        Toast.makeText(rootView.getContext(), "Ya has realizado la petición en este grupo", Toast.LENGTH_LONG).show();
                        break;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
