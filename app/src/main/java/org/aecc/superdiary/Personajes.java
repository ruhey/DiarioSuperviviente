package org.aecc.superdiary;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.aecc.superdiary.Activity.DiaryBaseActivity;

public class Personajes extends DiaryBaseActivity {
    ListView listViewCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_personajes, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        //setContentView(R.layout.activity_personajes);

        //Array que asociaremos al adaptador
        String[] array = new String[] {
                "Elemento 1"
                ,"Elemento 2"
                ,"Elemento 3"
                ,"Elemento 4"
                ,"Elemento 5"
                ,"Elemento 6"
        };

        //Creaci�n del adaptador, vamos a escoger el layout
        //simple_list_item_1, que los mostr
        ListAdapter adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, array);

        //Asociamos el adaptador a la vista.
        listViewCharacters = (ListView) view.findViewById(R.id.list);
        listViewCharacters.setAdapter(adaptador);
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
        int id = item.getItemId();
        int id2 = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

