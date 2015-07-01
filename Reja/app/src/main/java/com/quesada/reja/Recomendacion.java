package com.quesada.reja;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.quesada.utils.AdapterRecomendacion;
import com.quesada.utils.Restaurante;
import com.quesada.utils.utils;

import java.util.ArrayList;


public class Recomendacion extends ActionBarActivity {

    public  static ArrayList<Restaurante> listaRecomendacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendacion);

        final TextView   lista =(TextView) findViewById(R.id.boton_lista_recomendacion);
        TextView   contexto=(TextView) findViewById(R.id.boton_activar_contexto);
        final TextView   geolocalizar =(TextView) findViewById(R.id.boton_geolocalizar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListaRecomendacion())
                    .commit();

            lista.setBackgroundColor(0xFF009AD7);
        }

        geolocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView recomendacion=(ListView) findViewById(R.id.recomendacion);

                Bundle aux=new Bundle();
                AdapterRecomendacion lista= (AdapterRecomendacion) ((ListView) findViewById(R.id.recomendacion)).getAdapter();

                listaRecomendacion=lista.getList();

                Intent intent=new Intent(Recomendacion.this,MapsActivity.class);








                startActivity(intent);
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prueba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/



}
