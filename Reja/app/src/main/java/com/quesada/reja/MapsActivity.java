package com.quesada.reja;

/**
 * Created by SobreMesa on 30/06/2015.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quesada.utils.AdapterRecomendacion;
import com.quesada.utils.Restaurante;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng baseposition = new LatLng(37.7731245,-3.7886943);
        ArrayList<Restaurante> aux=Recomendacion.listaRecomendacion;

        for(int i=0;i<aux.size();i++)
        {
            LatLng recPos=new LatLng(aux.get(i).getLatitud(),aux.get(i).getLongitud());
            map.addMarker(new MarkerOptions().position(recPos).title(aux.get(i).getNombre()));
        }
        //map.addMarker(new MarkerOptions().position(sydney).title("Marker in jaen"));


        map.moveCamera(CameraUpdateFactory.newLatLng(baseposition));

        map.moveCamera(CameraUpdateFactory.zoomTo(14));
    }
}
