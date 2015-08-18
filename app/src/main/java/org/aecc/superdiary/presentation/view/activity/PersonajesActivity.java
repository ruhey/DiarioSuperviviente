package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.ContactComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerContactComponent;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.presenter.ContactListPresenter;
import org.aecc.superdiary.presentation.view.PersonajesListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.InjectView;


public class PersonajesActivity extends DiaryBaseActivity implements HasComponent<ContactComponent>, PersonajesListView{

    @Inject
    ContactListPresenter contactListPresenter;

    @InjectView(R.id.recycler_contacts)
    RecyclerView recycler_contacts;

    private ContactsAdapter contactsAdapter;

    private ContactComponent contactComponent;

    public static Intent getCallingIntent(Context context) {
        //Intent intent = new Intent(PersonajesActivity.this, Personaje.class);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_personajes, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        this.initializeInjector();
        //setContentView(R.layout.activity_personajes);

        //Array que asociaremos al adaptador
        String[] array = new String[]{
                "Elemento 1"
                , "Elemento 2"
                , "Elemento 3"
                , "Elemento 4"
                , "Elemento 5"
                , "Elemento 6"
        };

        //Creaci�n del adaptador, vamos a escoger el layout
        //simple_list_item_1, que los mostr
        ListAdapter adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, array);

        //Asociamos el adaptador a la vista.
        listViewCharacters = (ListView) view.findViewById(R.id.list);
        listViewCharacters.setAdapter(adaptador);

        listViewCharacters.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            // ListView Clicked item index
            int itemPosition = position;
            // ListView Clicked item value
            String itemValue = (String) listViewCharacters.getItemAtPosition(position);
            Intent intent = new Intent(PersonajesActivity.this, Personaje.class);

            // Show Alert
                /*Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();*/

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
            //startActivity(intent);


        });


    }

    private void initializeInjector() {
        this.contactComponent = DaggerContactComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /*@Override public void onUserClicked(ContactModel contactModel) {
        this.navigator.navigateToContacDetails(this, contactModel.getContactId());
    }*/

    @Override
    public ContactComponent getComponent() {
        return contactComponent;
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

    @Override
    public void renderContactList(Collection<ContactModel> contactModelCollection) {

    }

    @Override
    public void viewContact(ContactModel contactModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}

