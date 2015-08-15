package org.aecc.superdiary.presentation.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.aecc.superdiary.R;


public class RecursosActivity extends DiaryBaseActivity {


    /*
            * The page Adapter : Manage the list of views (in fact here, it's fragments)
            * And send them to the ViewPager
            */
    private RecursosPageAdapter pagerAdapter;
    /**
     * The ViewPager is a ViewGroup that manage the swipe from left to right to left
     * Like a listView with a gesture listener...
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recursos, menu);
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

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recursos, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);
        //Find the viewPager
        viewPager = (ViewPager) super.findViewById(R.id.viewpager);
        //instanciate the PageAdapter
        pagerAdapter = new RecursosPageAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);
        //Add animation when the page are swiped
        //this instanciation only works with honeyComb and more
        //if you want it all version use AnimatorProxy of the nineoldAndroid lib
        //@see:http://stackoverflow.com/questions/15767729/backwards-compatible-pagetransformer
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pagerAdapter.release();
    }
}