package com.quesada.reja;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;


public class Grupos extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        Button unirse= (Button) findViewById(R.id.boton_unirse_grupo);
        Button crear= (Button) findViewById(R.id.boton_crear_grupo);

        unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FrameLayout crear= (FrameLayout) findViewById(R.id.frame_crear_grupo);
                FrameLayout unirse= (FrameLayout) findViewById(R.id.frame_unirse_grupo);

                crear.setVisibility(View.GONE);
                unirse.setVisibility(View.VISIBLE);

                unirse.setEnabled(false);
                crear.setEnabled(true);
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FrameLayout crear= (FrameLayout) findViewById(R.id.frame_crear_grupo);
                FrameLayout unirse= (FrameLayout) findViewById(R.id.frame_unirse_grupo);

                crear.setVisibility(View.VISIBLE);
                unirse.setVisibility(View.GONE);
                crear.setEnabled(false);
                unirse.setEnabled(true);
            }
        });




    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grupos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
