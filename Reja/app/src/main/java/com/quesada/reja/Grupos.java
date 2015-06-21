package com.quesada.reja;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Grupos extends ActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        final TextView unirse= (TextView) findViewById(R.id.boton_unirse_grupo);
        TextView crear= (TextView) findViewById(R.id.boton_crear_grupo);
        final CrearGrupo crearGrupo= new CrearGrupo();
        final UnirseGrupo unirseGrupo=new UnirseGrupo();
        final View separador_crear= (View) findViewById(R.id.separador_crear);
        final View separador_unirse= (View) findViewById(R.id.separador_unirse);
        unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            separador_unirse.setBackgroundColor(0xFF2CFF00);
            separador_crear.setBackgroundColor(0xff9b9b9b);
                if(savedInstanceState==null)
                {


                   getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grupos_container, unirseGrupo).commit();
                }
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            separador_crear.setBackgroundColor(0xFF2CFF00);
            separador_unirse.setBackgroundColor(0xff9b9b9b);
                if(savedInstanceState==null)
                {

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.grupos_container,crearGrupo).commit();
                }


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
