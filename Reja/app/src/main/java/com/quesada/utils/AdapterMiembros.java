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

    public AdapterMiembros(Context contexto, ArrayList<Usuario> user, String nombre, ListenerRefresh l) {
        super(contexto, user, nombre, l);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView username;
        ImageView aceptar;
        ImageView rechazar;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_usuario, parent, false);

            username = (TextView) convertView.findViewById(R.id.username_miembro);
            aceptar=(ImageView) convertView.findViewById(R.id.boton_aceptar_miembro);
            rechazar=(ImageView) convertView.findViewById(R.id.boton_rechazar_miembro);
            convertView.setTag(R.id.username_miembro, username);
        } else {
            username = (TextView) convertView.getTag(R.id.username_miembro);
            aceptar=(ImageView) convertView.findViewById(R.id.boton_aceptar_miembro);
            rechazar=(ImageView) convertView.findViewById(R.id.boton_rechazar_miembro);
        }
        aceptar.setVisibility(View.GONE);
        username.setText(lista_usuarios.get(position).getUsername());
        final int aux=position;


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=lista_usuarios.get(aux).getUserId();


                AcceptUser admitir=new AcceptUser();
                admitir.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, nombreGrupo, Integer.toString(id));



            }
        });
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id=lista_usuarios.get(aux).getUserId();
                DenyUser denegar=new DenyUser();
                denegar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,nombreGrupo,Integer.toString(id));

            }
        });

        return convertView;
    }
}
