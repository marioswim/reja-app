package com.quesada.reja;
import com.quesada.utils.utils;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class Main extends ActionBarActivity {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;


    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RelativeLayout recomendacion = (RelativeLayout) findViewById(R.id.recomendacion);
        RelativeLayout busqueda = (RelativeLayout) findViewById(R.id.busqueda);
        RelativeLayout grupos = (RelativeLayout) findViewById(R.id.grupos);
        recomendacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isNetworkAvailable = utils.isNetworkAvailable(Main.this);

                if (isNetworkAvailable) {
                    startActivity(new Intent(Main.this, Recomendacion.class));
                } else {
                    //lanzar mensaje de no hay conexion disponible.
                    utils.connectionMessage(Main.this);

                }
            }
        });

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNetworkAvailable = utils.isNetworkAvailable(Main.this);

                if (isNetworkAvailable) {
                    startActivity(new Intent(Main.this, BusquedaRestaurante.class));
                } else {
                    //lanzar mensaje de no hay conexion disponible.
                    utils.connectionMessage(Main.this);

                }
            }

        });
        grupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isNetworkAvailable = utils.isNetworkAvailable(Main.this);

                if (isNetworkAvailable) {
                    startActivity(new Intent(Main.this, Grupos.class));
                } else {
                    //lanzar mensaje de no hay conexion disponible.
                    utils.connectionMessage(Main.this);

                }
            }

        });
    }
}
