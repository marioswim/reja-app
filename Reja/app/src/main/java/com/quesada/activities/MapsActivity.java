package com.quesada.activities;

/**
 * Created by SobreMesa on 30/06/2015.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quesada.fragments.Recomendacion;
import com.quesada.reja.R;
import com.quesada.services.Gps;
import com.quesada.objects.Restaurante;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    GoogleMap map;
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

        this.map=map;
        refreshMap();

    }
    private void refreshMap()
    {
        this.map.clear();
        this.map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        int zoom=9;
        LatLng baseposition;
        ArrayList<Restaurante> aux= Recomendacion.listaRecomendacion;
        if(Gps.longitude!=0.0 && Gps.latitude!=0.0)
        {
            zoom=12;
            baseposition = new LatLng(Gps.latitude,Gps.longitude);
            this.map.addMarker(new MarkerOptions().position(baseposition).icon(BitmapDescriptorFactory.fromResource(R.drawable.person_marker)));
            this.map.moveCamera(CameraUpdateFactory.newLatLng(baseposition));
        }
        else
        {
            baseposition = new LatLng(37.8946807,-3.5418155);
            this.map.moveCamera(CameraUpdateFactory.newLatLng(baseposition));
        }
        for(int i=0;i<aux.size();i++)
        {
            LatLng recPos=new LatLng(aux.get(i).getLatitud(),aux.get(i).getLongitud());
            this.map.addMarker(new MarkerOptions().position(recPos).icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_marker)).title(aux.get(i).getNombre()).snippet(aux.get(i).getDireccion()));
        }
        this.map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
}
