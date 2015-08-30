package org.aecc.superdiary.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.aecc.superdiary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Sintoma extends DiaryBaseActivity  implements View.OnClickListener{

    //UI References
    private EditText fecha;
    private EditText hora;

    private DatePickerDialog fDatePickerDialog;
    private TimePickerDialog hTimePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sintoma, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        fecha = (EditText) findViewById(R.id.fechaSintoma);
        fecha.setInputType(InputType.TYPE_NULL);
        fecha.requestFocus();

        hora = (EditText) findViewById(R.id.horaSintoma);
        hora.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fecha.setOnClickListener(this);
        hora.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fecha.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                Calendar newTime = Calendar.getInstance();
                hora.setText(selectedHour + ":" + selectedMinute);
            } },hour , minute, true);

    }

    @Override
    public void onClick(View view) {
        if (view == fecha) {
            fDatePickerDialog.show();
        } else if (view == hora) {
            hTimePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sintoma, menu);
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
