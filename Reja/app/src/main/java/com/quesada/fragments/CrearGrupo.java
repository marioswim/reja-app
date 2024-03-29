package com.quesada.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quesada.activities.Login;
import com.quesada.listeners.ListenerRefresh;
import com.quesada.adapters.AdapterMiembros;
import com.quesada.adapters.AdapterUsuario;
import com.quesada.reja.R;
import com.quesada.objects.Usuario;
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
public class CrearGrupo extends Fragment implements ListenerRefresh {

    private View rootView;
    private ListView lista_miembros;
    private ListView lista_pendientes;
    private ListenerRefresh listen;
    String nombreGrupo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listen=this;
        rootView = inflater.inflate(R.layout.fragment_crear_grupos, container, false);

        lista_miembros=(ListView) rootView.findViewById(R.id.lista_usuarios);
        lista_pendientes=(ListView) rootView.findViewById(R.id.lista_pendientes);


        final EditText crear_grupo=(EditText) rootView.findViewById(R.id.nombre_crear_grupo);
        final LinearLayout reload= (LinearLayout) rootView.findViewById(R.id.refrescar_miembros);
        Button request = (Button) rootView.findViewById(R.id.requestGroups);

        //ImageView aceptar=(ImageView) rootView.findViewById(R.id.boton_aceptar_miembro);
        crear_grupo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == 0) {
                    nombreGrupo = (String) crear_grupo.getText().toString();

                    new AddGroup(rootView).execute(nombreGrupo, Login.iduser);
                }

                return false;
            }
        });


        Button crear = (Button) rootView.findViewById(R.id.boton_crear_grupo);
        crear_grupo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                crear_grupo.setHint("");

            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreGrupo = (String) crear_grupo.getText().toString();

                if (!nombreGrupo.equals(""))
                    new AddGroup(rootView).execute(nombreGrupo, Login.iduser);
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreGrupo = (String) crear_grupo.getText().toString();
                //if(recarga==null)
                new Members(rootView).execute(nombreGrupo, Login.iduser);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Bundle args=new Bundle();
                args.putString("request","groupRecommendation");
                args.putString("nombre",nombreGrupo);
                ListaRecomendacion frag=ListaRecomendacion.newInstance(args);
                getFragmentManager().beginTransaction().replace(R.id.main_container,frag).commit();
            }
        });


        return rootView;
    }

    @Override
    public void refresh() {
        new Members(rootView).execute(nombreGrupo, Login.iduser);
    }

    private class AddGroup extends AsyncTask<String,Void,Void>
    {
        ProgressDialog dialog;
        JSONObject obj;
        String nombreGrupo;
        public AddGroup(View view)
        {
            this.dialog=new ProgressDialog(rootView.getContext());
        }
        protected  void onPreExecute()
        {
            this.dialog.setMessage("Realizando petición");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            this.nombreGrupo=params[0];
            List<NameValuePair> postParams=new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("groupId",params[0]));
            postParams.add(new BasicNameValuePair("adminId",params[1]));
            this.obj= utils.postRequest("addGroup", postParams);
            return null;
        }
        protected void onPostExecute(Void v)
        {
            Log.d("TAG", "p execute");
            this.dialog.dismiss();
            try {
                String message= obj.getString("message");

                switch (obj.getString("code_error"))
                {
                    case "0":
                        Toast.makeText(rootView.getContext(), message, Toast.LENGTH_LONG).show();
                        ((LinearLayout) rootView.findViewById(R.id.container_aux_crear_grupo)).setVisibility(View.GONE);
                        ((LinearLayout) rootView.findViewById(R.id.refrescar_miembros)).setVisibility(View.VISIBLE);
                        break;
                    case "1062":
                        Toast.makeText(rootView.getContext(), "Este grupo ya existe", Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class Members extends AsyncTask<String,Void, Void>
    {
        ProgressDialog dialog;
        Boolean stop;
        JSONObject obj;
        public Members(View view)
        {
            this.dialog=new ProgressDialog(rootView.getContext());
            this.stop=false;
        }
        protected void onPreExecute()
        {
            this.dialog.setMessage("Procesando peticion");
            this.dialog.show();
        }
        @Override
        protected Void doInBackground(String... params)
        {

                String nombre_grupo=params[0];
                List<NameValuePair> postParams=new ArrayList<NameValuePair>();
                postParams.add(new BasicNameValuePair("userId",Login.iduser));
                this.obj=utils.postRequest("/"+nombre_grupo+"/members",postParams);


            return null;
        }

        protected void onPostExecute(Void v)
        {

            result();
        }

        /**
         *
         */
        private void result()
        {
            this.dialog.dismiss();
            ArrayList<Usuario> miembros=new ArrayList<Usuario>();
            ArrayList<Usuario> pendientes=new ArrayList<Usuario>();
            JSONArray json= null;
            JSONArray json1=null;

            try {
                String status_code=obj.getString("status_code");


                switch (status_code)
                {
                    case "200":
                        try {
                            json = obj.getJSONObject("miembros").getJSONArray("members");
                            json1=obj.getJSONObject("pendientes").getJSONArray("users");
                            for(int i=0;i<json.length();i++)
                            {
                                Usuario aux=new Usuario();
                                int id=(Integer) json.getJSONObject(i).getInt("id");
                                String username= (String) json.getJSONObject(i).getString("username");

                                aux.setUserId(id);
                                aux.setUsername(username);
                                miembros.add(aux);
                            }
                            for(int i=0;i<json1.length();i++)
                            {
                                Usuario aux=new Usuario();
                                int id=(Integer) json1.getJSONObject(i).getInt("id");
                                String username= (String) json1.getJSONObject(i).getString("username");

                                aux.setUserId(id);
                                aux.setUsername(username);
                                pendientes.add(aux);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        AdapterMiembros adapterMiembros=null;
                        AdapterUsuario adapterPendientes = null;
                        adapterMiembros = new AdapterMiembros(getActivity(), miembros,nombreGrupo);
                        adapterMiembros.addListener(listen);
                        lista_miembros.setAdapter(adapterMiembros);

                        adapterMiembros.notifyDataSetChanged();

                        adapterPendientes = new AdapterUsuario(getActivity(),pendientes,nombreGrupo);
                        adapterPendientes.addListener(listen);
                        lista_pendientes.setAdapter(adapterPendientes);
                        adapterPendientes.notifyDataSetChanged();
                        if(pendientes.size()>0)
                        {
                            ((LinearLayout) rootView.findViewById(R.id.layout_pendientes)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((LinearLayout) rootView.findViewById(R.id.layout_pendientes)).setVisibility(View.GONE);

                        }
                        if(miembros.size()>0)
                        {
                            ((LinearLayout) rootView.findViewById(R.id.layout_mienbros)).setVisibility(View.VISIBLE);
                            ((RelativeLayout) rootView.findViewById(R.id.getRec)).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            ((RelativeLayout) rootView.findViewById(R.id.getRec)).setVisibility(View.GONE);
                            ((LinearLayout) rootView.findViewById(R.id.layout_mienbros)).setVisibility(View.GONE);
                        }
                        ((LinearLayout) rootView.findViewById(R.id.container_aux_crear_grupo)).setVisibility(View.GONE);
                        break;
                    case "500":
                        String message=obj.getString("message");
                        this.stop=true;
                        Toast.makeText(rootView.getContext(),message , Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
