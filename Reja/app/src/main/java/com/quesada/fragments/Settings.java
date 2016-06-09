package com.quesada.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.a;
import com.quesada.activities.Login;
import com.quesada.reja.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Settings extends Fragment {


    private View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        //rootview=container;

        rootview = inflater.inflate(R.layout.fragment_settigns, container, false);
        getActivity().setTitle("Ajustes");
        if(savedInstanceState==null) {

            final SharedPreferences preferences = this.getActivity().getSharedPreferences("Mis preferencias", Context.MODE_PRIVATE);

            final SharedPreferences.Editor editor = preferences.edit();
            final int activity=preferences.getInt("mainActivity", -1);

            ListView activities = (ListView) rootview.findViewById(R.id.pantalla_principal);


            String[] item = {"Recomendacion", "Buscar Restaurante", "Recomendación a Grupos", "Opciones","Mis puntuaciones"};


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootview.getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, item);



            activities.setAdapter(adapter);

            adapter.notifyDataSetChanged();


            SeekBar barra = (SeekBar) rootview.findViewById(R.id.distancia_contexto);


            final TextView distancia = (TextView) rootview.findViewById(R.id.text_distancia);

            int distanciaGuardada = preferences.getInt("barraPos", 0);
            barra.setProgress(distanciaGuardada);
            distancia.setText(String.valueOf(distancia(distanciaGuardada)) + " km");

            barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    int progress = seekBar.getProgress();

                    /*DecimalFormat df = new DecimalFormat("###.##");
                    df.setRoundingMode(RoundingMode.DOWN);
                    */
                    float dist = distancia(progress);
                    distancia.setText(String.valueOf(dist) + " km");
                    editor.putFloat("distancia", dist);
                    editor.putInt("barraPos", progress);
                    editor.commit();

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    int progress = seekBar.getProgress();
                    editor.putFloat("distancia", distancia(progress));
                    editor.putInt("barraPos", progress);
                    editor.commit();
                    guardado();
                }
            });
            Button borrar = (Button) rootview.findViewById(R.id.borrar_cuenta);

            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(rootview.getContext(), Login.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });




            activities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    int aux = preferences.getInt("mainActivity", -1);
                    if (aux != -1)
                        adapterView.getChildAt(aux).setBackgroundColor(getResources().getColor(R.color.background_material_light));

                    view.setBackgroundColor(getResources().getColor(R.color.enable_list));
                    editor.putInt("mainActivity", i);
                    editor.commit();
                    guardado();
                }
            });

        }
        LinearLayout ratings= (LinearLayout) rootview.findViewById(R.id.mis_puntuaciones);

        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle args=new Bundle();
                Ratings rat = Ratings.newInstance(null);

                getFragmentManager().beginTransaction().replace(R.id.main_container,rat).commit();


            }
        });
        return rootview;
    }

    private void guardado()
    {
        Toast.makeText(rootview.getContext(), "Guardado con exito", Toast.LENGTH_SHORT).show();
    }

    private float distancia(float progress)
    {
        float distancia=1;
        if(progress<=50)
        {
           distancia=(1000 * progress) / 50;

            distancia = distancia / 1000;
        }
        else
        {
            distancia=progress-49;
        }


        return distancia;
    }
}
