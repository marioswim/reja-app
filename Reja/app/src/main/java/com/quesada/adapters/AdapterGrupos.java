package com.quesada.adapters;

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
public class AdapterGrupos extends BaseAdapter {

    private ArrayList<String> grupos;
    private int R_layout_IdView;
    private Context contexto;
    private static LayoutInflater inflater = null;
    public AdapterGrupos(Context contexto, ArrayList<String> grupos) {

        this.contexto = contexto;
        this.grupos = grupos;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return grupos.size();
    }

    @Override
    public Object getItem(int position) {
        return grupos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView nombreGrupo;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grupos, parent, false);

            nombreGrupo = (TextView) convertView.findViewById(R.id.nombre_grupo);



            convertView.setTag(R.id.nombre_grupo, nombreGrupo);


        } else {

            nombreGrupo = (TextView) convertView.getTag(R.id.nombre_grupo);


        }

        nombreGrupo.setText(grupos.get(position));


        return convertView;
    }

    public void newAdapter(ArrayList<String> grupos) {
        this.grupos = grupos;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}
