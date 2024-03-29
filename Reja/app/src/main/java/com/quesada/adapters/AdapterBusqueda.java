package com.quesada.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quesada.reja.R;
import com.quesada.objects.Restaurante;

import java.util.ArrayList;

/***
 * Created by uni on 27/04/15.
 */
public class AdapterBusqueda extends BaseAdapter {

    private ArrayList<Restaurante> entradas;
    private int R_layout_IdView;
    private Context contexto;

    private static LayoutInflater inflater = null;

    public AdapterBusqueda(Context contexto, ArrayList<Restaurante> entradas) {

        this.contexto = contexto;
        this.entradas = entradas;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView nombre;
        TextView direccion;
        TextView rating;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_busqueda, parent, false);

            nombre = (TextView) convertView.findViewById(R.id.nombre);
            direccion = (TextView) convertView.findViewById(R.id.direccion);


            convertView.setTag(R.id.nombre, nombre);
            convertView.setTag(R.id.direccion, direccion);

        } else {

            nombre = (TextView) convertView.getTag(R.id.nombre);
            direccion = (TextView) convertView.getTag(R.id.direccion);

        }

        nombre.setText(entradas.get(position).getNombre());
        direccion.setText(entradas.get(position).getDireccion());

        return convertView;
    }

    public void newAdapter(ArrayList<Restaurante> entradas) {
        this.entradas = entradas;
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}