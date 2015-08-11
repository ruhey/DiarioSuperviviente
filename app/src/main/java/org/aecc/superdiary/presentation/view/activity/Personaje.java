package org.aecc.superdiary.presentation.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import org.aecc.superdiary.presentation.view.activity.DiaryBaseActivity;
import org.aecc.superdiary.R;

public class Personaje extends DiaryBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje);

        ImageButton botonLlamada = (ImageButton)findViewById(R.id.llamadaContacto);
        ImageButton botonEmail = (ImageButton)findViewById(R.id.correoEmailContacto);

        Spinner spinner = (Spinner)findViewById(R.id.spinerRelaccionContacto);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.relacionContactoArray, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        botonLlamada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText num=(EditText)findViewById(R.id.telefonoInsert);
                String numTelefono = "tel:" + num.getText().toString().trim();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(numTelefono));
                startActivity(callIntent);
            }
        });

        botonEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText mail=(EditText)findViewById(R.id.emailInsert);
                String emailContacto = mail.getText().toString().trim();
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { emailContacto });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Diario de un superviviente");
                emailIntent.setType("plain/text");
                startActivity(emailIntent);

                }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personaje, menu);
        return true;
    }


}