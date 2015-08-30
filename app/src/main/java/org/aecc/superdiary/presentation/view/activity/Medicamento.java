package org.aecc.superdiary.presentation.view.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

public class Medicamento extends DiaryBaseActivity implements View.OnClickListener{


    //UI References
    private EditText fechaIniMed;
    private EditText fechaFinMed;
    private EditText horaIniMed;
    private EditText horaFinMed;

    private DatePickerDialog fIniDatePickerDialog;
    private DatePickerDialog fFinDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;
    private TimePickerDialog hFinTimePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_medicamento, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(titulos[position]);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        findViewsById();
        setDateTimeField();
    }

    private void findViewsById() {
        fechaIniMed = (EditText) findViewById(R.id.fechaInicMedic);
        fechaIniMed.setInputType(InputType.TYPE_NULL);
        fechaIniMed.requestFocus();

        horaIniMed = (EditText) findViewById(R.id.horaIniMedic);
        horaIniMed.setInputType(InputType.TYPE_NULL);
        
        fechaFinMed = (EditText) findViewById(R.id.fechaFinMedic);
        fechaFinMed.setInputType(InputType.TYPE_NULL);

        horaFinMed = (EditText) findViewById(R.id.horaFinMedic);
        horaFinMed.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        fechaIniMed.setOnClickListener(this);
        horaIniMed.setOnClickListener(this);
        fechaFinMed.setOnClickListener(this);
        horaFinMed.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaIniMed.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fFinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaFinMed.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                Calendar newTime = Calendar.getInstance();
                horaIniMed.setText(selectedHour + ":" + selectedMinute);
            } },hour , minute, true);

        hFinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                Calendar newTime = Calendar.getInstance();
                horaFinMed.setText(selectedHour + ":" + selectedMinute);
            } },hour, minute, true);
    }

    @Override
    public void onClick(View view) {
        if (view == fechaIniMed) {
            fIniDatePickerDialog.show();
        } else if (view == fechaFinMed) {
            fFinDatePickerDialog.show();
        } else if (view == horaIniMed) {
            hIniTimePickerDialog.show();
        } else if (view == horaFinMed) {
            hFinTimePickerDialog.show();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicamento, menu);
        return true;
    }
}
