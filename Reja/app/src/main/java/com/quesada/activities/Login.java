package com.quesada.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.quesada.reja.R;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {

    private SharedPreferences preferences;
    public static String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.preferences = getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String aux = (String) preferences.getString("id", "");
        if (aux == "") {
            editor.putString("id", String.valueOf(33346));
            editor.commit(); //TODO eliminar este bloque if cuando este desplegado para que coga directamente el usuario del servidor si no esta.
        } else {
            iduser = preferences.getString("id", "");
            startApp();
        }


        SignInButton g = (SignInButton) findViewById(R.id.sign_in_button);


        final String[] correos = accounts();

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Seleccione una cuenta")
                        .setItems(correos, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String cuenta = correos[which];
                                View root = findViewById(R.id.activity_login);
                                new LoginRequest(root.getContext()).execute(cuenta);
                            }
                        });
                builder.show();
            }
        });
    }

    private String[] accounts() {
        ArrayList<String> aux = new ArrayList<String>();

        try {

            Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");

            for (Account account : accounts) {

                aux.add(account.name);

            }
        } catch (Exception e) {
            Log.i("Exception", "Exception:" + e);
        }
        String[] cuentas = new String[aux.size()];
        cuentas = aux.toArray(cuentas);
        return cuentas;
    }

    private class LoginRequest extends AsyncTask<String, Void, Void> {
        JSONObject obj = null;
        ProgressDialog dialog;
        Context context;

        public LoginRequest(Context root) {

            this.context = root;
            this.dialog = new ProgressDialog(root);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Login");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("email", params[0]));

            this.obj = utils.postRequest("login", postParams);


            return null;
        }


        protected void onPostExecute(Void v) {
            this.dialog.dismiss();
            if (obj != null) {

                try {
                    int code = obj.getInt("status_code");
                    switch (code) {
                        case 200:
                            iduser = obj.getString("userId");
                            guardarId(iduser);
                            Toast.makeText(context, "Login correcto", Toast.LENGTH_SHORT);
                            startApp();
                            break;
                        case 201:
                            iduser = obj.getString("userId");
                            guardarId(iduser);
                            Toast.makeText(context, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT);
                            startApp();
                            break;
                        case 500:

                            Toast.makeText(context, "Error durante el proceso, intentelo mas tarde", Toast.LENGTH_LONG);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startApp()
    {

        startActivity(new Intent(Login.this, Menu.class));
    }


    private void guardarId(String id)
    {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("id",id);
        editor.commit();
    }
}
