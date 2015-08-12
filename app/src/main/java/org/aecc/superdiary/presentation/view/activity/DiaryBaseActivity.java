package org.aecc.superdiary.presentation.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.aecc.superdiary.R;

import java.util.ArrayList;

public class DiaryBaseActivity extends BaseActivity {


    protected static int position;
    private static boolean isLaunch = true;
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    protected String[] titulos;
    //private ArrayList<Item_objct> NavItms;
    //private TypedArray NavIcons;
    NavigationAdapter NavAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lista);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        mDrawerList.addHeaderView(header, "header", false);
        TypedArray NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);
        //Tomamos listado  de titulos desde el string-array de los recursos @string/nav_options
        titulos = getResources().getStringArray(R.array.nav_options);
        ArrayList<Item_objct> NavItms = new ArrayList<Item_objct>();
        //Agregamos objetos Item_objct al array
        //bienvenida
        NavItms.add(new Item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
        //caledario
        NavItms.add(new Item_objct(titulos[1], NavIcons.getResourceId(1, -1)));
        //citas
        NavItms.add(new Item_objct(titulos[2], NavIcons.getResourceId(2, -1)));
        //otros
        NavItms.add(new Item_objct(titulos[3], NavIcons.getResourceId(3, -1)));
        //PersonajesActivity
        NavItms.add(new Item_objct(titulos[4], NavIcons.getResourceId(4, -1)));
        //principal
        NavItms.add(new Item_objct(titulos[5], NavIcons.getResourceId(5, -1)));
        //Declaramos y seteamos nuestrp adaptador al cual le pasamos el array con los titulos
        NavAdapter = new NavigationAdapter(this, NavItms);
        mDrawerList.setAdapter(NavAdapter);
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer


        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.drawable.ic_drawer,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */ {
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(titulos[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        if (isLaunch) {
            isLaunch = false;
            openActivity(1);
        }
    }

    protected void openActivity(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position - 1]);
        mDrawerLayout.closeDrawer(mDrawerList);
        DiaryBaseActivity.position = position - 1;
        switch (position) {
            case 0:
                Log.d("DEV", "Debuggin");
                break;
            case 1:
                startActivity(new Intent(this, BienvenidaActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, Calendario.class));
                break;
            case 3:
                startActivity(new Intent(this, Citas.class));
                break;
            case 4:
                startActivity(new Intent(this, Otros.class));
                break;
            case 5:
                startActivity(new Intent(this, PersonajesActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, Principal.class));
                break;
            default:
                break;
        }

        Toast.makeText(this, "Selected Item Position::" + (position), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}