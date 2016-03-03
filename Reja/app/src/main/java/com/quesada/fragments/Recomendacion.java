package com.quesada.fragments;


import android.content.Intent;
import android.support.v4.app.*;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.quesada.activities.MapsActivity;
import com.quesada.activities.Menu;
import com.quesada.adapters.AdapterRecomendacion;
import com.quesada.fragments.ListaRecomendacion;
import com.quesada.listeners.ListenerPositionChanged;
import com.quesada.reja.R;
import com.quesada.services.Gps;
import com.quesada.objects.Restaurante;

import java.util.ArrayList;


public class Recomendacion extends Fragment implements ListenerPositionChanged{
    private boolean context;
    public  static ArrayList<Restaurante> listaRecomendacion;


    private View rootview;
    Gps gps;
    ListenerPositionChanged listener;

    ListaRecomendacion lista_recomendacion;

    public Recomendacion(){};

    public static Recomendacion newInstance(Bundle arguments){
        Recomendacion f = new Recomendacion();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_recomendacion, container, false);


        listener=this;

        this.context=false;

        final TextView   lista =(TextView) rootview.findViewById(R.id.boton_lista_recomendacion);
        final TextView   contexto=(TextView) rootview.findViewById(R.id.boton_activar_contexto);
        final TextView   geolocalizar =(TextView) rootview.findViewById(R.id.boton_geolocalizar);





        if (savedInstanceState == null) {

            Bundle params = getArguments();
            params.putBoolean("context", this.context);

            this.lista_recomendacion = ListaRecomendacion.newInstance(params);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, lista_recomendacion)
                    .commit();

            lista.setBackgroundColor(getResources().getColor(R.color.blue_enable));


            geolocalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: mirar xq no funciona la recomendacion contextualizada geolocalizada
                    ListView recomendacion = (ListView) rootview.findViewById(R.id.recomendacion);
                    AdapterRecomendacion lista = (AdapterRecomendacion) ((ListView) rootview.findViewById(R.id.recomendacion)).getAdapter();
                    listaRecomendacion = lista.getList();
                    Intent intent = new Intent(rootview.getContext(), MapsActivity.class);
                    startActivity(intent);
                }
            });


            contexto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    gps = new Gps(rootview.getContext(), listener);

                    if (!gps.canGetLocation()) {

                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }


                }
            });
        }
        return rootview;
    }

    @Override
    public void refresh() {

       this.lista_recomendacion.requestContextRecomendation();
        TextView   contexto=(TextView) rootview.findViewById(R.id.boton_activar_contexto);
        contexto.setTextColor(getResources().getColor(R.color.enable));

    }

}
