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


    private RecursosPageAdapter pagerAdapter;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recursos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}