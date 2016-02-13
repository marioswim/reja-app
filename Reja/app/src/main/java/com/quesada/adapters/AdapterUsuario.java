package com.quesada.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quesada.listeners.ListenerRefresh;
import com.quesada.reja.R;
import com.quesada.objects.Usuario;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SobreMesa on 21/06/2015.
 */
public class AdapterUsuario extends BaseAdapter {

    ArrayList<Usuario> lista_usuarios;
    String nombreGrupo;
    ListenerRefresh fragmet;
    private Context contexto;
    protected static LayoutInflater inflater = null;


    public AdapterUsuario(Context contexto, ArrayList<Usuario> user,String nombre) {

        this.contexto = contexto;
        this.nombreGrupo=nombre;
        this.lista_usuarios = user;


        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setListaUsuario(ArrayList<Usuario> x){
        this.lista_usuarios=x;
    }

    @Override
    public int getCount() {
        return lista_usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView username;
        ImageView aceptar;
        ImageView rechazar;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_usuario, parent, false);

            username = (TextView) convertView.findViewById(R.id.username_miembro);
            aceptar=(ImageView) convertView.findViewById(R.id.boton_aceptar_miembro);
            rechazar=(ImageView) convertView.findViewById(R.id.boton_rechazar_miembro);
            convertView.setTag(R.id.username_miembro, username);

        } else {

            username = (TextView) convertView.getTag(R.id.username_miembro);
            aceptar=(ImageView) convertView.findViewById(R.id.boton_aceptar_miembro);
            rechazar=(ImageView) convertView.findViewById(R.id.boton_rechazar_miembro);


        }

        username.setText(lista_usuarios.get(position).getUsername());
        final int aux=position;


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=lista_usuarios.get(aux).getUserId();
                new AcceptUser().execute(nombreGrupo, Integer.toString(id));



            }
        });
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=lista_usuarios.get(aux).getUserId();
                new DenyUser().execute(nombreGrupo,Integer.toString(id));

            }
        });

        return convertView;
    }

    public void newAdapter(ArrayList<Usuario> lista_usuarios,String nombre) {

        this.lista_usuarios = lista_usuarios;
        this.nombreGrupo=nombre;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void addListener(ListenerRefresh l)
    {
        this.fragmet=l;
    }
/*
    @Override
    public boolean isEnabled(int position)
    {
        return false;
    }*/

    protected class AcceptUser extends AsyncTask<String,Void, Void>
    {
        ProgressDialog dialog;
        JSONObject obj;
        public AcceptUser()
        {


        }
        protected void onPreExecute()
        {
            Toast.makeText(contexto, "Procesando petición",Toast.LENGTH_SHORT).show();
        }
        @Override
        protected Void doInBackground(String... params) {
            String nombre_grupo=params[0];
            String userId=params[1];
            List<NameValuePair> postParams=new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("groupId", nombre_grupo));
            postParams.add(new BasicNameValuePair("userId",userId));


            this.obj= utils.postRequest("/accept", postParams);



            return null;
        }
        protected void onPostExecute(Void v)
        {

            fragmet.refresh();

        }
    }
    protected class DenyUser extends AsyncTask<String,Void, Void>
    {
        ProgressDialog dialog;
        JSONObject obj;
        public DenyUser()
        {


        }
        protected void onPreExecute()
        {
            Toast.makeText(contexto, "Procesando petición", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected Void doInBackground(String... params) {
            String nombre_grupo=params[0];
            String userId=params[1];
            List<NameValuePair> postParams=new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("groupId", nombre_grupo));
            postParams.add(new BasicNameValuePair("userId",userId));


            this.obj=utils.postRequest("/deny",postParams);



            return null;
        }
        protected void onPostExecute(Void v)
        {
/*
            Log.d("TAG", "p execute");
            ArrayList<Usuario> miembros=new ArrayList<Usuario>();
            ArrayList<Usuario> pendientes=new ArrayList<Usuario>();
            JSONArray json= null;
            JSONArray json1=null;
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
            }*/
            fragmet.refresh();
        }
    }

}
