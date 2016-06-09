package com.quesada.fragments;


import android.support.v4.app.Fragment;
;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quesada.fragments.CrearGrupo;
import com.quesada.reja.R;
import com.quesada.fragments.UnirseGrupo;


public class Grupos extends Fragment {

    @Override
   public View onCreateView(LayoutInflater inflater,ViewGroup container, final Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.fragment_grupos, container, false);

        getActivity().setTitle("Grupos");
        final TextView unirse= (TextView) rootView.findViewById(R.id.boton_unirse_grupo);
        final TextView crear= (TextView) rootView.findViewById(R.id.boton_crear_grupo);
        final CrearGrupo crearGrupo= new CrearGrupo();
        final UnirseGrupo unirseGrupo=new UnirseGrupo();
        final View separador_crear= (View) rootView.findViewById(R.id.separador_crear);
        final View separador_unirse= (View) rootView.findViewById(R.id.separador_unirse);
        unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            unirse.setBackgroundColor(getResources().getColor(R.color.blue_enable));
            crear.setBackgroundColor(getResources().getColor(R.color.blue_disable));
            separador_unirse.setBackgroundColor(getResources().getColor(R.color.enable));
            separador_crear.setBackgroundColor(getResources().getColor(R.color.disable));
                if(savedInstanceState==null)
                {


                   getFragmentManager().beginTransaction()
                            .replace(R.id.grupos_container, unirseGrupo).commit();
                }
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            crear.setBackgroundColor(getResources().getColor(R.color.blue_enable));
                unirse.setBackgroundColor(getResources().getColor(R.color.blue_disable));
            separador_crear.setBackgroundColor(getResources().getColor(R.color.enable));
            separador_unirse.setBackgroundColor(getResources().getColor(R.color.disable));
                if(savedInstanceState==null)
                {

                    getFragmentManager().beginTransaction()
                            .replace(R.id.grupos_container,crearGrupo).commit();
                }


            }
        });



    return rootView;
    }

}
