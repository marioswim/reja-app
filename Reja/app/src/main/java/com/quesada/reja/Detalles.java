package com.quesada.reja;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quesada.utils.Restaurante;
import com.quesada.utils.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class Detalles extends ActionBarActivity {

    ProgressDialog dialog;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalles);

        dialog=new ProgressDialog(this);
        Bundle b=getIntent().getExtras();

        final String idItem=b.getString("idItem");
        final String idUser=b.getString("idUser");

        final Button puntuar=(Button) findViewById(R.id.detalles_enviar);
        final RatingBar rating=(RatingBar) findViewById(R.id.detalles_puntuar);
        TextView promedio=(TextView) findViewById(R.id.detalles_rating_medio);
        TextView llamar=(TextView) findViewById(R.id.detalles_llamar);


        String origen=b.getString("origen");

        if(origen.compareToIgnoreCase("Búsqueda")==0)
        {
            promedio.setVisibility(View.VISIBLE);
        }

        puntuar.setEnabled(false);
        new DetallesRequest().execute(idItem, "33346");

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView numero=(TextView) findViewById(R.id.detalles_numero);
                Intent intent= new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+numero.getText().toString()));
                startActivity(intent);
            }
        });



        rating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                puntuar.setEnabled(true);
                return false;
            }
        });


        puntuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double rat=rating.getRating();
                View root= findViewById(R.id.detalles);
                new Puntuar(root.getContext()).execute(idItem,"33346",Double.toString(rat));
            }
        });

    }




    private class DetallesRequest extends AsyncTask<String, Void, Void> {

        JSONObject obj;

        protected void onPreExecute() {
            dialog.setMessage("Recibiendo informacion del restaurante");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("idItem", params[0]));
            postParams.add(new BasicNameValuePair("idUser", params[1]));
            this.obj=utils.postRequest("details", postParams);

            return null;
        }

        protected void onPostExecute(Void v) {
            dialog.dismiss();


            //TextView nombre = (TextView) findViewById(R.id.detalles_nombre_restaurante);
            TextView media= (TextView) findViewById(R.id.detalles_rating_medio);
            TextView terraza = (TextView) findViewById(R.id.detalles_terraza_opcion);
            TextView direccion = (TextView) findViewById(R.id.detalles_direccion_obtenida);
            TextView numero = (TextView) findViewById(R.id.detalles_numero);
            TextView llamar = (TextView) findViewById(R.id.detalles_llamar);
            TextView telefono = (TextView) findViewById(R.id.detalles_telefono);
            RatingBar ratingBar= (RatingBar) findViewById(R.id.detalles_puntuar);
            try {

                if (obj!=null) {

                    String nom=obj.getString("Name");
                    String dir=obj.getString("addres");
                    String num=obj.getString("Phone_Number");
                    Float rat=Float.parseFloat((obj.getString("rating")));
                    String rat_media= obj.getString("Average");
                    int ter=0;

                    if(obj.has("terraece"))
                    {
                        ter=obj.getInt("terrace");
                    }

                    //nombre.setText(nom);

                    setTitle(nom);
                    if(dir.compareToIgnoreCase("")!=0)
                        direccion.setText(dir);
                    if(num.compareToIgnoreCase("")==0  || num.compareToIgnoreCase("null")==0)
                    {
                        telefono.setVisibility(View.INVISIBLE);
                        numero.setVisibility(View.INVISIBLE);
                        llamar.setEnabled(false);
                    }
                    else
                        numero.setText(num);

                    ratingBar.setRating(rat);


                    if(ter==1)
                    {
                        terraza.setText("Si");
                    }
                    else
                    {
                        terraza.setText("No");
                    }
                    media.setText(rat_media);
                    // (obj.getString("Name"));


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class Puntuar extends AsyncTask<String, Void,Void>
    {
        JSONObject obj;
        Context context;

        Puntuar(Context cont)
        {
            this.context=cont;
        }
        protected void onPreExecute() {
            dialog.setMessage("Enviando puntuación");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair("idItem", params[0]));
            postParams.add(new BasicNameValuePair("idUser", params[1]));
            postParams.add(new BasicNameValuePair("rating", params[2]));
            this.obj=utils.postRequest("rating", postParams);

            return null;
        }

        protected void onPostExecute(Void v) {
            dialog.dismiss();


            if (obj!=null) {

                try {
                    int code=obj.getInt("code");
                    switch (code)
                    {
                        case 200:
                            Button puntuar= (Button) findViewById(R.id.detalles_enviar);
                            puntuar.setEnabled(false);
                            Toast.makeText(context,"Puntuación enviada con exito",Toast.LENGTH_SHORT);


                            break;
                        case 304:
                            Toast.makeText(context,"Hubo un erro. Intentelo más tarde",Toast.LENGTH_LONG);
                            break;
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // (obj.getString("Name"));


            }


        }
    }
}

