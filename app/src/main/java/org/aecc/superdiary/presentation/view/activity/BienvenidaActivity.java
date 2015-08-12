package org.aecc.superdiary.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.aecc.superdiary.R;


public class BienvenidaActivity extends DiaryBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_bienvenida, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);

        Button botonBienvenida = (Button) findViewById(R.id.botonEnMarcha);
        Button botonEnMarcha = (Button) findViewById(R.id.botonEnMarcha);
        Button botonCalendario = (Button) findViewById(R.id.botonCalendario);
        Button botonMisPersonas = (Button) findViewById(R.id.botonMisPersonas);
        Button botonMisCitas = (Button) findViewById(R.id.botonMisCitas);
        Button botonOtros = (Button) findViewById(R.id.botonOtros);
        Button botonInicio = (Button) findViewById(R.id.botonInicio);


        botonBienvenida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, Principal.class);
                startActivity(intent);
            }
        });

        botonCalendario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, Calendario.class);
                startActivity(intent);
            }
        });

        botonMisPersonas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, PersonajesActivity.class);
                startActivity(intent);
            }
        });

        botonMisCitas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, Citas.class);
                startActivity(intent);
            }
        });

        botonOtros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BienvenidaActivity.this, Otros.class);
                startActivity(intent);
            }
        });

        ImageView imagenLogo = (ImageView) findViewById(R.id.imagenEntrada);
        imagenLogo.setImageResource(R.drawable.ic_launcher);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bienvenida, menu);
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
    }
}
