package org.aecc.superdiary.presentation.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.aecc.superdiary.R;


public class Principal extends DiaryBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_principal, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        //setContentView(R.layout.activity_principal);

        final Button botonCreditos = (Button) findViewById(R.id.botonCreditos);
        final EditText login = (EditText) findViewById(R.id.insertaTextoLogin);

        botonCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable nombre = login.getText();
                Toast toast = Toast.makeText(getApplicationContext(), "BIENVENIDO " + nombre + "Designed by Rub�n Toquero and Fernando Santaolaya", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    //Commit inicial para ver si funciona
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int id2 = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
