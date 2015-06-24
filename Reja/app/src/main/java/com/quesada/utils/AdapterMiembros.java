package com.quesada.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quesada.reja.ListenerRefresh;
import com.quesada.reja.R;

import java.util.ArrayList;

/**
 * Created by SobreMesa on 23/06/2015.
 */
public class AdapterMiembros extends AdapterUsuario
{

    public AdapterMiembros(Context contexto, ArrayList<Usuario> user, String nombre) {
        super(contexto, user, nombre);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView=super.getView(position,convertView,parent);
        ImageView aceptar= (ImageView) convertView.findViewById(R.id.boton_aceptar_miembro);

        aceptar.setVisibility(View.INVISIBLE);



        return convertView;
    }
}
