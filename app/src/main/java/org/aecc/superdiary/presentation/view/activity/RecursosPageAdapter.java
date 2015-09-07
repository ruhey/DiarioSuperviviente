package org.aecc.superdiary.presentation.view.activity;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.aecc.superdiary.R;

import java.util.ArrayList;

public class RecursosPageAdapter extends PagerAdapter {

    private static int NUMVIEWS = 3;
    ArrayList<ViewGroup> views;
    LayoutInflater inflater;

    public RecursosPageAdapter(RecursosActivity ctx){
        inflater=LayoutInflater.from(ctx);
        views=new ArrayList<ViewGroup>(NUMVIEWS);
    }

    /**
     * To be called by onStop
     * Clean the memory
     */
    public void release(){
        views.clear();
        views=null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return NUMVIEWS;
    }
    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.,container
     */
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup currentView;

        if(views.size()>position && views.get(position)!=null){
            currentView=views.get(position);
        }else{
            int rootLayout= R.layout.view_screen;
            currentView= (ViewGroup) inflater.inflate(rootLayout,container,false);

            //((TextView)currentView.findViewById(R.id.txvTitle)).setText("My Views " + position);
            //((TextView)currentView.findViewById(R.id.btnButton)).setText("Button");
            //((ImageView)currentView.findViewById(R.id.imvPicture)).setBackgroundColor(0xFF00FF00);


            if(position==0)currentView= (ViewGroup)  inflater.inflate(R.layout.consejos_view, container, false);
            if(position==1)currentView= (ViewGroup)  inflater.inflate(R.layout.meditacion_view, container, false);
            if(position==2)currentView= (ViewGroup)  inflater.inflate(R.layout.telefonos_view, container, false);

        }
        container.addView(currentView);
        return currentView;
    }
    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);

    }
    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }
}