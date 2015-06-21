package com.quesada.reja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
public class CrearGrupo extends Fragment{

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_crear_grupos, container, false);

        final EditText crear_grupo=(EditText) rootView.findViewById(R.id.nombre_crear_grupo);

        crear_grupo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                  if(actionId == 0)
                  {
                      String nombre=(String) crear_grupo.getText().toString();
                      new AddGroup(rootView).execute(nombre,Login.iduser);
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
                String nombre=(String) crear_grupo.getText().toString();
                new AddGroup(rootView).execute(nombre,Login.iduser);
            }
        });


        return rootView;
    }
    private class AddGroup extends AsyncTask<String,Void,Void>
    {
        ProgressDialog dialog;
        JSONObject obj;
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
}
