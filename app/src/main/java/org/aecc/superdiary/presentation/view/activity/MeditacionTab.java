package org.aecc.superdiary.presentation.view.activity;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import org.aecc.superdiary.R;


public class MeditacionTab extends DiaryBaseActivity{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.meditacion_tab,container,false);
        return v;
    }
}