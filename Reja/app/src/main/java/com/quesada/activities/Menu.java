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
import android.widget.ListView;
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

        getSupportActionBar().setElevation(0);

        Login.iduser="33346";





        if(savedInstanceState==null) {


            startApp();


            final TextView rec = (TextView) findViewById(R.id.recomendacion);
            final TextView busqueda = (TextView) findViewById(R.id.busqueda);
            final TextView grupos = (TextView) findViewById(R.id.grupos);
            final TextView ajustes = (TextView) findViewById(R.id.ajustes);



            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    
                    boolean isNetworkAvailable = utils.isNetworkAvailable(Menu.this);


                    if (isNetworkAvailable) {

                        rec.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                        busqueda.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        grupos.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        ajustes.setBackgroundColor(getResources().getColor(R.color.blue_disable));

                        Recomendacion recomendacion=new Recomendacion();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,recomendacion).commit();


                    } else {

                        utils.connectionMessage(Menu.this);

                    }
                }
            });

            busqueda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isNetworkAvailable = utils.isNetworkAvailable(Menu.this);



                    if (isNetworkAvailable) {
                        rec.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        busqueda.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                        grupos.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        ajustes.setBackgroundColor(getResources().getColor(R.color.blue_disable));

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
                        rec.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        busqueda.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                        grupos.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                        ajustes.setBackgroundColor(getResources().getColor(R.color.blue_disable));
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
                    rec.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                    busqueda.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                    grupos.setBackgroundColor(getResources().getColor(R.color.blue_disable));
                    ajustes.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                    Settings ajustes=new Settings();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container,ajustes).commit();
                }

            });
        }
    }

    private void startApp()
    {

        SharedPreferences preferences = getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);

        final TextView rec = (TextView) findViewById(R.id.recomendacion);
        final TextView busqueda = (TextView) findViewById(R.id.busqueda);
        final TextView grupos = (TextView) findViewById(R.id.grupos);
        final TextView ajustes = (TextView) findViewById(R.id.ajustes);
        switch (preferences.getInt("mainActivity",-1))
        {
            case 0:
                rec.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                Recomendacion frag=new Recomendacion();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag).commit();
                break;
            case 1:
                busqueda.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                BusquedaRestaurantes frag1=new BusquedaRestaurantes();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag1).commit();

                break;
            case 2:
                grupos.setBackgroundColor(getResources().getColor(R.color.blue_enable));
               Grupos frag2=new Grupos();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag2).commit();

                break;
            case 3:
                ajustes.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                Settings frag3=new Settings();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,frag3).commit();




                break;
            default:
                rec.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                Recomendacion aux=new Recomendacion();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,aux).commit();

                break;
        }
    }
}
