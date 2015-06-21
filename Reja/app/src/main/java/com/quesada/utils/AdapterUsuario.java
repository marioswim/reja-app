package com.quesada.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quesada.reja.R;

import java.util.ArrayList;

/**
 * Created by SobreMesa on 21/06/2015.
 */
public class AdapterUsuario extends BaseAdapter {

    ArrayList<Usuario> lista_usuarios;

    private int R_layout_IdView;
    private Context contexto;
    private static LayoutInflater inflater = null;
    public AdapterUsuario(Context contexto, ArrayList<Usuario> user) {

        this.contexto = contexto;
        this.lista_usuarios = user;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista_usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView username;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_usuario, parent, false);

            username = (TextView) convertView.findViewById(R.id.username_miembro);
            convertView.setTag(R.id.username_miembro, username);

        } else {

            username = (TextView) convertView.getTag(R.id.username_miembro);


        }

        username.setText(lista_usuarios.get(position).getUsername());


        return convertView;
    }

    public void newAdapter(ArrayList<Usuario> lista_usuarios) {
        this.lista_usuarios = lista_usuarios;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}
