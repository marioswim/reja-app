package com.quesada.activities;
import com.quesada.fragments.BusquedaRestaurantes;
import com.quesada.fragments.Grupos;
import com.quesada.fragments.Recomendacion;
import com.quesada.fragments.Settings;
import com.quesada.reja.R;
import com.quesada.utils.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Menu extends ActionBarActivity {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    //private NavigationDrawerFragment mNavigationDrawerFragment;


    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Login.iduser="33346";





        if(savedInstanceState==null) {


            startApp();


            final TextView rec = (TextView) findViewById(R.id.recomendacion);
            TextView busqueda = (TextView) findViewById(R.id.busqueda);
            TextView grupos = (TextView) findViewById(R.id.grupos);
            TextView ajustes = (TextView) findViewById(R.id.ajustes);




    /*
            SharedPreferences preferences=getSharedPreferences("Mis preferencias",MODE_PRIVATE);
            SharedPreferences.Editor edit=preferences.edit();
            edit.clear();
            edit.commit(;*/


            //TODO crear navigation drawer, para eliminar este menu
            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    
                    boolean isNetworkAvailable = utils.isNetworkAvailable(Menu.this);


                    if (isNetworkAvailable) {

                        Recomendacion recomendacion=new Recomendacion();

                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,recomendacion).commit();




                        //startActivity(new Intent(Menu.this, Recomendacion.class));
                    } else {
                        //lanzar mensaje de no hay conexion disponible.
                        utils.connectionMessage(Menu.this);

                    }
                }
            });

            busqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isNetworkAvailable = utils.isNetworkAvailable(Menu.this);

                    if (isNetworkAvailable) {
                        BusquedaRestaurantes busqueda=new BusquedaRestaurantes();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,busqueda).commit();
                    } else {
                        //lanzar mensaje de no hay conexion disponible.
                        utils.connectionMessage(Menu.this);

                    }
                }

            });
            grupos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isNetworkAvailable = utils.isNetworkAvailable(Menu.this);

                    if (isNetworkAvailable) {
                        Grupos grp=new Grupos();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,grp).commit();
                    } else {
                        //lanzar mensaje de no hay conexion disponible.
                        utils.connectionMessage(Menu.this);

                    }
                }

            });
            ajustes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Settings ajustes=new Settings();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,ajustes).commit();
                }

            });
        }
    }

    private void startApp()
    {

        SharedPreferences preferences = getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);

        switch (preferences.getInt("mainActivity",-1))
        {
            case 0:
                Recomendacion frag=new Recomendacion();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag).commit();
                break;
            case 1:
                BusquedaRestaurantes frag1=new BusquedaRestaurantes();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag1).commit();

                break;
            case 2:
               Grupos frag2=new Grupos();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag2).commit();

                break;
            case 3:
                Settings frag3=new Settings();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag3).commit();

                break;
            default:
                Recomendacion aux=new Recomendacion();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,aux).commit();

                break;
        }
    }
}
